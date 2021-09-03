package com.suny.rpc.nettyrpc.core.ext.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * Zookeeper 辅助函数
 *
 * @author sunjianrong
 * @date 2021/8/25 下午11:10
 */
@Slf4j
@Component
public class ZookeeperHelper {

    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    public static final String BASE_RPC_PATH = "/rpc";
    private static volatile CuratorFramework zookeeperClient;

    private static final Map<String, List<String>> RPC_SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();
    public static final Set<String> PATH_SET = new ConcurrentSkipListSet<>();

    public List<String> getChildrenNodesValue(String rpcServiceName) {
        final List<String> childrenNodes = getChildrenNodes(rpcServiceName);
        if (childrenNodes == null) {
            return Collections.emptyList();
        }

        List<String> res = new ArrayList<>();
        for (String childrenNode : childrenNodes) {
            try {
                final byte[] bytes = getZookeeperClient().getData().forPath(childrenNode);
                res.add(new String(bytes));
            } catch (Exception e) {
                // e.printStackTrace();
                throw new RuntimeException("读取节点值" + rpcServiceName + "失败");
            }
        }
        return res;
    }

    public List<String> getChildrenNodes(String rpcServiceName) {
        checkInit();
        if (RPC_SERVICE_ADDRESS_MAP.containsKey(rpcServiceName)) {
            return RPC_SERVICE_ADDRESS_MAP.get(rpcServiceName);
        }

        List<String> res;
        String path = BASE_RPC_PATH + "/" + rpcServiceName;

        try {
            res = zookeeperClient.getChildren().forPath(path);
            RPC_SERVICE_ADDRESS_MAP.put(rpcServiceName, res);
            registerWatcher(rpcServiceName);
        } catch (Exception e) {
            throw new RuntimeException("获取 Zookeeper " + path + " 子节点失败");
        }

        return res;
    }

    public void createNode(String path) {
        checkInit();
        try {
            if (PATH_SET.contains(path) || zookeeperClient.checkExists().forPath(path) != null) {
                log.debug("节点 {} 已经存在", path);
            } else {
                zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
        } catch (Exception e) {
            log.info("节点 {} 创建失败", path, e);
            throw new RuntimeException("创建节点" + path + "失败", e);
        }
    }


    public void removeNode(InetSocketAddress inetSocketAddress) {
        checkInit();
        PATH_SET.forEach(p -> {
            if (p.endsWith(inetSocketAddress.toString())) {
                try {
                    zookeeperClient.delete().forPath(p);
                } catch (Exception e) {
                    log.warn("清理注册服务路径 {} 失败", p);
                }
            }
        });

        log.info("服务 {} 清理完成", inetSocketAddress.toString());
    }

    private void checkInit() {
        if (zookeeperClient != null && zookeeperClient.getState() == CuratorFrameworkState.STARTED) {
            return;
        }

        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
        synchronized (CuratorFramework.class) {
            if (zookeeperClient == null) {
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
    }

    /**
     * @return zookeeper 操作客户端
     */
    public CuratorFramework getZookeeperClient() {
        checkInit();
        return zookeeperClient;
    }


    private void registerWatcher(String rpcServiceName) throws Exception {
        checkInit();
        String path = BASE_RPC_PATH + "/" + rpcServiceName;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeperClient, path, true);
        PathChildrenCacheListener listener = (f, e) -> {
            List<String> list = f.getChildren().forPath(path);
            RPC_SERVICE_ADDRESS_MAP.put(rpcServiceName, list);
        };

        pathChildrenCache.getListenable().addListener(listener);
        pathChildrenCache.start();

    }
}
