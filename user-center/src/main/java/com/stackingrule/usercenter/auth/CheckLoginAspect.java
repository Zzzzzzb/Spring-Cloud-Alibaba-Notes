package com.stackingrule.usercenter.auth;

import com.stackingrule.usercenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckLoginAspect {

    private final JwtOperator jwtOperator;

    @Around("@annotation(com.stackingrule.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) {

        try {
            // 1.从header获取token
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = ((ServletRequestAttributes) requestAttributes);
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("X-Token");

            // 2.校验token是否合法&是否过期；
            Boolean isValid = jwtOperator.validateToken(token);
            if (isValid) {
                throw new SecurityException("token不合法!!!");
            }

            // 3.如果校验成功，将用户信息设置到request的attributes中
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickname", claims.get("wxNickname"));
            request.setAttribute("role", claims.get("role"));

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("token不合法!!!");
        }

    }
}
