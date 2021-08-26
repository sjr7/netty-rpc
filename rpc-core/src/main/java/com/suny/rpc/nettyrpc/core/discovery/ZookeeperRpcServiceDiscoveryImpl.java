package com.suny.rpc.nettyrpc.core.discovery;

import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

        List<String> childrenNodes = ZookeeperHelper.getChildrenNodes(serviceName);
        if (CollectionUtils.isEmpty(childrenNodes)) {
            throw new RuntimeException("未找到" + serviceName + "服务节点");
        }

        // todo 负载
        ServiceAddress serviceAddress = new ServiceAddress();
        serviceAddress.setServiceName(serviceName);
        serviceAddress.setServiceAddressList(childrenNodes);
        return serviceAddress;
    }

    @Override
    public List<ServiceAddress> lookupAllService() {
        return null;
    }
}
