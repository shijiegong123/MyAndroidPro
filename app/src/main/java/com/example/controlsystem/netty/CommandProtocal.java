package com.example.controlsystem.netty;

import java.io.Serializable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CommandProtocal implements Serializable {
    //头标志
    private static final byte HEAD_DATA=0x76;
    //消息体数据长度
    private int length=0;
    //转向角度，单位为0.1度
    private int steer=0;
    //油门度，0-100百分比
    private int throttle=0;
    //刹车度
    private int brake=0;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSteer() {
        return steer;
    }

    public void setSteer(int steer) {
        this.steer = steer;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public int getBrake() {
        return brake;
    }

    public void setBrake(int brake) {
        this.brake = brake;
    }

    public ByteBuf GetByteBuf(){
        ByteBuf buf= Unpooled.buffer(17);
        buf.writeByte(this.HEAD_DATA);
        buf.writeInt(this.length);
        buf.writeInt(this.steer);
        buf.writeInt(this.throttle);
        buf.writeInt(this.brake);
        return buf;

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
