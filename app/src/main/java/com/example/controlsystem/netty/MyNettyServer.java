package com.example.controlsystem.netty;

import android.util.Log;

import org.apache.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class MyNettyServer extends Thread {

    //打印日志
    public static final Logger log=Logger.getLogger(MyNettyServer.class);

    public static String m_host = "192.168.1.49"; //服务器IP地址
    public static int m_port = 8000; //服务器端口

    public boolean STOP=false;

    @Override
    public void run() {

        /**
         * TCP-Server创建服务需要两个事件循环组(两个线程)
         * // 1 创建两个事件循环组
         * // 一个是用于处理服务器端接收客户端连接的
         * // 一个是进行网络通信的（网络读写的）
         * EventLoopGroup pGroup = new NioEventLoopGroup();
         * EventLoopGroup cGroup = new NioEventLoopGroup();
         */
        //UDP-Server 只需要一个事件循环组(线程)(UDP不需要处理客户端连接)
        EventLoopGroup workergroup=new NioEventLoopGroup();

        //设置线程数
        final EventExecutorGroup group=new DefaultEventExecutorGroup(16);
        try{
            //UDP-Server 创建辅助工具类Bootstrap，用于服务器通道的一系列配置
            Bootstrap b=new Bootstrap();    //udp不能使用ServerBootstrap
            b.group(workergroup).channel(NioDatagramChannel.class)    //设置UDP通道
                                .option(ChannelOption.SO_BROADCAST,true)//支持广播
                                .option(ChannelOption.SO_RCVBUF,2048*1024)//设置UDP读缓冲区为2M
                                .option(ChannelOption.SO_SNDBUF,1024*1024)//设置UDP写缓冲区为1M
                                .handler(new ChannelInitializer<NioDatagramChannel>() {
                                    //NioDatagramChannel标志着UDP格式
                                    @Override
                                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                                        //TODO Auto-generated method stub
                                        //创建一个执行Handler的容器
                                        ChannelPipeline pipeline=ch.pipeline();
                                        pipeline.addLast(new StringEncoder());
                                        pipeline.addLast(new StringDecoder());
                                        //执行具体的处理器
                                        pipeline.addLast(group, "handler", new MyServerHandler());

                                    }
                                });

            //绑定到指定端口，开始接收进来的连接
            Channel ch = b.bind(m_port ).sync().channel();

            //等待服务器socket关闭
            //这不会发生，可以优雅地关闭服务器
            ch.closeFuture().sync();

        }catch (Exception e){
            Log.e("MyNettyServer","Error");
            e.printStackTrace();

        }finally {
            // 优雅退出 释放线程池资源
            workergroup.shutdownGracefully();
            group.shutdownGracefully();

        }

    }
}
