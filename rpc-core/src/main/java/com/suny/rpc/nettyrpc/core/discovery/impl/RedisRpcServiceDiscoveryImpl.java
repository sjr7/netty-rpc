package com.suny.rpc.nettyrpc.core.discovery.impl;

import com.suny.rpc.nettyrpc.core.discovery.RpcServiceDiscovery;
import com.suny.rpc.nettyrpc.core.discovery.ServiceAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午10:02
 */
@Service
@Slf4j
public class RedisRpcServiceDiscoveryImpl implements RpcServiceDiscovery {
    @Override
    public ServiceAddress lookupService(String serviceName) {
        return null;
    }

    @Override
    public List<ServiceAddress> lookupAllService() {
        return null;
    }
}
