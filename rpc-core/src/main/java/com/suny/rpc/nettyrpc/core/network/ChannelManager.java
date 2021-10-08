package com.suny.rpc.nettyrpc.core.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunjianrong
 * @date 2021-09-09 17:03
 */
@Slf4j
public class ChannelManager {

    public static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    private static Bootstrap b;

    public static void setBootstrap(Bootstrap bootstrap) {
        b = bootstrap;
    }


    @SneakyThrows
    private static Channel connect(InetSocketAddress address) {
        CompletableFuture<Channel> future = new CompletableFuture<>();
        b.connect(address).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                log.info("连接 {} 成功", address.toString());
                future.complete(f.channel());
            } else {
                throw new IllegalStateException();
            }
        });

        return future.get();
    }

    public static Channel get(InetSocketAddress address) {
        final String s = address.toString();
//        if (!CHANNEL_MAP.containsKey(s)) {
//            return null;
//        }

        final Channel channel = CHANNEL_MAP.get(s);
        if (channel == null) {
            return connect(address);
        }

        if (channel.isActive()) {
            return channel;
        } else {
            CHANNEL_MAP.remove(s);
            return null;
        }
    }


    public static void set(InetSocketAddress address, Channel channel) {
        final String s = address.toString();
        CHANNEL_MAP.put(s, channel);
    }

    public static void remove(InetSocketAddress address) {
        final String s = address.toString();
        CHANNEL_MAP.remove(s);
    }

}
