package com.suny.rpc.nettyrpc.core.codec;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import com.suny.rpc.nettyrpc.core.model.packet.HeartBeatPacket;
import com.suny.rpc.nettyrpc.core.model.packet.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunjianrong
 * @date 2021-09-09 15:33
 */
public class PacketClassManager {

    private static final Map<Byte, Class<? extends Packet>> PACKET_CLASS_MAP = new HashMap<>();


    static {
        PACKET_CLASS_MAP.put(PacketType.HEART_BEAT.getType(), HeartBeatPacket.class);
        PACKET_CLASS_MAP.put(PacketType.RPC_REQUEST.getType(), RpcRequest.class);
        PACKET_CLASS_MAP.put(PacketType.RPC_RESPONSE.getType(), RpcResponse.class);
    }

    public static Class<? extends Packet> getPacketClass(byte packetType) {
        return PACKET_CLASS_MAP.get(packetType);
    }
}
