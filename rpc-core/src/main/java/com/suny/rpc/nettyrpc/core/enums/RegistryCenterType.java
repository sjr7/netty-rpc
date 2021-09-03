package com.suny.rpc.nettyrpc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注册中心
 *
 * @author sunjianrong
 * @date 2021-09-03 14:44
 */
@AllArgsConstructor
@Getter
public enum RegistryCenterType {

    /**
     * Zookeeper
     */
    ZOOKEEPER("Zookeeper"),

    /**
     * Redis
     */
    REDIS("Redis");

    private final String name;
}
