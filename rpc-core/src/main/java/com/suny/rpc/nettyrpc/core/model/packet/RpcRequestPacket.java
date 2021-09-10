package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RpcRequestPacket extends Packet {

    private RpcRequest rpcRequest;

    @Override
    public PacketType getPacketType() {
        return PacketType.RPC_REQUEST;
    }
}
