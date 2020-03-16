package com.stackingrule.contentcenter.service.content;

import com.stackingrule.contentcenter.dao.content.ShareMapper;
import com.stackingrule.contentcenter.domain.dto.content.ShareDTO;
import com.stackingrule.contentcenter.domain.dto.user.UserDTO;
import com.stackingrule.contentcenter.domain.entity.content.Share;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;

    private final RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人 id
        Integer userId = share.getUserId();

        // 用户中心所有实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        String targetURL = instances.stream()
                .map(instance -> instance.getUri().toString() + "/users/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("当前没有实例!"));

        log.info("请求目标地址： {}", targetURL );

        UserDTO userDTO = restTemplate.getForObject(
                targetURL,
                UserDTO.class,
                userId
        );

        // 消息装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }


}
