package com.suny.rpc.nettyrpc.core.manager;

import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import com.suny.rpc.nettyrpc.core.registry.param.RpcServiceRegistryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/26 下午10:47
 */
@Service
@Slf4j
public class ZookeeperRpcServiceImpl implements RpcService {

    private final ZookeeperHelper zookeeperHelper;

    public ZookeeperRpcServiceImpl(ZookeeperHelper zookeeperHelper) {
        this.zookeeperHelper = zookeeperHelper;
    }

    @Override
    public void register(RpcServiceRegistryParam param) {
        zookeeperHelper.createNode(ZookeeperHelper.BASE_RPC_PATH + "/" + param.getServiceName());
    }

    @Override
    public String getServiceAddress(String serviceName) {
        List<String> childrenNodes = zookeeperHelper.getChildrenNodes(serviceName);
        return childrenNodes.stream().findFirst().orElse(null);
    }

    @Override
    public void unregister(String serviceName, InetSocketAddress address) {
        zookeeperHelper.removeNode(address);
    }
}
