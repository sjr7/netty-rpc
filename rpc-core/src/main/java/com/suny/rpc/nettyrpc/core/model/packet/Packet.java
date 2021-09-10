package com.suny.rpc.nettyrpc.core.model.packet;

import com.suny.rpc.nettyrpc.core.enums.PacketType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * 消息体. 参考网上常用结构定义.
 *
 * @author sunjianrong
 * @date 2021-09-09 13:51
 */
@Data
public abstract class Packet implements Serializable {

    private static final long serialVersionUID = 5038767224481675128L;

    /**
     * 魔数. 标识协议
     */
    @Setter(AccessLevel.NONE)
    private byte magicNumber = 66;

    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    public abstract PacketType getPacketType();
}
