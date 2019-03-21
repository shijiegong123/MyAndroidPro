package com.example.controlsystem.netty;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.apache.log4j.Logger;

public class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    //打印日志
    public static final Logger log=Logger.getLogger(MyServerHandler.class);

    //客户端与服务器连接成功的时候触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d("MyServerHandler", "channelActivity");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                DatagramPacket packet) throws Exception {
        //读取收到的数据
        ByteBuf buf=(ByteBuf)packet.copy().content();
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req, CharsetUtil.UTF_8);
        Log.d("MyNettyHandler",body);

        //回复一条信息给客户端
        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer("Server :"+System.currentTimeMillis(),
                        CharsetUtil.UTF_8),packet.sender())).sync();
    }


    //通道读取完成时触发
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.flush();
    }

    //捕获异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.e("MyNettyServer","exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
