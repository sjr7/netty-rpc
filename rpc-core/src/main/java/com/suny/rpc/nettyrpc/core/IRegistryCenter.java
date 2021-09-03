package com.suny.rpc.nettyrpc.core;

import com.suny.rpc.nettyrpc.core.enums.RegistryCenterType;

/**
 * 注册中心接口
 *
 * @author sunjianrong
 * @date 2021-09-03 17:14
 */
public interface IRegistryCenter {

    /**
     * 获取当前注册中心类型
     *
     * @return 当前注册中心类型
     */
    RegistryCenterType getRegistryCenterType();
}
