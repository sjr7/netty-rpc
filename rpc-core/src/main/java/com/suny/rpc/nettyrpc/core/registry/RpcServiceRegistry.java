package com.suny.rpc.nettyrpc.core.registry;

import com.suny.rpc.nettyrpc.core.IRegistryCenter;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceUnRegistryParam;

/**
 * RPC 注册接口
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:05
 */
public interface RpcServiceRegistry extends IRegistryCenter {

    /**
     * 注册服务
     *
     * @param registryParam 注册参数
     */
    void register(RpcServiceRegistryParam registryParam);

    /**
     * 反注册参数
     *
     * @param unRegistryParam 反注册参数
     */
    void unRegister(RpcServiceUnRegistryParam unRegistryParam);
}
