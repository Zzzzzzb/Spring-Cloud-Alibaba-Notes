package com.stackingrule.contentcenter.configuration;

/**
 * feign 配置类
 * 这个类别加@Configuration注解
 */

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class GlobalFeignConfiguration {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }

}
