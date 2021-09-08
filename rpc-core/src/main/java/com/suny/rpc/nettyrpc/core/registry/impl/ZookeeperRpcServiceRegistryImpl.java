package com.suny.rpc.nettyrpc.core.registry.impl;

import com.suny.rpc.nettyrpc.core.enums.RegistryCenterType;
import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceUnRegistryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Zookeeper 服务
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:19
 */
@Primary
@Component
@Slf4j
public class ZookeeperRpcServiceRegistryImpl extends AbstractRpcServiceRegistry {

    private final ZookeeperHelper zookeeperHelper;

    public ZookeeperRpcServiceRegistryImpl(ZookeeperHelper zookeeperHelper) {
        this.zookeeperHelper = zookeeperHelper;
    }

    @Override
    public RegistryCenterType getRegistryCenterType() {
        return RegistryCenterType.ZOOKEEPER;
    }

    @Override
    void doRegister(RpcServiceRegistryParam registryParam) {
        zookeeperHelper.createServiceInstanceNode(registryParam.getServiceName());
    }

    @Override
    void doUnRegister(RpcServiceUnRegistryParam unRegistryParam) {
        // final InetSocketAddress inetSocketAddress = new InetSocketAddress(unRegistryParam.getIp(), unRegistryParam.getPort());
        // zookeeperHelper.removeNode(inetSocketAddress);
    }

}
