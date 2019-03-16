package com.example.controlsystem.netty;

import java.net.InetSocketAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * 自定义编码类
 * 类说明：编码，将实际的CommandProtocal类编码为DatagramPacket
 */
public class CommandEncoder extends MessageToMessageEncoder<CommandProtocal> {
    private final InetSocketAddress remoteAddress;

    //CommandEncoder 创建了即将被发送到指定的 InetSocketAddress的 DatagramPacket 消息
    public CommandEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          CommandProtocal commandProtocal, List<Object> out) throws Exception {
        byte[] bytes= ConverterUtil.Object2Byte(commandProtocal);
        ByteBuf buf=channelHandlerContext.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        //将一个拥有数据和目的地址的新DatagramPacket添加到出站的消息列表中
        out.add(new DatagramPacket(buf,remoteAddress));

    }
}
