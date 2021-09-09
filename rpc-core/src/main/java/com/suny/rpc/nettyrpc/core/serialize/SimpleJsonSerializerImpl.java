package com.suny.rpc.nettyrpc.core.serialize;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:50
 */
@Service
public class SimpleJsonSerializerImpl implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }


    @Override
    public SerializerType getSerializerType() {
        return SerializerType.JSON;
    }
}
