package com.suny.rpc.nettyrpc.core.registry.param;

import lombok.Data;

/**
 * Rpc 提供者注册参数
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:25
 */
@Data
public class RpcServiceRegistryParam {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * Rpc bean 对象
     */
    private Object rpcBean;


}
