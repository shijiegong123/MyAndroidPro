package com.example.controlsystem.netty;

import java.io.Serializable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CommandProtocal implements Serializable {
    //载车标识号
    private int unique_key=0;
    //GPS周，时间零点为1980年1月6日，每1024周为一个循环周期
    private int gps_week=0;
    //GPS毫秒
    private long gps_ms=0;
    //指控方舱毫秒
    private long cmd_ms=0;
    //转向角，单位为0.01度，-3000~3000
    private short cmd_steer=0;
    //期望速度cm/s
    private short cmd_velocity=0;
    //期望曲率
    private byte cmd_curvature=0;
    //强制开始
    private byte cmd_forcestart=0;
    //任务模式
    private byte cmd_taskmode=0;
    //手刹
    private byte cmd_handbrake=0;
    //油门
    private byte cmd_throttle=0;
    //刹车
    private byte cmd_brake=0;
    //档位
    private byte cmd_shift=0;
    //点火开关
    private byte cmd_ignition=0;
    //灯光
    private byte cmd_light=0;
    //复位，进行自主
    private byte cmd_reset=0;
    //预留
    private byte reserve1=0;
    //预留
    private byte reserve2=0;

    public int getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(int unique_key) {
        this.unique_key = unique_key;
    }

    public int getGps_week() {
        return gps_week;
    }

    public void setGps_week(int gps_week) {
        this.gps_week = gps_week;
    }

    public long getGps_ms() {
        return gps_ms;
    }

    public void setGps_ms(long gps_ms) {
        this.gps_ms = gps_ms;
    }

    public long getCmd_ms() {
        return cmd_ms;
    }

    public void setCmd_ms(long cmd_ms) {
        this.cmd_ms = cmd_ms;
    }

    public short getCmd_steer() {
        return cmd_steer;
    }

    public void setCmd_steer(short cmd_steer) {
        this.cmd_steer = cmd_steer;
    }

    public short getCmd_velocity() {
        return cmd_velocity;
    }

    public void setCmd_velocity(short cmd_velocity) {
        this.cmd_velocity = cmd_velocity;
    }

    public byte getCmd_curvature() {
        return cmd_curvature;
    }

    public void setCmd_curvature(byte cmd_curvature) {
        this.cmd_curvature = cmd_curvature;
    }

    public byte getCmd_forcestart() {
        return cmd_forcestart;
    }

    public void setCmd_forcestart(byte cmd_forcestart) {
        this.cmd_forcestart = cmd_forcestart;
    }

    public byte getCmd_taskmode() {
        return cmd_taskmode;
    }

    public void setCmd_taskmode(byte cmd_taskmode) {
        this.cmd_taskmode = cmd_taskmode;
    }

    public byte getCmd_handbrake() {
        return cmd_handbrake;
    }

    public void setCmd_handbrake(byte cmd_handbrake) {
        this.cmd_handbrake = cmd_handbrake;
    }

    public byte getCmd_throttle() {
        return cmd_throttle;
    }

    public void setCmd_throttle(byte cmd_throttle) {
        this.cmd_throttle = cmd_throttle;
    }

    public byte getCmd_brake() {
        return cmd_brake;
    }

    public void setCmd_brake(byte cmd_brake) {
        this.cmd_brake = cmd_brake;
    }

    public byte getCmd_shift() {
        return cmd_shift;
    }

    public void setCmd_shift(byte cmd_shift) {
        this.cmd_shift = cmd_shift;
    }

    public byte getCmd_ignition() {
        return cmd_ignition;
    }

    public void setCmd_ignition(byte cmd_ignition) {
        this.cmd_ignition = cmd_ignition;
    }

    public byte getCmd_light() {
        return cmd_light;
    }

    public void setCmd_light(byte cmd_light) {
        this.cmd_light = cmd_light;
    }

    public byte getCmd_reset() {
        return cmd_reset;
    }

    public void setCmd_reset(byte cmd_reset) {
        this.cmd_reset = cmd_reset;
    }

    public byte getReserve1() {
        return reserve1;
    }

    public void setReserve1(byte reserve1) {
        this.reserve1 = reserve1;
    }

    public byte getReserve2() {
        return reserve2;
    }

    public void setReserve2(byte reserve2) {
        this.reserve2 = reserve2;
    }

    public ByteBuf GetByteBuf(){
        ByteBuf buf= Unpooled.buffer(40);
        buf.writeInt(this.unique_key);
        buf.writeInt(this.gps_week);
        buf.writeLong(this.gps_ms);
        buf.writeLong(this.cmd_ms);
        buf.writeShort(cmd_steer);
        buf.writeShort(cmd_velocity);
        buf.writeByte(cmd_curvature);
        buf.writeByte(cmd_forcestart);
        buf.writeByte(cmd_taskmode);
        buf.writeByte(cmd_handbrake);
        buf.writeByte(cmd_throttle);
        buf.writeByte(cmd_brake);
        buf.writeByte(cmd_shift);
        buf.writeByte(cmd_ignition);
        buf.writeByte(cmd_light);
        buf.writeByte(cmd_reset);
        buf.writeByte(reserve1);
        buf.writeByte(reserve2);
        return buf;

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
