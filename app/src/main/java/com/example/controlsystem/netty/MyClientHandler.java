package com.example.controlsystem.netty;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


//public class MyClientHandler extends SimpleChannelInboundHandler {
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
//
//    }
//}

//因为Netty对UDP进行了封装，所以接收到的是DatagramPacket对象
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    //接受服务端发送的内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                DatagramPacket msg) throws Exception {
        Log.d("MyClientHandler","receive");
        String body=msg.content().toString(CharsetUtil.UTF_8);
        Log.d("MyClientHandler","body:"+body);
    }

    //捕获异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
