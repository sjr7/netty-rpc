package com.suny.rpc.nettyrpc.core.registry.impl;

import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistry;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceUnRegistryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Redis 服务
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:21
 */
@Primary
@Service
@Slf4j
public class RedisRpcServiceRegistryImpl implements RpcServiceRegistry {

    @Override
    public void register(RpcServiceRegistryParam registryParam) {
        log.info("【RPC服务注册】{} >> {}:{}", registryParam.getServiceName(), registryParam.getIp(), registryParam.getPort());
    }

    @Override
    public void unRegister(RpcServiceUnRegistryParam unRegistryParam) {
        log.info("【RPC服务反注册】{} >> {}:{}", unRegistryParam.getServiceName(), unRegistryParam.getIp(), unRegistryParam.getPort());
    }

}
