package com.suny.rpc.nettyrpc.core.discovery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午5:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAddress implements Serializable {

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务地址集合
     */
    private List<String> serviceAddressList;


}
