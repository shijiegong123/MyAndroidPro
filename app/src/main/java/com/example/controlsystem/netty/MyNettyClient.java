package com.example.controlsystem.netty;

import android.util.Log;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import org.apache.log4j.Logger;



public class MyNettyClient extends Thread {

    //打印日志
    public static final Logger log=Logger.getLogger(MyNettyClient.class);

    public static String m_host = "192.168.1.100"; //服务器IP地址
    public static int m_port = 8080; //服务器端口

    public boolean STOP=false;

    public CommandProtocal m_commandProtocal=new CommandProtocal();

    @Override
    public void run() {
        //super.run();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    //设置UDP通道
                    //.channel(NioSocketChannel.class)  //NioSocketChannel对应于TCP-Client
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)   //允许广播
                    //设置UDP的管道工厂
                    .handler(new ClientChannelInitializer());


            //Channel channel = b.connect(m_host, m_port).sync().channel();//TCP客户端连接服务器的方式
            //UDP客户端绑定端口
            Channel channel=b.bind(0).sync().channel();

            while (!STOP)
            {
                Log.d("client","send");
                //向服务端发送数据

                //channel.writeAndFlush(m_commandProtocal);
                channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(m_commandProtocal.GetByteBuf()),
                        new InetSocketAddress(m_host,m_port)));

//                channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("6666",
//                        CharsetUtil.UTF_8), new InetSocketAddress(m_host,m_port)));
//                channel.writeAndFlush(new DatagramPacket(m_commandProtocal),
//                         new InetSocketAddress(m_host,m_port));
                Thread.sleep(10);
            }

        }catch (Exception e){
            Log.d("client","send5");
            e.printStackTrace();

        }finally {
            //优雅退出，释放线程池资源
            group.shutdownGracefully();
        }


    }

    public void tostop() {
        STOP=true;
    }

    public void updateCommandProtocal(CommandProtocal commandProtocal){
        m_commandProtocal=commandProtocal;
    }

}
