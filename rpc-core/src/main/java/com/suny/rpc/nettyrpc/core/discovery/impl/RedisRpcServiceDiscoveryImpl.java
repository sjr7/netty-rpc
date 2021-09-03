package com.suny.rpc.nettyrpc.core.discovery.impl;

import com.suny.rpc.nettyrpc.core.enums.RegistryCenterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午10:02
 */
@Service
@Slf4j
public class RedisRpcServiceDiscoveryImpl extends AbstractRpcServiceDiscovery {

    @Override
    public Map<String, List<String>> getAllServiceInstance() {
        return Collections.emptyMap();
    }


    @Override
    List<String> doGetServiceInstanceList(String serviceName) {
        // todo 待扩展
        return Collections.emptyList();
    }


    @Override
    public RegistryCenterType getRegistryCenterType() {
        return RegistryCenterType.REDIS;
    }
}
