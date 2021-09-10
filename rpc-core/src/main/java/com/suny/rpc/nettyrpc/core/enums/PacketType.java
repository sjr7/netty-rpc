package com.suny.rpc.nettyrpc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunjianrong
 * @date 2021-09-09 16:11
 */
@AllArgsConstructor
@Getter
public enum PacketType {

    /**
     * 心跳包
     */
    HEART_BEAT((byte) 0),

    /**
     * 请求包
     */
    RPC_REQUEST((byte) 1),

    /**
     * 应答包
     */
    RPC_RESPONSE((byte) 2);

    private byte type;
}
