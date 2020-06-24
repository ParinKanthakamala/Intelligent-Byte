package app.android.intbytes;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
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

public class MyAccessibilityService extends AccessibilityService {


    public void alert(String message) {
        Log.d("AutoClickService", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }


    @Override
    public void onInterrupt() {
        // NO-OP

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