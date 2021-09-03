package com.suny.rpc.nettyrpc.core.discovery.impl;

import com.suny.rpc.nettyrpc.core.discovery.RpcServiceDiscovery;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author sunjianrong
 * @date 2021-09-03 17:11
 */
@Slf4j
public abstract class AbstractRpcServiceDiscovery implements RpcServiceDiscovery {
    @Override
    public List<String> getServiceInstanceList(String serviceName) {
        return doGetServiceInstanceList(serviceName);
    }

    abstract List<String> doGetServiceInstanceList(String serviceName);

    @Override
    public String getServiceInstance(String serviceName) {
        final List<String> list = doGetServiceInstanceList(serviceName);
        if (list == null) {
            return null;
        }

        return list.stream().findFirst().orElse(null);
    }

}
