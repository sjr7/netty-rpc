package com.suny.rpc.nettyrpc.core.annotations;

import com.suny.rpc.nettyrpc.core.spring.RpcBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 参考 spring @ComponentScan
 *
 * @author sunjianrong
 * @date 2021/8/21 下午5:53
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcBeanRegistrar.class)
@Documented
public @interface RpcServiceScan {
    String[] basePackages() default {};
}
