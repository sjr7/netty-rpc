package com.suny.rpc.nettyrpc.core.server;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import com.suny.rpc.nettyrpc.core.model.packet.HeartBeatPacket;
import com.suny.rpc.nettyrpc.core.model.packet.Packet;
import com.suny.rpc.nettyrpc.core.process.RpcRequestProcessor;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunjianrong
 * @date 2021-09-10 15:27
 */
@Slf4j
public class RpcServerSimpleChannelInboundHandlerImpl extends SimpleChannelInboundHandler<Packet> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        final PacketType packetType = msg.getPacketType();

        log.info("服务端收到请求 {} . 请求体: {}", packetType, msg);

        if (packetType == PacketType.HEART_BEAT) {
            // 应答心跳
            final HeartBeatPacket heartBeatPacket = new HeartBeatPacket();
            heartBeatPacket.pong();
            ctx.writeAndFlush(heartBeatPacket).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

        } else if (packetType == PacketType.RPC_REQUEST) {
//            final RpcRequestPacket rpcRequestPacket = (RpcRequestPacket) msg;
//            final RpcRequest rpcRequest = rpcRequestPacket.getRpcRequest();

            final RpcRequest rpcRequest = (RpcRequest) msg;

            final Object result = RpcRequestProcessor.process(rpcRequest);

            final RpcResponse rpcResponse = new RpcResponse(rpcRequest.getSequence(), result);
//            final RpcResponsePacket rpcResponsePacket = new RpcResponsePacket();
//            rpcResponsePacket.setRpcResponse(rpcResponse);

            ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            log.info("服务端处理请求 {}-{} 完毕. 应答结果: {}", rpcRequest.getSequence(), packetType, rpcResponse);
        }

    }
}
