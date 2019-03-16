package com.example.controlsystem.netty;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 客户端Channel通道初始化设置
 */
public class ClientChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    //SocketChannel标志着TCP格式
    //NioDatagramChannel标志着UDP格式的

    public static String m_host = "192.168.1.49"; //服务器IP地址
    public static int m_port = 8000; //服务器端口

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        //创建一个执行Handler、自定义编码、解码的容器
        ChannelPipeline pipeline=ch.pipeline();
        //pipeline.addLast(new StringDecoder());
        //pipeline.addLast(new CommandEncoder(new InetSocketAddress(m_host,m_port)));
        //执行具体的处理器
        pipeline.addLast("handler",new MyClientHandler());


    }
}


