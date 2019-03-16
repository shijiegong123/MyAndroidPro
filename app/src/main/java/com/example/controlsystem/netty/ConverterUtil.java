package com.example.controlsystem.netty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 相互转化工具
 */
public class ConverterUtil {

    //byte[]->Object
    public static Object Byte2Object(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    //Object->byte[]
    public static byte[] Object2Byte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (bytes);
    }

    /**
     * byte数组转int类型的对象
     * @param bytes
     * @return
     */
    public int Byte2Int(Byte[] bytes) {
        return (bytes[0]&0xff)<<24
                | (bytes[1]&0xff)<<16
                | (bytes[2]&0xff)<<8
                | (bytes[3]&0xff);
    }

    /**
     * int转byte数组
     * @param num
     * @return
     */
    public byte[] IntToByte(int num){
        byte[] bytes=new byte[4];
        bytes[0]=(byte) ((num>>24)&0xff);
        bytes[1]=(byte) ((num>>16)&0xff);
        bytes[2]=(byte) ((num>>8)&0xff);
        bytes[3]=(byte) (num&0xff);
        return bytes;
    }

    /**
     * 高尾端-大端
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     * 和bytesToInt（）配套使用
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes_big( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * 高尾端-大端
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     * @param src byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt_big(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }

    /**
     * 低尾端-小端
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     * 和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes_little(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 低尾端-小端
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt_little(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

}
