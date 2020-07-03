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
    private DisplayMetrics metrics = null;
    public int width = 0;
    public int height = 0;

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

        Facebook.GetInstance(this).process(this.step++);

        this.alert("step : " + this.step);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
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