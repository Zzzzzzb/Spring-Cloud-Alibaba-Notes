package com.stackingrule.usercenter.rocketmq;

import com.stackingrule.usercenter.dao.bonus.BonusEventLogMapper;
import com.stackingrule.usercenter.dao.user.UserMapper;
import com.stackingrule.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.stackingrule.usercenter.domain.entity.bonus.BonusEventLog;
import com.stackingrule.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO msgDTO) {
        // 当收到消息时执行的任务
        // 1.为用户加积分
        Integer userId = msgDTO.getUserId();
        Integer bonus = msgDTO.getBonus();
        User user = this.userMapper.selectByPrimaryKey(userId);

        user.setBonus(user.getBonus() + bonus);
        this.userMapper.updateByPrimaryKeySelective(user);
        // 2.记录日志到bonus_log表中
        this.bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event(msgDTO.getEvent())
                        .createTime(new Date())
                        .description(msgDTO.getDescription())
                        .build()
        );
        log.info("积分添加完毕...");
    }
}
