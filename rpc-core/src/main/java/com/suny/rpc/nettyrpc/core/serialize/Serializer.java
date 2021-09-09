package com.suny.rpc.nettyrpc.core.serialize;

/**
 * 序列化
 *
 * @author sunjianrong
 * @date 2021-09-09 14:47
 */
public interface Serializer {

    /**
     * 序列化对象
     *
     * @param obj 待序列化对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);


    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz 目标类型
     * @param <T>   泛型
     * @return 反序列化对象
     */
    <T> T deSerialize(byte[] bytes, Class<T> clazz);

    /**
     * 获取序列化、反序列化类型
     *
     * @return 序列化类型
     */
    SerializerType getSerializerType();
}
