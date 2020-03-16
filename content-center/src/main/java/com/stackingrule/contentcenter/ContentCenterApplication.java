package com.stackingrule.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.stackingrule.contentcenter.dao")
@SpringBootApplication
public class ContentCenterApplication {

    // 在 spring 容器中创建一个对象，类型为RestTemplate， 名称为 restTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

}
