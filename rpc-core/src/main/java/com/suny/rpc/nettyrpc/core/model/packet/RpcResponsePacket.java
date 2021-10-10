package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sunjianrong
 * @date 2021-09-09 16:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class RpcResponsePacket extends Packet {

    private RpcResponse rpcResponse;

    @Override
    public PacketType getPacketType() {
        return PacketType.RPC_RESPONSE;
    }
}
