package com.suny.rpc.nettyrpc.core.annotations;

import java.lang.annotation.*;

/**
 * 服务引用注解. 用于代替 @Autowired . 参考 dubbo @reference
 *
 * @author sunjianrong
 * @date 2021/8/21 下午5:43
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Reference {
}
