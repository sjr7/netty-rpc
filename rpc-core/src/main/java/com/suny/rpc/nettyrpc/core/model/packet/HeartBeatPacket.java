package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;

/**
 * @author sunjianrong
 * @date 2021-09-09 16:19
 */
public class HeartBeatPacket extends Packet {


    @Override
    public PacketType getPacketType() {
        return PacketType.HEART_BEAT;
    }
}
