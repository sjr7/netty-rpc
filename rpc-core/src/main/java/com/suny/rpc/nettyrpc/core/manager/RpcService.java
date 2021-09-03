package com.suny.rpc.nettyrpc.core.manager;

import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;

import java.net.InetSocketAddress;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午5:24
 */
public interface RpcService {


    /**
     * 注册
     *
     * @param param 注册参数
     */
    void register(RpcServiceRegistryParam param);


    /**
     * 服务地址
     *
     * @param serviceName 服务名
     * @return 可用服务地址
     */
    String getServiceAddress(String serviceName);


    /**
     * 取消注册
     *
     * @param serviceName 服务名
     * @param address     服务地址
     */
    void unregister(String serviceName, InetSocketAddress address);
}
