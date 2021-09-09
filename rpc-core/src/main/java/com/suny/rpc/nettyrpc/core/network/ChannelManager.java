package com.suny.rpc.nettyrpc.core.network;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunjianrong
 * @date 2021-09-09 17:03
 */
@Slf4j
public class ChannelManager {

    public static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static Channel get(InetSocketAddress address) {
        final String s = address.toString();
        if (!CHANNEL_MAP.containsKey(s)) {
            return null;
        }

        final Channel channel = CHANNEL_MAP.get(s);
        if (channel == null) {
            return null;
        }

        if (channel.isActive()) {
            return channel;
        } else {
            CHANNEL_MAP.remove(s);
            return null;
        }
    }

    private void connect(InetSocketAddress address) {
        // 重新连接
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
