package com.suny.rpc.nettyrpc.server;

import com.suny.rpc.nettyrpc.core.annotations.RpcServiceScan;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import com.suny.rpc.nettyrpc.core.server.NettyServer;
import com.suny.rpc.nettyrpc.core.server.NettyServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@RpcServiceScan(basePackages = "com.suny.rpc.nettyrpc.**")
@SpringBootApplication
public class RpcServerApplication implements CommandLineRunner {

    @Autowired
    private SerializerFactory serializerFactory;

    @Autowired
    private NettyServerProperties nettyServerProperties;

    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer(nettyServerProperties.getServerPort(), serializerFactory);
        new CountDownLatch(1).await();
    }
}
