package com.suny.rpc.nettyrpc.core.ext.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Zookeeper 辅助函数
 *
 * @author sunjianrong
 * @date 2021/8/25 下午11:10
 */
@Slf4j
public class ZookeeperHelper {

    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    public static final String BASE_RPC_PATH = "/rpc";
    private static volatile CuratorFramework zookeeperClient;

    private static final Map<String, List<String>> RPC_SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();

    public static List<String> getChildrenNodes(String rpcServiceName) {
        if (RPC_SERVICE_ADDRESS_MAP.containsKey(rpcServiceName)) {
            return RPC_SERVICE_ADDRESS_MAP.get(rpcServiceName);
        }

        List<String> res;
        String path = BASE_RPC_PATH + "/" + rpcServiceName;

        try {
            res = zookeeperClient.getChildren().forPath(path);
            RPC_SERVICE_ADDRESS_MAP.put(rpcServiceName, res);
        } catch (Exception e) {
            throw new RuntimeException("获取 Zookeeper " + path + " 子节点失败");
        }

        return res;
    }

    /**
     * @return zookeeper 操作客户端
     */
    public static CuratorFramework getZookeeperClient() {

        if (zookeeperClient != null && zookeeperClient.getState() == CuratorFrameworkState.STARTED) {
            return zookeeperClient;
        }

        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
        synchronized (zookeeperClient) {
            if (zookeeperClient != null) {
                zookeeperClient = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_ADDRESS).retryPolicy(retry).build();
            }
            zookeeperClient.start();

            try {
                if (!zookeeperClient.blockUntilConnected(30, TimeUnit.SECONDS)) {
                    throw new RuntimeException("Zookeeper 连接超时");
                }
            } catch (InterruptedException e) {
                log.info("响应线程中断");
                throw new RuntimeException(e);
            }
        }

        return zookeeperClient;
    }


    private static void registerWatcher(String rpcServiceName) {
        String path = BASE_RPC_PATH + "/" + rpcServiceName;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeperClient, path, true);
        PathChildrenCacheListener listener = (f, e) -> {
            List<String> list = f.getChildren().forPath(path);
            RPC_SERVICE_ADDRESS_MAP.put(rpcServiceName, list);
        };

    }
}
