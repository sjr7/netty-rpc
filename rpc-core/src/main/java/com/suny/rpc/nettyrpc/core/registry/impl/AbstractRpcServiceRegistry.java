package com.suny.rpc.nettyrpc.core.registry.impl;

import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistry;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceUnRegistryParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunjianrong
 * @date 2021-09-03 14:47
 */
@Slf4j
public abstract class AbstractRpcServiceRegistry implements RpcServiceRegistry {

    @Override
    public void register(RpcServiceRegistryParam registryParam) {
        doRegister(registryParam);
        log.info("【{} RPC服务注册】{} >> {}:{}", getRegistryCenterType().getName(), registryParam.getServiceName(), registryParam.getIp(), registryParam.getPort());
    }

    abstract void doRegister(RpcServiceRegistryParam registryParam);

    @Override
    public void unRegister(RpcServiceUnRegistryParam unRegistryParam) {
        doUnRegister(unRegistryParam);
        log.info("【{} RPC服务反注册】{} >> {}:{}", getRegistryCenterType().getName(), unRegistryParam.getServiceName(), unRegistryParam.getIp(), unRegistryParam.getPort());
    }

    abstract void doUnRegister(RpcServiceUnRegistryParam unRegistryParam);
}
