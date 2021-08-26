package com.suny.rpc.nettyrpc.core.manager;

import com.suny.rpc.nettyrpc.core.annotations.Service;
import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import com.suny.rpc.nettyrpc.core.registry.RpcServiceRegistryParam;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/26 下午10:47
 */
@Service
@Slf4j
public class ZookeeperRpcServiceImpl implements RpcService {


    @Override
    public void register(RpcServiceRegistryParam param) {
        ZookeeperHelper.createNode(ZookeeperHelper.BASE_RPC_PATH + "/" + param.getServiceName());
    }

    @Override
    public String getServiceAddress(String serviceName) {
        List<String> childrenNodes = ZookeeperHelper.getChildrenNodes(serviceName);
        return childrenNodes.stream().findFirst().orElse(null);
    }

    @Override
    public void unregister(String serviceName, InetSocketAddress address) {
        ZookeeperHelper.removeNode(address);
    }
}
