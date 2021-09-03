package com.suny.rpc.nettyrpc.core.discovery;

import com.suny.rpc.nettyrpc.core.discovery.impl.ZookeeperRpcServiceDiscoveryImpl;
import com.suny.rpc.nettyrpc.core.ext.zookeeper.ZookeeperHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * @author sunjianrong
 * @date 2021-09-03 15:29
 */
class ZookeeperRpcServiceDiscoveryImplTest {
    @Mock
    Logger log;

    @InjectMocks
    ZookeeperRpcServiceDiscoveryImpl zookeeperRpcServiceDiscoveryImpl;

    @Mock
    private ZookeeperHelper zookeeperHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        List<String> service1List = new ArrayList<>();
        service1List.add("127.0.0.1");
        service1List.add("127.0.0.2");
        service1List.add("127.0.0.3");
        when(zookeeperHelper.getChildrenNodes("com.suny.rpc.nettyrpc.api.UserService")).thenReturn(service1List);
    }

    @Test
    void testGetServiceInstanceList() {
        final List<String> res1 = zookeeperRpcServiceDiscoveryImpl.getServiceInstanceList("com.suny.rpc.nettyrpc.api.UserService");
        Assertions.assertNotNull(res1);
        Assertions.assertTrue(res1.contains("127.0.0.1"));

        final String res2 = zookeeperRpcServiceDiscoveryImpl.getServiceInstance("com.suny.rpc.nettyrpc.api.UserService1");
        Assertions.assertNotNull(res2);

    }

    @Test
    void testGetAllServiceInstance() {
        Map<String, List<String>> result = zookeeperRpcServiceDiscoveryImpl.getAllServiceInstance();
        Assertions.assertNull(result);

    }
}