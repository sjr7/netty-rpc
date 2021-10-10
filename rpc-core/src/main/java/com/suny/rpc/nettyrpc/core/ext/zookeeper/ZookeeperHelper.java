package com.suny.rpc.nettyrpc.core.ext.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
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
public class ZookeeperHelper implements DisposableBean {

    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    public static final String BASE_RPC_PATH = "/rpc";
    private static volatile CuratorFramework zookeeperClient;

    private static final Map<String, List<String>> RPC_SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();
    public static final Set<String> PATH_SET = new ConcurrentSkipListSet<>();


    /**
     * 创建服务节点
     *
     * @param rpcServiceName 服务类名
     */
    public void createServiceInstanceNode(String rpcServiceName) {
        createServiceInstanceNode(rpcServiceName, null);
    }

    /**
     * 创建服务节点
     *
     * @param rpcServiceName 服务类名
     */
    public void createServiceInstanceNode(String rpcServiceName, String data) {
        checkInit();
        final String serviceNode = BASE_RPC_PATH + "/" + rpcServiceName + "/node";
        try {
            if (data == null) {
                zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(serviceNode);
            } else {
                zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(serviceNode, data.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.info("节点 {} 创建失败", serviceNode, e);
            throw new RuntimeException("创建节点" + serviceNode + "失败", e);
        }
    }


    public List<String> getServiceInstanceNode(String rpcServiceName) {
        checkInit();
        final String serviceNodeName = BASE_RPC_PATH + "/" + rpcServiceName;
        try {
            List<String> serviceNodeList = zookeeperClient.getChildren().forPath(serviceNodeName);
            List<String> res = new ArrayList<>();
            for (String childrenNode : serviceNodeList) {
                try {
                    final byte[] bytes = getZookeeperClient().getData().forPath(serviceNodeName + "/" + childrenNode);
                    res.add(new String(bytes));
                } catch (Exception e) {
                    // throw new RuntimeException("读取节点值" + rpcServiceName + "失败");
                    log.info("读取节点值 {} 失败", rpcServiceName, e);
                }
            }
            return res;
        } catch (Exception e) {
            log.info("获取 {} 子节点列表失败", serviceNodeName, e);
            return Collections.emptyList();
        }
    }

    public Map<String, List<String>> getAllServiceInstanceNode() {
        Map<String, List<String>> res = new HashMap<>();

        try {
            List<String> serviceNodeList = zookeeperClient.getChildren().forPath(BASE_RPC_PATH);
            for (String s : serviceNodeList) {
                res.put(s, getServiceInstanceNode(s));
            }

            return res;
        } catch (Exception e) {
            log.info("获取 {} 子节点列表失败", BASE_RPC_PATH, e);
            return res;
        }
    }


    @Deprecated
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

    @Deprecated
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


    @Deprecated
    public void createNode(String path) {
        checkInit();

        try {
            if (PATH_SET.contains(path) || zookeeperClient.checkExists().forPath(path) != null) {
                log.debug("节点 {} 已经存在", path);
            } else {
                final String createPath = zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            }
        } catch (Exception e) {
            log.info("节点 {} 创建失败", path, e);
            throw new RuntimeException("创建节点" + path + "失败", e);
        }
    }


    @Deprecated
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
            final GetDataBuilder data = f.getData();
            if (data != null) {
                switch (e.getType()) {
                    case CHILD_ADDED:
                        log.debug("添加节点");
                        break;
                    case CHILD_REMOVED:
                        log.debug("删除节点");
                        break;
                    case CHILD_UPDATED:
                        log.debug("节点更新");
                        break;
                    default:
                        log.debug("未处理事件类型 {}", e.getType());
                }
            }
            List<String> list = f.getChildren().forPath(path);
            RPC_SERVICE_ADDRESS_MAP.put(rpcServiceName, list);
        };

        pathChildrenCache.getListenable().addListener(listener);
        pathChildrenCache.start();

    }

    @Override
    public void destroy() throws Exception {
        log.info("Zookeeper开始关闭!");
        zookeeperClient.close();
    }
}
