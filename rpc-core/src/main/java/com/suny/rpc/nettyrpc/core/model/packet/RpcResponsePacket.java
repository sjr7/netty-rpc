package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;

/**
 * @author sunjianrong
 * @date 2021-09-09 16:18
 */
public class RpcResponsePacket extends Packet {
    @Override
    public PacketType getPacketType() {
        return PacketType.RESPONSE;
    }
}
