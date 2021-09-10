package com.suny.rpc.nettyrpc.core.server;

import com.suny.rpc.nettyrpc.core.codec.MessageDecoder;
import com.suny.rpc.nettyrpc.core.codec.MessageEncoder;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author sunjianrong
 * @date 2021-09-10 15:09
 */
@Slf4j
public class NettyServer {
    public static final int PORT = 50001;

    private SerializerFactory serializerFactory;


    public NettyServer(SerializerFactory serializerFactory) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup defaultEventExecutorGroup = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new MessageEncoder(serializerFactory));
                        pipeline.addLast(new MessageDecoder(serializerFactory));
                        pipeline.addLast(new RpcServerSimpleChannelInboundHandlerImpl());

                    }
                });

        final ChannelFuture future;
        try {
            future = bootstrap.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty 启动失败", e);
        } finally {
            log.info("Netty 服务开始关闭");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            defaultEventExecutorGroup.shutdownGracefully();
        }

    }
}
