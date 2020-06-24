package app.android.intbytes;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;

import app.android.intbytes.program.Facebook;

public class AutoClickService extends AccessibilityService {

    private int step = 0;
    private WindowManager.LayoutParams params = null;
    private DisplayMetrics metrics = null;
    private final int firstPost = 2000;
    private int num_flag = 0;
    private boolean release = true;
    private GestureDescription pushGestureDescription;

    public int width = 0;
    public int height = 0;

    public void CreateDebugImage() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.widget, null);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        // Add layout to window manager
        wm.addView(myView, params);
    }

    public void alert(String message) {
        Log.d("AutoClickService", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!SharePoint.OnSession) {
            return;
        }
        if (this.metrics == null) {
            this.metrics = getApplicationContext().getResources().getDisplayMetrics();
        }
        if (width == 0) width = metrics.widthPixels;
        if (height == 0) height = metrics.heightPixels;
        this.step = new Facebook(this).process(this.step);

//        this.step++;

        this.alert("step : " + this.step);
    }

    private void OpenFacebook() {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page"));
            startActivity(intent);
        } catch (Exception ex) {
            this.alert(ex.toString());
        }
    }

    private void OpenGmail() {

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(intent);
    }

    @Override
    public void onInterrupt() {
        // NO-OP
        //this.alert("onInterrupt");
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        //this.alert("onServiceConnected");
        //this.alert("start application");

    }

    public void drag(Point from, Point to, int duration) {
        Path path = new Path();
        path.moveTo((float) from.x, (float) from.y);
        path.lineTo((float) to.x, to.y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, 10, duration))
                .build();
        dispatchGesture(gestureDescription, null, null);
    }

    public void click(int x, int y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, 10, 10))
                .build();
        dispatchGesture(gestureDescription, null, null);
    }

    public void double_click(int x, int y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;
        for (int i = 0; i < 2; i++) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder
                    .addStroke(new GestureDescription.StrokeDescription(path, 10, 5))
                    .build();
            dispatchGesture(gestureDescription, null, null);
        }
    }


    public void push(int x, int y, int duration) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;
        int pre_click = 10;
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, pre_click, pre_click + duration))
                .build();
        dispatchGesture(gestureDescription, null, null);
    }

    public void release() {
        this.alert("Release");
    }


    @Override
    public boolean onUnbind(Intent intent) {
        this.alert("onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        this.alert("good bye");
        super.onDestroy();
    }
}