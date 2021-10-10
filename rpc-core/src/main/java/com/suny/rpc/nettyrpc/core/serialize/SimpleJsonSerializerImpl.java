package com.suny.rpc.nettyrpc.core.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Service;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:50
 */
@Service
public class SimpleJsonSerializerImpl implements Serializer {

    static{
        ParserConfig.getGlobalInstance().addAccept("com.suny.rpc");
    }

    @Override
    public byte[] serialize(Object obj) {
//        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
        return JSON.toJSONBytes(obj, SerializerFeature.WriteClassName);
    }

    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
//        return JSON.parseObject(new String(bytes), clazz);
        return JSON.parseObject(bytes, clazz);
    }


    @Override
    public SerializerType getSerializerType() {
        return SerializerType.JSON;
    }
}
