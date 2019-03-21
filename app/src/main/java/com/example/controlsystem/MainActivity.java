package com.example.controlsystem;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.Window;
import android.view.WindowManager;

import com.example.controlsystem.netty.CommandProtocal;
import com.example.controlsystem.netty.MyNettyClient;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends BaseActivity{

    public static final String TAG="MainActivity";

    //显示视频控件
    private SurfaceView surfaceView;
    //摇杆与技能控件
    //private RockerView
    //HC设备管理者
    private HC_DVRManager hc_dvrManager=null;
    private Bitmap mbitmap;
    //UDP netty客户端
    private MyNettyClient myNettyClient=null;
    //控制协议
    private CommandProtocal m_commandProtocal;

    //OpenCV库静态加载并初始化
    private void staticLoadCVLibraries(){
        boolean load= OpenCVLoader.initDebug();
        if(load){
            Log.i("CV", "OpenCV Libraries success");
        }else {
            Log.i("CV", "OpenCV Libraries failed");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息状态栏
        setContentView(R.layout.activity_main);

        initialView();

        staticLoadCVLibraries();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        myNettyClient=new MyNettyClient();
        myNettyClient.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                hc_dvrManager.loginDevice();
                hc_dvrManager.realPlay();
            }
        }).start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        myNettyClient.tostop();

        new Thread(new Runnable() {
            @Override
            public void run() {
                hc_dvrManager.stopPlay();

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(hc_dvrManager!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    hc_dvrManager.logoutDevice();
                    hc_dvrManager.freeSDK();
                }
            }).start();

        }

    }

    private void initialView(){
        //initial surfaceView
        surfaceView=(SurfaceView) findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(new Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("surfaceview","surfaceCreated");

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("surfaceview","surfaceChanged");

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("surfaceview","surfaceDestroyed");
                surfaceView.destroyDrawingCache();
            }
        });

        hc_dvrManager=HC_DVRManager.getInstance();
        hc_dvrManager.setDeviceBean(getDeviceBean());
        hc_dvrManager.setSurfaceHolder(surfaceView.getHolder());
        hc_dvrManager.initSDK();
        hc_dvrManager.setOnHCCallBackListener(new HC_DVRManager.OnHCCallBackListener() {
            @Override
            public void OnGetBitmap(Bitmap bitmap) {
//                mbitmap=bitmap;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(mbitmap);
//                    }
//                });
            }

            @Override
            public void OnError(String err) {

            }
        });

        m_commandProtocal=new CommandProtocal();

    }

    private DeviceBean getDeviceBean() {
        DeviceBean bean = new DeviceBean();
        bean.setIP("192.168.1.106");
        bean.setPort("8000");
        bean.setUserName("admin");
        bean.setPassWord("alv123456");
        bean.setChannel(1);
        return bean;
    }

    /**
     * 响应键盘按下事件（这里主要用于蓝牙无线手柄）
     * @param
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.i("KeyDown","keyCode:"+keyCode+"  KeyEvent:"+event);
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(m_commandProtocal.getCmd_curvature()<=-125){
                    break;
                }
                m_commandProtocal.setCmd_curvature((byte)(m_commandProtocal.getCmd_curvature()-1));
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(m_commandProtocal.getCmd_curvature()>=125){
                    break;
                }
                m_commandProtocal.setCmd_curvature((byte)(m_commandProtocal.getCmd_curvature()+1));
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(m_commandProtocal.getCmd_velocity()>=60){
                    break;
                }
                m_commandProtocal.setCmd_velocity((short)(m_commandProtocal.getCmd_velocity()+5));
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            case KeyEvent.KEYCODE_BUTTON_X:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            case KeyEvent.KEYCODE_BUTTON_Y:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            case KeyEvent.KEYCODE_BUTTON_A:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            case KeyEvent.KEYCODE_BUTTON_B:
                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
                break;
            /**
             * 屏蔽掉有些按键的另一效果
             */
            //KEYCODE_BUTTON_X的另一效果
            case KeyEvent.KEYCODE_DEL:
                return true;
            //KEYCODE_BUTTON_Y的另一效果
            case KeyEvent.KEYCODE_SPACE:
                return true;
            //KEYCODE_BUTTON_A的另一效果
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return true;
            //KEYCODE_BUTTON_B的另一效果
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        myNettyClient.updateCommandProtocal(m_commandProtocal);
        return super.onKeyDown(keyCode, event);
    }

//    /**
//     * 响应键盘弹起事件（这里主要用于蓝牙无线手柄）
//     * @param
//     * @return
//     */
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        //Log.i("KeyUp","keyCode:"+keyCode+"  KeyEvent:"+event);
//        switch (keyCode){
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_THUMBL:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_THUMBR:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_X:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_Y:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_A:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            case KeyEvent.KEYCODE_BUTTON_B:
//                Log.i(TAG, "keyCode:"+keyCode+"  KeyEvent:"+event);
//                break;
//            /**
//             * 屏蔽掉有些按键的另一效果
//             */
//            //KEYCODE_BUTTON_X的另一效果
//            case KeyEvent.KEYCODE_DEL:
//                return true;
//            //KEYCODE_BUTTON_Y的另一效果
//            case KeyEvent.KEYCODE_SPACE:
//                return true;
//            //KEYCODE_BUTTON_A的另一效果
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                return true;
//            //KEYCODE_BUTTON_B的另一效果
//            case KeyEvent.KEYCODE_BACK:
//                return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    /**
     * 响应键盘弹起事件（这里主要用于蓝牙无线手柄）
     * @param
     * @return
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        //左摇杆
        Log.i("MotionEvent","x:"+event.getAxisValue(MotionEvent.AXIS_X)+" event:"+event);
        Log.i("MotionEvent","y:"+event.getAxisValue(MotionEvent.AXIS_Y)+" event:"+event);

        //右摇杆
        Log.i("MotionEvent","z:"+event.getAxisValue(MotionEvent.AXIS_Z)+" event:"+event);
        Log.i("MotionEvent","Rz:"+event.getAxisValue(MotionEvent.AXIS_RZ)+" event:"+event);

        //R2
        Log.i("MotionEvent","R2:"+event.getAxisValue(MotionEvent.AXIS_GAS)+" event:"+event);   //这个可能与官方图中不一样,我也是试出来的

        //L2
        Log.i("MotionEvent","L2:"+event.getAxisValue(MotionEvent.AXIS_BRAKE)+" event:"+event);

        return super.onGenericMotionEvent(event);
    }


}
