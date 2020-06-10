package app.android.intbytes;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;

public class AutoClickService extends AccessibilityService {

    private int step = 0;
    private WindowManager.LayoutParams params = null;
    private DisplayMetrics metrics = null;
    private final int firstPost = 2000;
    private int num_flag = 0;
    private boolean release = true;
    private GestureDescription pushGestureDescription;

    private int width = 0;
    private int height = 0;

    public void alert(String message) {
        Log.d("AutoClickService", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // NO-OP

        if (this.metrics == null) {
            this.metrics = getApplicationContext().getResources().getDisplayMetrics();
        }
        if (width == 0) width = metrics.widthPixels;
        if (height == 0) height = metrics.heightPixels;

        //this.alert("onAccessibilityEvent");
        try {
            if (step == 0) {
                this.OpenFacebook();
                step = 1;
            } else if (step == 1) {
                // click menu
                this.click(width - 100, 200);
                step = 2;
            } else if (step == 2) {
                //click for my page
                this.click(300, 300);
                step = 3;
            } else if (step == 3) {
                // click drag
                //
                int duration = 500;
                Point from = new Point(20, 500);
                Point to = new Point(20, 0);
                this.drag(from, to, duration);

                if (num_flag > this.firstPost) {
                    num_flag = 0;
                    step = 4;
                }
                this.num_flag += 500;
            } else if (step == 4) {
                // maxx
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Path path = new Path();
                        path.moveTo(width / 2, height / 2);
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        pushGestureDescription = builder
                                .addStroke(new GestureDescription.StrokeDescription(path, 10, 50000))
                                .build();
                        step = 5;
                    }
                }, 0);

            } else if (step == 5) {
                dispatchGesture(pushGestureDescription, null, null);
                // maxx : for call past
                step = 6;
            } else if (step == 6) {
                int height = metrics.heightPixels;
                this.click(100, (height / 2) - 10);
                step = 7;
            } else if (step == 7) {
                this.OpenGmail();
                this.click(10, 580);
                step = 8;
            } else if (step == 8) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Path path = new Path();
                        path.moveTo(width / 2, 580);
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        pushGestureDescription = builder
                                .addStroke(new GestureDescription.StrokeDescription(path, 10, 50000))
                                .build();
                        step = 9;
                    }
                }, 0);
            } else if (step == 9) {
                dispatchGesture(pushGestureDescription, null, null);
                step = 10;
            } else if (step == 10) {
                this.click(150, 520);
                step = 11;
            } else {
                this.alert("Everything complete");
                this.disableSelf();
            }
            this.alert("current step : " + step);

        } catch (Exception ex) {
            this.alert(ex.toString());
        }
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
        this.alert("start application");

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
