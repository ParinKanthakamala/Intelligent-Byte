package app.android.intbytes.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

import app.android.intbytes.R;
import app.android.intbytes.SharePoint;
import app.android.intbytes.helpers.TouchAndDragListener;

public class FloatingClickService extends Service {
    private WindowManager manager;
    private View view;
    private WindowManager.LayoutParams params;
    private int xForRecord = 0;
    private int yForRecord = 0;
    private ArrayList<String> location = new ArrayList<String>();
    private int startDragDistance = 0;
    private Timer timer = null;

    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        startDragDistance = SharePoint.dp2px(10f);
        view = LayoutInflater.from(this).inflate(R.layout.widget, null);

        //setting the layout parameters
        int overlayParam =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        : WindowManager.LayoutParams.TYPE_PHONE;

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                overlayParam,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        //getting windows services and adding the floating view to it
        manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.addView(view, params);

        //adding an touchlistener to make drag movement of the floating widget

        TouchAndDragListener touchAndDragListener = new TouchAndDragListener(params);
        touchAndDragListener.startDragDistance = startDragDistance;
        touchAndDragListener.onTouch = this.viewOnClick();
//        touchAndDragListener.onDrag = manager.updateViewLayout(view, params);

        view.setOnTouchListener(touchAndDragListener);
    }

    private boolean isOn = false;

    private View.OnTouchListener viewOnClick() {

        return new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isOn) {
                    // let's off
                } else {

                }
                isOn = !isOn;
                ((TextView) view).setText((isOn) ? "ON" : "OFF");
                return false;
            }
        };


    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        if (timer == null) {

        }
        manager.removeView(view);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //"FloatingClickService onConfigurationChanged".logd()
        int x = params.x;
        int y = params.y;
        params.x = xForRecord;
        params.y = yForRecord;
        xForRecord = x;
        yForRecord = y;
        manager.updateViewLayout(view, params);
    }
}