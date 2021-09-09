package com.suny.rpc.nettyrpc.core.network;

import com.suny.rpc.nettyrpc.core.model.RpcRequest;
import com.suny.rpc.nettyrpc.core.model.RpcResponse;

/**
 * @author sunjianrong
 * @date 2021-09-09 11:04
 */
public interface RpcRequestSender {

    /**
     * 发送 rpc 请求
     *
     * @param rpcRequest 请求参数
     * @return 请求结果
     */
    RpcResponse sendRpcRequest(RpcRequest rpcRequest);
}
