package com.suny.rpc.nettyrpc.core.server;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务端配置
 *
 * @author sunjianrong
 * @date 2021/10/8 下午9:54
 */
@Data
@Component
public class NettyServerProperties {

    @Value("${netty.server.port:50001}")
    private int serverPort;
}
