package app.android.intbytes;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import app.android.intbytes.program.Facebook;

// this class for main accessibility service
public class AutoClickService extends AccessibilityService {

    private int step = 0; // step
    private DisplayMetrics metrics = null;
    public int width = 0;
    public int height = 0;

    public void alert(String message) {
        Log.d("AutoClickService", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (this.metrics == null) {
            this.metrics = getApplicationContext().getResources().getDisplayMetrics();// get Display metrics
        }
        if (width == 0) width = metrics.widthPixels;// get resolution with
        if (height == 0) height = metrics.heightPixels;// get resolution height
        Facebook.GetInstance(this).process(this.step++);// manage for facebook process and send step
        this.alert("step : " + this.step);// show step message
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
        this.alert("onUnBind"); // show message when close service
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        this.alert("good bye");// show message when service Destroy complete
        super.onDestroy();
    }
}