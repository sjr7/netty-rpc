package com.suny.rpc.nettyrpc.core.client;

import com.suny.rpc.nettyrpc.core.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求处理管理
 *
 * @author sunjianrong
 * @date 2021-09-09 14:05
 */
@Slf4j
public class RequestFutureManager {

    private static final Map<String, CompletableFuture<RpcResponse>> RESPONSE_FUTURE_MAP = new ConcurrentHashMap<>();


    public static void addFuture(String sequence, CompletableFuture<RpcResponse> future) {
        RESPONSE_FUTURE_MAP.put(sequence, future);
    }


    public static void removeAndComplete(RpcResponse rpcResponse) {
        final String sequence = rpcResponse.getSequence();
        final CompletableFuture<RpcResponse> future = RESPONSE_FUTURE_MAP.remove(sequence);
        if (future == null) {
            log.info("未找到请求 {} 的待处理任务", sequence);
        } else {
            future.complete(rpcResponse);
        }
    }


}
