package com.suny.rpc.nettyrpc.core.client;

import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import com.suny.rpc.nettyrpc.core.network.RpcRequestSender;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理
 *
 * @author sunjianrong
 * @date 2021-09-09 10:48
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private final RpcRequestSender rpcRequestSender;

    public RpcClientProxy(RpcRequestSender rpcRequestSender) {
        this.rpcRequestSender = rpcRequestSender;
    }


    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setSequence(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameterType(method.getParameterTypes());
        rpcRequest.setParameters(args);

        final RpcResponse rpcResponse = rpcRequestSender.sendRpcRequest(rpcRequest);
        return rpcResponse.getResult();
    }
}
