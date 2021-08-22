package com.suny.rpc.nettyrpc.client;

import com.suny.rpc.nettyrpc.core.annotations.RpcServiceScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@RpcServiceScan
@SpringBootApplication
public class RpcClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new CountDownLatch(1).await();
    }
}
