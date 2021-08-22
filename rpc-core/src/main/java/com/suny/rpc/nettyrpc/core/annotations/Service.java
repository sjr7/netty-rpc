package com.suny.rpc.nettyrpc.core.annotations;

import java.lang.annotation.*;

/**
 * RPC 服务注解,用于代替 spring @service
 *
 * @author sunjianrong
 * @date 2021/8/21 下午5:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service {
}
