package com.suny.rpc.nettyrpc.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class RpcServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new CountDownLatch(1).await();
    }
}
