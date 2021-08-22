package com.suny.rpc.nettyrpc.core.registry.impl;

import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistry;
import com.suny.rpc.nettyrpc.core.discovery.ServiceAddress;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceUnRegistryParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Zookeeper 服务
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:19
 */
@Component
public class ZookeeperRpcServiceRegistryImpl implements RpcServiceRegistry {

    @Override
    public void register(RpcServiceRegistryParam registryParam) {

    }

    @Override
    public void unRegister(RpcServiceUnRegistryParam unRegistryParam) {

    }

}
