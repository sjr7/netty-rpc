package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:39
 */
public class RpcRequestPacket extends Packet {

    @Override
    public PacketType getPacketType() {
        return PacketType.REQUEST;
    }
}
