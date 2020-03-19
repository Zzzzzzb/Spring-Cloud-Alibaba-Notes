package com.stackingrule.usercenter;

import com.stackingrule.usercenter.rocketmq.MySink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

// 扫描mybatis哪些包里面的接口
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.stackingrule.usercenter.dao")
@EnableBinding({Sink.class, MySink.class})
@SpringBootApplication
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
