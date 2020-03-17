package com.stackingrule.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

@Slf4j

public class NacosWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    public NacosWeightedRule() {

    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // 读取配置文件并初始化
    }

    @Override
    public Server choose(Object o) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // log.info("loadBalancer = {}", loadBalancer);
            // 想要请求的微服务的名称
            String name = loadBalancer.getName();
            // 负载均衡算法
            // 拿到服务发现相关的API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            // nacos client自动通过基于权重的负载均衡算法，给我们选择一个实例。
            Instance instance = namingService.selectOneHealthyInstance(name);

            log.info("选择的实例是：port = {}, instance = {}", instance.getPort(), instance);

            return new NacosServer(instance);

        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }

    }
}
