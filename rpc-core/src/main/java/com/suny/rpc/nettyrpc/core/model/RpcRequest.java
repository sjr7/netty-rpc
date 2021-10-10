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
 * @date 2021-09-08 18:03
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RpcRequest extends Packet implements Serializable {

    private static final long serialVersionUID = 6290632141408825905L;

    /**
     * 请求流水号
     */
//    @Setter(AccessLevel.NONE)
    private String sequence;


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


    @Override
    public PacketType getPacketType() {
        return PacketType.RPC_REQUEST;
    }

}
