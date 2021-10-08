package com.suny.rpc.nettyrpc.core.spring;

import com.suny.rpc.nettyrpc.core.annotations.Reference;
import com.suny.rpc.nettyrpc.core.annotations.Service;
import com.suny.rpc.nettyrpc.core.client.RpcClientProxy;
import com.suny.rpc.nettyrpc.core.network.RpcRequestSender;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistry;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import com.suny.rpc.nettyrpc.core.server.NettyServerProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.InetAddress;

/**
 * Rpc bean 处理
 *
 * @author sunjianrong
 * @date 2021/8/22 下午3:20
 */
@Component
@Slf4j
public class RpcBeanProcessor implements BeanPostProcessor {

    private final RpcRequestSender requestSender;
    private final RpcServiceRegistry rpcServiceRegistry;
    private final NettyServerProperties nettyServerProperties;

    public RpcBeanProcessor(RpcServiceRegistry rpcServiceRegistry, RpcRequestSender requestSender, NettyServerProperties nettyServerProperties) {
        this.rpcServiceRegistry = rpcServiceRegistry;
        this.requestSender = requestSender;
        this.nettyServerProperties = nettyServerProperties;
    }


    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // bean 初始完毕,注册服务
        Service rpcServiceAnnotation = bean.getClass().getAnnotation(Service.class);

        if (rpcServiceAnnotation == null) {
            return bean;
        }

        RpcServiceRegistryParam registryParam = new RpcServiceRegistryParam();
        registryParam.setServiceName(bean.getClass().getInterfaces()[0].getCanonicalName());
        registryParam.setIp(InetAddress.getLocalHost().getHostAddress());
        registryParam.setPort(nettyServerProperties.getServerPort());
        registryParam.setRpcBean(bean);


        rpcServiceRegistry.register(registryParam);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 处理 rpc reference 注入
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();

        for (Field field : declaredFields) {
            Reference annotation = field.getAnnotation(Reference.class);
            if (annotation == null) {
                continue;
            }

            log.info("【@Reference 注入】{}.{}", beanClass.getName(), field.getName());
            // 将代理过后的 bean 注入到字段中

            final RpcClientProxy rpcClientProxy = new RpcClientProxy(requestSender);
            final Object proxy = rpcClientProxy.getProxy(field.getType());
            field.setAccessible(true);
            try {
                field.set(bean, proxy);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("属性" + beanName + field.getName() + "赋值失败");
            }

        }

        return bean;
    }
}
