package com.suny.rpc.nettyrpc.core.registry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 反注册参数
 *
 * @author sunjianrong
 * @date 2021/8/22 下午5:38
 */
@AllArgsConstructor
@Data
public class RpcServiceUnRegistryParam implements Serializable {

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
    private String port;
}
