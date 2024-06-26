package com.suny.rpc.nettyrpc.core.network;

import com.suny.rpc.nettyrpc.core.client.RequestFutureManager;
import com.suny.rpc.nettyrpc.core.discovery.RpcServiceDiscovery;
import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author sunjianrong
 * @date 2021-09-09 11:05
 */
@Service
@Slf4j
public class NettyRpcRequestSenderImpl implements RpcRequestSender {

    @Autowired
    private RpcServiceDiscovery rpcServiceDiscovery;

    @SneakyThrows
    @Override
    public RpcResponse sendRpcRequest(RpcRequest rpcRequest) {
        CompletableFuture<RpcResponse> future = new CompletableFuture<>();

        // 查找地址
        final String className = rpcRequest.getClassName();
        final String serviceInstance = rpcServiceDiscovery.getServiceInstance(className);

        if (StringUtils.isBlank(serviceInstance)) {
            throw new RuntimeException(className + "暂无服务提供者");
        }

        String[] split = serviceInstance.split(":");
        // 查找 channel
        final Channel channel = ChannelManager.get(new InetSocketAddress(split[0], Integer.parseInt(split[1])));
        if (channel == null || !channel.isActive()) {
            throw new IllegalStateException();
        }


        RequestFutureManager.addFuture(rpcRequest.getSequence(), future);

//        RpcRequestPacket rpcRequestPacket = new RpcRequestPacket();
//        rpcRequestPacket.setRpcRequest(rpcRequest);


        channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                log.info("客户端消息发送成功! [{}]", rpcRequest);
            } else {
                f.channel().close();
                future.completeExceptionally(f.cause());
                log.error("客户端发送消息失败:", f.cause());
            }
        });


        return future.get();
    }
}
