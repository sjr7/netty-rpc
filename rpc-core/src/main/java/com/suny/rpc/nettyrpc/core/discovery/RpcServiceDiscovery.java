package com.suny.rpc.nettyrpc.core.discovery;

import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午5:40
 */
public interface RpcServiceDiscovery {

    /**
     * 拉取指定服务地址
     *
     * @param serviceName 服务名称
     * @return 服务地址集合
     */
    ServiceAddress lookupService(String serviceName);


    /**
     * 拉取所有服务信息
     *
     * @return 所有服务信息
     */
    List<ServiceAddress> lookupAllService();
}
