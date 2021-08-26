package com.suny.rpc.nettyrpc.core.ext.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZookeeperHelperTest {

    @Test
    void getChildrenNodes() throws Exception {
        CuratorFramework zookeeperClient = ZookeeperHelper.getZookeeperClient();
        zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_WITH_TTL).forPath("test");

        List<String> list = zookeeperClient.getChildren().forPath("test");
        Assertions.assertNotNull(list);
    }

    @Test
    void createNode() throws Exception {

        ZookeeperHelper.createNode("/rpc/test/");

        List<String> list = ZookeeperHelper.getChildrenNodes("test");
        Assertions.assertNotNull(list);
    }

    @Test
    void removeNode() {
        ZookeeperHelper.createNode("/rpc/test/127.0.0.1:5000");

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5000);
        ZookeeperHelper.removeNode(inetSocketAddress);
    }


    @Test
    void getZookeeperClient() {
        CuratorFramework zookeeperClient = ZookeeperHelper.getZookeeperClient();
        Assertions.assertNotNull(zookeeperClient);
        assertEquals(CuratorFrameworkState.STARTED, zookeeperClient.getState());
    }
}