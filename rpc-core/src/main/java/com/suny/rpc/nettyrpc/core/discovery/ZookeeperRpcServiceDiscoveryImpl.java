package com.suny.rpc.nettyrpc.core.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/25 下午11:05
 */
@Service
@Slf4j
@Primary
public class ZookeeperRpcServiceDiscoveryImpl implements RpcServiceDiscovery {

    @Override
    public ServiceAddress lookupService(String serviceName) {
        return null;
    }

    @Override
    public List<ServiceAddress> lookupAllService() {
        return null;
    }
}
