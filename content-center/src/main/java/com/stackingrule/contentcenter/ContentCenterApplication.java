package com.stackingrule.contentcenter;


import com.stackingrule.contentcenter.configuration.GlobalFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Collections;

@MapperScan("com.stackingrule.contentcenter.dao")
@EnableFeignClients(defaultConfiguration = GlobalFeignConfiguration.class)
@EnableBinding({Source.class})
@SpringBootApplication
public class ContentCenterApplication {

    // 在 spring 容器中创建一个对象，类型为RestTemplate， 名称为 restTemplate
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(
                        new TestRestTemplateTokenRelayInterceptor()
                )
        );

        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

}
