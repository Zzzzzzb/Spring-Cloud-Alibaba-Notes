package com.stackingrule.contentcenter.service.content;

import com.stackingrule.contentcenter.dao.content.ShareMapper;
import com.stackingrule.contentcenter.domain.dto.content.ShareAuditDTO;
import com.stackingrule.contentcenter.domain.dto.content.ShareDTO;
import com.stackingrule.contentcenter.domain.dto.user.UserDTO;
import com.stackingrule.contentcenter.domain.entity.content.Share;
import com.stackingrule.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;

    private final RestTemplate restTemplate;

    private final UserCenterFeignClient userCenterFeignClient;


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
        // 2.审核资源，将状态设置为PASS/REJECT
        share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);
        // 3. 如果是PASS，给发布人添加积分
        userCenterFeignClient.add


    }



}
