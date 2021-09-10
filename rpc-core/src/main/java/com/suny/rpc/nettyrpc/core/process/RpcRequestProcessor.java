package com.suny.rpc.nettyrpc.core.process;

import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunjianrong
 * @date 2021-09-10 15:33
 */
@Slf4j
public class RpcRequestProcessor {

    private static final Map<String, Object> BEAN_MAP = new ConcurrentHashMap<>();

    public static void addRpcBean(String serviceName, Object bean) {
        BEAN_MAP.put(serviceName, bean);
    }

    public static Object getBean(String serviceName) {
        return BEAN_MAP.get(serviceName);
    }

    public static void remove(String serviceName) {
        BEAN_MAP.remove(serviceName);
    }

    /**
     * 处理 rpc 请求
     *
     * @param rpcRequest rpc 请求参数
     * @return rpc请求结果
     */
    public static Object process(RpcRequest rpcRequest) {
        try {
            final String className = rpcRequest.getClassName();
            final Object bean = getBean(className);
            if (bean == null) {
                throw new RuntimeException("未找到 " + className + " 对应实例");
            }

            final Method method = bean.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterType());
            final Object invoke = method.invoke(bean, rpcRequest.getParameters());
            log.debug("RPC服务 {}.{} 调用成功", rpcRequest.getClassName(), rpcRequest.getMethodName());
            return invoke;
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("RPC调用失败", e);
        }
    }

}
