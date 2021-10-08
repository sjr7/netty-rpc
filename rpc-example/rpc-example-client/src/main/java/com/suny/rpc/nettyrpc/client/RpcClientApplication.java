package com.suny.rpc.nettyrpc.client;

import com.suny.rpc.nettyrpc.core.annotations.RpcServiceScan;
import com.suny.rpc.nettyrpc.core.client.NettyClient;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@RpcServiceScan
@SpringBootApplication(scanBasePackages = {"com.suny.rpc.nettyrpc.**"})
public class RpcClientApplication implements CommandLineRunner {

    @Autowired
    private SerializerFactory serializerFactory;

    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        NettyClient nettyClient = new NettyClient(serializerFactory);

        new CountDownLatch(1).await();
    }
}
