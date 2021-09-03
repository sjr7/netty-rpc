package com.suny.rpc.nettyrpc.core.discovery.impl;

import com.suny.rpc.nettyrpc.core.enums.RegistryCenterType;
import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sunjianrong
 * @date 2021/8/25 下午11:05
 */
@Service
@Slf4j
@Primary
public class ZookeeperRpcServiceDiscoveryImpl extends AbstractRpcServiceDiscovery {

    private final ZookeeperHelper zookeeperHelper;

    public ZookeeperRpcServiceDiscoveryImpl(ZookeeperHelper zookeeperHelper) {
        this.zookeeperHelper = zookeeperHelper;
    }


    @Override
    List<String> doGetServiceInstanceList(String serviceName) {
        List<String> childrenNodes = zookeeperHelper.getChildrenNodes(serviceName);
        if (CollectionUtils.isEmpty(childrenNodes)) {
            // throw new RuntimeException("未找到" + serviceName + "服务节点");
            return Collections.emptyList();
        }

        return childrenNodes;
    }


    @Override
    public Map<String, List<String>> getAllServiceInstance() {

        return null;
    }


    @Override
    public RegistryCenterType getRegistryCenterType() {
        return RegistryCenterType.ZOOKEEPER;
    }
}
