package com.suny.rpc.nettyrpc.core.codec;

import com.suny.rpc.nettyrpc.core.model.packet.Packet;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import com.suny.rpc.nettyrpc.core.serialize.SerializerType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunjianrong
 * @date 2021-09-09 14:17
 */
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Packet> {

    private final SerializerFactory serializerFactory;

    public MessageEncoder(SerializerFactory serializerFactory) {
        this.serializerFactory = serializerFactory;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        final byte type = SerializerType.JSON.getType();

        // 魔数
        out.writeByte(msg.getMagicNumber());

        // 序列化算法
        out.writeByte(type);

        // 指令类型
        out.writeByte(msg.getPacketType().getType());

        // 长度
        final byte[] bytes = serializerFactory.getSerializer(type).serialize(msg);
        out.writeInt(bytes.length);

        // 数据
        out.writeBytes(bytes);
    }
}
