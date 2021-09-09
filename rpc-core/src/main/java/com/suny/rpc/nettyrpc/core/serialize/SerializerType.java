package com.suny.rpc.nettyrpc.core.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:53
 */
@AllArgsConstructor
@Getter
public enum SerializerType {
    /**
     * json类型
     */
    JSON((byte) 1);


    private byte type;


}
