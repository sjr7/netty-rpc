package com.suny.rpc.nettyrpc.core.client;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import com.suny.rpc.nettyrpc.core.model.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunjianrong
 * @date 2021-09-09 13:49
 */
@Slf4j
public class RpcClientChannelInboundHandlerImpl extends SimpleChannelInboundHandler<Packet> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("客户端收到消息: [{}]", msg.toString());

        final PacketType packetType = msg.getPacketType();

        if (packetType == PacketType.HEART_BEAT) {
            log.info("心跳帧 [{}]", msg.getData());
        } else {
            // 收到服务端应答
            final RpcResponse response = (RpcResponse) msg.getData();
            RequestFutureManager.removeAndComplete(response);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端处理错误: ", cause);
        ctx.close();
    }
}
