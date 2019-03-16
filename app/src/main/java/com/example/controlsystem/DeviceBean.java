package com.example.controlsystem;

/**
 * @author Gong
 */
public class DeviceBean {

    /**
     * IP地址
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 通道号
     */
    private int channel=1;

    /**
     * 码流类型：0-主码流，1-子码流，2-事件类型，3-码流3，4-虚拟码流，……
     */
    private int bitStreamType=1;

    /**
     * 视频码率:0-保留，1-16K(保留)，2-32K，3-48k，4-64K，5-80K，6-96K，7-128K，8-160k，9-192K，10-224K，
     * 11-256K，12-320K，13-384K，14-448K，15-512K，16-640K，17-768K，18-896K，19-1024K，20-1280K，21-1536K，22-1792K，23-2048K，
     * 24-3072K，25-4096K，26-8192K，27-16384K。最高位(31位)置成1表示是自定义码流，0～30位表示码流值，最小值16k,0xfffffffe，自动，和源一致
     */
    private int videoBitrate=19;

    /**
     * 视频帧率 0-全部; 1-1/16; 2-1/8; 3-1/4; 4-1/2; 5-1; 6-2; 7-4; 8-6; 9-8; 10-10; 11-12; 12-16; 13-20; V2.0版本中新加14-15; 15-18; 16-22;
     * 17-25；18-30；19-35；20-40；21-45；22-50；23-55；24-60；25-3;26-5;27-7;28-9;29-100; 30-120;31-24;32-48,0xfffffffe-自动，和源一致
     */
    private int videoFrameRate=12;

    /**
     * 0-视频流，1-复合流
     */
    private int videoStreamType=0;

    /**
     * 码率类型：0-变码率，1-定码率
     */
    private int bitrateType=1;

    /**
     * 0- TCP方式，1- UDP方式，2- 多播方式，3- RTP方式，4-RTP/RTSP，5-RSTP/HTTP
     */
    private int linkMode=0;

    /**
     * 0-不显示OSD，1-显示OSD
     */
    private int showOSD=0;

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setBitrateType(int bitrateType) {
        this.bitrateType = bitrateType;
    }

    public int getBitrateType() {
        return bitrateType;
    }

    public void setBitStreamType(int bitStreamType) {
        this.bitStreamType = bitStreamType;
    }

    public int getBitStreamType() {
        return bitStreamType;
    }

    public int getLinkMode() {
        return linkMode;
    }

    public void setLinkMode(int linkMode) {
        this.linkMode = linkMode;
    }

    public int getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public int getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(int videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public int getVideoStreamType() {
        return videoStreamType;
    }

    public void setVideoStreamType(int videoStreamType) {
        this.videoStreamType = videoStreamType;
    }

    public int getShowOSD() {
        return showOSD;
    }

    public void setShowOSD(int showOSD) {
        this.showOSD = showOSD;
    }

    @Override
    public String toString() {
        return "[IP=" + ip + "; PORT=" + port + "; USERNAME=" + userName
                + "; PASSWORD=" + passWord + "; CHANNEL=" + channel + ";]";
    }

}

