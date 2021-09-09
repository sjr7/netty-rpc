package com.suny.rpc.nettyrpc.core.codec;

import com.suny.rpc.nettyrpc.core.model.packet.Packet;
import com.suny.rpc.nettyrpc.core.serialize.Serializer;
import com.suny.rpc.nettyrpc.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author sunjianrong
 * @date 2021-09-09 15:51
 */
@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {

    private final SerializerFactory serializerFactory;

    public MessageDecoder(SerializerFactory serializerFactory) {
        this.serializerFactory = serializerFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 魔数
        in.skipBytes(1);

        // 序列化算法
        final byte serializeType = in.readByte();

        // 消息类型
        final byte messageType = in.readByte();
        final Class<? extends Packet> packetClass = PacketClassManager.getPacketClass(messageType);
        if (packetClass == null) {
            throw new UnsupportedOperationException("不支持" + messageType + "消息类型");
        }


        // 长度
        final int messageLength = in.readInt();

        // 反序列化数据
        final Serializer serializer = serializerFactory.getSerializer(serializeType);
        if (serializer == null) {
            throw new UnsupportedOperationException("不支持" + serializeType + "序列化算法");
        }

        final byte[] bytes = new byte[messageLength];
        in.readBytes(bytes);

        final Packet packet = serializer.deSerialize(bytes, packetClass);
        out.add(packet);
    }
}
