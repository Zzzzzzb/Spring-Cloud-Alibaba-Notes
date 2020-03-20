package com.stackingrule.contentcenter.feignclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TokenRelayRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // 1.获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = ((ServletRequestAttributes) requestAttributes);
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");

        // 2.将token传递
        if (StringUtils.isNotBlank(token)) {
            requestTemplate.header("X-Token");
        }

    }
}
