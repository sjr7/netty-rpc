package com.suny.rpc.nettyrpc.core.discovery;

import com.suny.rpc.nettyrpc.core.IRegistryCenter;

import java.util.List;
import java.util.Map;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午5:40
 */
public interface RpcServiceDiscovery extends IRegistryCenter {

    /**
     * 拉取指定服务地址集合
     *
     * @param serviceName 服务名称
     * @return 服务地址集合
     */
    List<String> getServiceInstanceList(String serviceName);

    /**
     * 获取一个服务实例
     *
     * @param serviceName 服务名称
     * @return 服务实例
     */
    String getServiceInstance(String serviceName);

    /**
     * 拉取所有服务信息
     *
     * @return 所有服务信息
     */
    Map<String, List<String>> getAllServiceInstance();

}
