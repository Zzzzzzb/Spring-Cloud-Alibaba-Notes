package com.stackingrule.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestControllerBlockHandlerClass {

    /**
     * <h2>处理限流或者降级</h2>
     * @param a
     * @param e
     * @return
     */
    public static String block(String a, BlockException e) {
        log.warn("限流，或者降级了", e);
        return "限流，或者降级了 block";
    }
}
