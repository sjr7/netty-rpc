package com.suny.rpc.nettyrpc.core.serialize;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 序列化工厂
 *
 * @author sunjianrong
 * @date 2021-09-09 15:59
 */
@Component
public class SerializerFactory {
    private final List<Serializer> serializerList;

    public SerializerFactory(List<Serializer> serializerList) {
        this.serializerList = serializerList;
    }


    public Serializer getSerializer(byte type) {
        for (Serializer serializer : serializerList) {
            final SerializerType serializerType = serializer.getSerializerType();
            if (serializer.getSerializerType().getType() == type) {
                return serializer;
            }
        }
        return null;
    }
}
