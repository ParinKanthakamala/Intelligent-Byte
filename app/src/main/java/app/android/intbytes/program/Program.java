package app.android.intbytes.program;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;

import app.android.intbytes.AutoClickService;

public abstract class Program {
    public AutoClickService service;
    public WindowManager.LayoutParams params = null;
    public DisplayMetrics metrics = null;
    public final int firstPost = 2000;
    public int num_flag = 0;
    public boolean release = true;
    public GestureDescription pushGestureDescription;
    public int width = 0;
    public int height = 0;

    public abstract void process(int step);

    public void drag(Point from, Point to, int duration) {
        Path path = new Path();
        path.moveTo((float) from.x, (float) from.y);
        path.lineTo((float) to.x, to.y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, 10, duration < 10 ? 10 : duration))
                .build();
        service.dispatchGesture(gestureDescription, null, null);
    }

    // as click at position
    public void click(int x, int y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, 10, 10))
                .build();
        service.dispatchGesture(gestureDescription, null, null);
    }


//    public void push(int x, int y, int duration) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;
//        int pre_click = 10;
//        Path path = new Path();
//        path.moveTo((float) x, (float) y);
//        GestureDescription.Builder builder = new GestureDescription.Builder();
//        GestureDescription gestureDescription = builder
//                .addStroke(new GestureDescription.StrokeDescription(path, pre_click, pre_click + duration))
//                .build();
//        service.dispatchGesture(gestureDescription, null, null);
//    }


    // open facebook application
    public void OpenFacebook() {
        try {
            this.service.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile"));
            this.service.startActivity(intent);
        } catch (Exception ex) {

        }
    }


    // open gmail application
    public void OpenGmail() {

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = this.service.getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        this.service.startActivity(intent);
    }

    // initialize program and set width and height to store screen resolution
    public Program(AutoClickService service) {
        this.service = service;// set input service to current variable service
        if (this.metrics == null) {
            this.metrics = this.service.getApplicationContext().getResources().getDisplayMetrics();
        }
        if (width == 0) width = metrics.widthPixels;
        if (height == 0) height = metrics.heightPixels;
    }
}
