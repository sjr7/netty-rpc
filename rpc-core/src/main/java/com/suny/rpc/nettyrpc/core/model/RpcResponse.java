package com.suny.rpc.nettyrpc.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author sunjianrong
 * @date 2021-09-09 10:19
 */
@Getter
@EqualsAndHashCode
@ToString
@Setter
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 2964937493568971128L;

    /**
     * 流水号
     */
    private final String sequence;

    /**
     * 异常信息
     */
    private Throwable throwable;

    /**
     * 结果
     */
    private Object result;


    public RpcResponse(String sequence, Throwable throwable) {
        this.sequence = sequence;
        this.throwable = throwable;
    }

    public RpcResponse(String sequence, Object result) {
        this.sequence = sequence;
        this.result = result;
    }
}
