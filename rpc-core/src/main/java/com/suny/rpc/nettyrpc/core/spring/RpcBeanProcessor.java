package com.suny.rpc.nettyrpc.core.spring;

import com.suny.rpc.nettyrpc.core.annotations.Reference;
import com.suny.rpc.nettyrpc.core.annotations.Service;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistry;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
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

    private final RpcServiceRegistry rpcServiceRegistry;

    public RpcBeanProcessor(RpcServiceRegistry rpcServiceRegistry) {
        this.rpcServiceRegistry = rpcServiceRegistry;
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
        registryParam.setPort(5000);
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

        }

        return null;
    }
}
