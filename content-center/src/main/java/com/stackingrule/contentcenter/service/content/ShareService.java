package com.stackingrule.contentcenter.service.content;

import com.stackingrule.contentcenter.dao.content.ShareMapper;
import com.stackingrule.contentcenter.dao.messaging.RocketmqTransactionLogMapper;
import com.stackingrule.contentcenter.domain.dto.content.ShareAuditDTO;
import com.stackingrule.contentcenter.domain.dto.content.ShareDTO;
import com.stackingrule.contentcenter.domain.dto.enums.AuditStatusEnum;
import com.stackingrule.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.stackingrule.contentcenter.domain.dto.user.UserDTO;
import com.stackingrule.contentcenter.domain.entity.content.Share;
import com.stackingrule.contentcenter.domain.entity.messaging.RocketmqTransactionLog;
import com.stackingrule.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;

    private final RestTemplate restTemplate;

    private final UserCenterFeignClient userCenterFeignClient;

    private final RocketMQTemplate rocketMQTemplate;

    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;


    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人 id
        Integer userId = share.getUserId();

        // 用户中心所有实例信息
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);

        // 消息装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }

    public Share auditById(Integer id, ShareAuditDTO auditDTO) {

        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法!该分享不存在!");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        // 3. 如果是PASS，发送消息给rocketMQ，给发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            // 发送半消息
            String transactionId = UUID.randomUUID().toString();
            this.rocketMQTemplate.sendMessageInTransaction(
                    "tx-add-bonus",
                    "add-bonus",
                    MessageBuilder.withPayload(UserAddBonusMsgDTO
                            .builder()
                            .userId(share.getUserId())
                            .bonus(50)
                            .build()
                    )
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
                            .setHeader("share_id", id)
                    .build(),
                    auditDTO
            );
        } else {
            this.auditByIdInDB(id, auditDTO);
        }

        return share;

    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        this.shareMapper.updateByPrimaryKeySelective(share);

        // 4. 把share写到缓存
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        this.auditByIdInDB(id, auditDTO);
        this.rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享")
                        .build()
        );

    }


}
