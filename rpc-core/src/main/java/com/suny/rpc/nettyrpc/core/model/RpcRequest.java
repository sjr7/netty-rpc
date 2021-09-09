package com.suny.rpc.nettyrpc.core.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author sunjianrong
 * @date 2021-09-08 18:03
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 6290632141408825905L;

    /**
     * 请求流水号
     */
    @Setter(AccessLevel.NONE)
    private String sequence = UUID.randomUUID().toString();


    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;


    /**
     * 参数类型. 有序
     */
    private Class<?>[] parameterType;


    /**
     * 参数. 有序
     */
    private Object[] parameters;

}
