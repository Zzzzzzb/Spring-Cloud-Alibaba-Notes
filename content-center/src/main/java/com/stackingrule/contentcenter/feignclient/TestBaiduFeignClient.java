package com.stackingrule.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "baidu", url = "www.baidu.com")
public interface TestBaiduFeignClient {

    @GetMapping("")
    public String index();

}
