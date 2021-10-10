package com.suny.rpc.nettyrpc.core.model;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.packet.Packet;
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
@EqualsAndHashCode(callSuper = true)
@ToString
@Setter
public class RpcResponse extends Packet implements Serializable {

    private static final long serialVersionUID = 2964937493568971128L;

    /**
     * 流水号
     */
    private String sequence;

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

    /**
     * 只提供给反序列化使用
     */
    @Deprecated
    public RpcResponse() {
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.RPC_RESPONSE;
    }
}
