package com.suny.rpc.nettyrpc.core.process;

import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @author sunjianrong
 * @date 2021-09-10 15:57
 */
class RpcRequestProcessorTest {
    @Mock
    Map<String, Object> BEAN_MAP;

    @Mock
    Logger log;

    @InjectMocks
    RpcRequestProcessor rpcRequestProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddRpcBean() {
        RpcRequestProcessor.addRpcBean("service.string", new String("123"));
        final Object bean = RpcRequestProcessor.getBean("service.string");
        Assertions.assertNotNull(bean);
        Assertions.assertEquals("123", bean);
    }

    @Test
    void testGetBean() {
        Object result = RpcRequestProcessor.getBean("serviceName");
        Assertions.assertNull(result);
    }

    @Test
    void testRemove() {
        RpcRequestProcessor.addRpcBean("service.string", new String("123"));
        RpcRequestProcessor.remove("service.string");

    }

    @Test
    void testProcess() {
        RpcRequestProcessor.addRpcBean("service.string", new String("123"));


        final RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName("service.string");
        rpcRequest.setMethodName("length");
        rpcRequest.setParameterType(null);
        rpcRequest.setParameters(null);

        Object result = RpcRequestProcessor.process(rpcRequest);
        Assertions.assertEquals(3, result);
    }
}
