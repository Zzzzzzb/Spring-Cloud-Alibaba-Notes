package com.stackingrule.usercenter.rocketmq;

import com.stackingrule.usercenter.dao.bonus.BonusEventLogMapper;
import com.stackingrule.usercenter.dao.user.UserMapper;
import com.stackingrule.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.stackingrule.usercenter.domain.entity.bonus.BonusEventLog;
import com.stackingrule.usercenter.domain.entity.user.User;
import com.stackingrule.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusStreamConsumer {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;
    private final UserService userService;

    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO message) {

        message.setEvent("CONTRIBUTE");
        message.setDescription("投稿加积分..");
        this.userService.addBonus(message);

    }

}
