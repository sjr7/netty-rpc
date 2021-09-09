package com.suny.rpc.nettyrpc.core.client;

import com.suny.rpc.nettyrpc.core.codec.MessageDecoder;
import com.suny.rpc.nettyrpc.core.codec.MessageEncoder;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author sunjianrong
 * @date 2021-09-09 11:10
 */
@Slf4j
public class NettyClient {


    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;


    public NettyClient(SerializerFactory serializerFactory) {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new MessageEncoder(serializerFactory));
                        pipeline.addLast(new MessageDecoder(serializerFactory));
                        pipeline.addLast(new RpcClientChannelInboundHandlerImpl());
                    }
                });

    }

    @SneakyThrows
    public Channel connect(InetSocketAddress address) {
        CompletableFuture<Channel> future = new CompletableFuture<>();
        bootstrap.connect(address).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                log.info("连接 {}成功", address.toString());
                future.complete(f.channel());
            } else {
                throw new IllegalStateException();
            }
        });

        return future.get();
    }


}
