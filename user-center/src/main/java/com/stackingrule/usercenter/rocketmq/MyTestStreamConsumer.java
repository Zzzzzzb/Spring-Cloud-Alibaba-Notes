package com.stackingrule.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyTestStreamConsumer {

   @StreamListener(MySink.MY_INPUT)
   public void receive(String messageBody) {
       log.info("自定义接口消费：通过stram收到了消息: messageBody = {}", messageBody);

   }
}
