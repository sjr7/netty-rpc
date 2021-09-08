package com.suny.rpc.nettyrpc.core.ext.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ZookeeperHelperTest {

    @Spy
    private ZookeeperHelper zookeeperHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void distory() throws Exception {
        zookeeperHelper.destroy();
    }

    @Test
    void testGetServiceInstanceNode() {
        final List<String> serviceInstanceNode = zookeeperHelper.getServiceInstanceNode("com.suny.rpc.nettyrpc.api.UserService");
        assertNotNull(serviceInstanceNode);
        Assertions.assertTrue(serviceInstanceNode.size() > 0);
    }

    @Test
    public void testCreateServiceInstanceNode(){
        zookeeperHelper.createServiceInstanceNode("com.suny.rpc.nettyrpc.api.UserService");
    }


    @Test
    void testGetChildrenNodesValue() {
        final List<String> childrenNodesValue = zookeeperHelper.getChildrenNodesValue("com.suny.rpc.nettyrpc.api.UserService");
        for (String s : childrenNodesValue) {
            System.out.println(s);
        }
    }

    @Test
    public void testGetAll() throws Exception {
        final List<String> childrenNodesValue = zookeeperHelper.getChildrenNodesValue("com.suny.rpc.nettyrpc.api.UserService");
        System.out.println(childrenNodesValue);
    }

    @Test
    void getChildrenNodes() throws Exception {
        CuratorFramework zookeeperClient = zookeeperHelper.getZookeeperClient();
        zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_WITH_TTL).forPath("test");

        List<String> list = zookeeperClient.getChildren().forPath("test");
        Assertions.assertNotNull(list);
    }

    @Test
    void createNode() throws Exception {

        zookeeperHelper.createNode("/rpc/test/");

        List<String> list = zookeeperHelper.getChildrenNodes("test");
        Assertions.assertNotNull(list);
    }

    @Test
    void removeNode() {
        zookeeperHelper.createNode("/rpc/test/127.0.0.1:5000");

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5000);
        zookeeperHelper.removeNode(inetSocketAddress);
    }


    @Test
    void getZookeeperClient() {
        CuratorFramework zookeeperClient = zookeeperHelper.getZookeeperClient();
        Assertions.assertNotNull(zookeeperClient);
        assertEquals(CuratorFrameworkState.STARTED, zookeeperClient.getState());
    }
}