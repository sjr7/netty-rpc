package com.suny.rpc.nettyrpc.core.registry.impl;

import com.suny.rpc.nettyrpc.core.enums.RegistryCenterType;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceUnRegistryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Redis 服务
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:21
 */
@Service
@Slf4j
public class RedisRpcServiceRegistryImpl extends AbstractRpcServiceRegistry {


    @Override
    void doRegister(RpcServiceRegistryParam registryParam) {
        // todo redis 节点注册
    }

    @Override
    void doUnRegister(RpcServiceUnRegistryParam unRegistryParam) {
        // redis 节点反注册
    }

    @Override
    public RegistryCenterType getRegistryCenterType() {
        return RegistryCenterType.REDIS;
    }
}
