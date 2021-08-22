package com.suny.rpc.nettyrpc.core.spring;

import com.suny.rpc.nettyrpc.core.annotations.RpcServiceScan;
import com.suny.rpc.nettyrpc.core.annotations.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 处理 rpc bean 注册
 *
 * @author sunjianrong
 * @date 2021/8/21 下午5:46
 */
@Slf4j
public class RpcBeanRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    /**
     * rpc-core 模块包全路径限定名
     */
    public static final String CORE_MODULE_PACKAGE = "com.suny.rpc.nettyrpc.core";

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 扫描自定义的 @service 注解
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RpcServiceScan.class.getName()));
        String[] rpcPackages = new String[0];

        if (annotationAttributes != null) {
            rpcPackages = annotationAttributes.getStringArray("basePackages");
        }

        // 保险
        if (rpcPackages.length == 0) {
            // throw new RuntimeException("必须指定 rpc 包扫描路径");
            rpcPackages = new String[]{((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass().getPackage().getName()};
        }

        RpcBeanDefinitionScanner serviceScanner = new RpcBeanDefinitionScanner(registry, Service.class);

        // 核心模块下的 spring bean 也需要扫描
        serviceScanner.scan(CORE_MODULE_PACKAGE);

        // 自定义扫描注解
        int rpcServiceNum = serviceScanner.scan(rpcPackages);
        log.info("Rpc Service 注册数量 [{}]", rpcServiceNum);

    }


    /**
     * 扫描策略
     */
    static class RpcBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

        public RpcBeanDefinitionScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> clazz) {
            super(registry);
            super.addIncludeFilter(new AnnotationTypeFilter((clazz)));
        }
    }


}
