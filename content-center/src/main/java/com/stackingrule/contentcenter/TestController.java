package com.stackingrule.contentcenter;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.stackingrule.contentcenter.dao.content.ShareMapper;
import com.stackingrule.contentcenter.domain.dto.user.UserDTO;
import com.stackingrule.contentcenter.domain.entity.content.Share;

import com.stackingrule.contentcenter.feignclient.TestBaiduFeignClient;
import com.stackingrule.contentcenter.feignclient.TestUserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    // @Autowired(required = false)
    @Resource
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;

    @GetMapping("/test")
    public List<Share> testInsert() {
        // 1. 做插入
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("Allen");
        share.setBuyCount(1);

        this.shareMapper.insertSelective(share);

        // 2. 做查询: 查询当前数据库所有的share  select * from share ;
        List<Share> shares = this.shareMapper.selectAll();

        return shares;
    }

    /**
     * <h2>测试服务发现，证明内容中心能找到用户中心</h2>
     * @return
     */
    @GetMapping("/test2")
    public List<ServiceInstance> setDiscoveryClient() {

        // 查询指定服务的所有实例信息
        return this.discoveryClient.getInstances("user-center");

    }

    @GetMapping("/test-get")
    public UserDTO query(UserDTO userDTO) {
        return testUserCenterFeignClient.query(userDTO);
    }

    @GetMapping("/baidu")
    public String baiduIndex() {
        return this.testBaiduFeignClient.index();
    }


    @GetMapping("test-hot")
    @SentinelResource("hot")
    public String testHot(@RequestParam(required = false) String a,
                          @RequestParam(required = false) String b) {
        return a + " " + b;
    }



    @GetMapping("test-add-flow-rule")
    public String testHot() {
        this.initFlowQpsRule();
        return "success";
    }

    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/shares/1");

        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(@RequestParam(required = false) String a) {
        // 定义一个sentinel保护资源，名称为test-sentinel-api
        Entry entry = null;
        try {
            entry = SphU.entry("test-sentinel-api");
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a不能为空!");
            }
            return a;
        }
        // 如果被保护的资源被限流或降级了，会抛出BlockException
        catch (BlockException e) {
            log.warn("限流或降级了：", e);
            return "限流或降级了!";
        }
        finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }


}
