package com.stackingrule.contentcenter;


import com.stackingrule.contentcenter.configuration.GlobalFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.stackingrule.contentcenter.dao")
@EnableFeignClients(defaultConfiguration = GlobalFeignConfiguration.class)
@SpringBootApplication
public class ContentCenterApplication {

    // 在 spring 容器中创建一个对象，类型为RestTemplate， 名称为 restTemplate
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

}
