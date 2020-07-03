package app.android.intbytes.program;

import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;

import app.android.intbytes.AutoClickService;

import static app.android.intbytes.SharePoint.context;

public class Facebook extends Program {// inheritance from class named "Program"

    // close service
    public void end() {
        this.service.disableSelf();
    }

    // singleton instance
    private static Facebook instance = null;

    // singleton method
    public static Facebook GetInstance(AutoClickService service) {
        if (instance == null) {
            instance = new Facebook(service);
        }
        return instance;
    }

    @Override
    public void process(int step) {
        try {
            if (step == 0) {
                // open facebook
                this.service.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.service.startActivity(intent);// open facebook
            } else if (step == 2) {// click for timeline
                // open my page
                int x = 100;// set x
                int y = 350;// set y
                this.click(x, y);
            } else if (step >= 4 && step <= 9) {// drag for lastest post
                //
                int duration = height / 3;
                Point from = new Point(20, duration);
                Point to = new Point(20, 0);
                this.drag(from, to, duration);
                if (num_flag > this.firstPost) {
                    num_flag = 0;
                }
                this.num_flag += duration;
            } else if (step == 10) { // push
//
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Path path = new Path();
                        path.moveTo(width / 2, 400);
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        pushGestureDescription = builder
                                .addStroke(new GestureDescription.StrokeDescription(path, 10, 50000))
                                .build();
                    }
                }, 0);
//
            } else if (step == 12) { // release push and mobile will start context menu
                this.service.dispatchGesture(pushGestureDescription, null, null);
            } else if (step == 14) {// select copy of facebook message
                int height = metrics.heightPixels;
                this.click(width / 2, 400);
            } else if (step == 16) {// open gmail application
                this.OpenGmail();
            } else if (step == 18) {// click at compost area
                //
                this.click(10, 580);
            } else if (step == 20) {// push in compose area
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Path path = new Path();
                        path.moveTo(width / 2, 580);
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        pushGestureDescription = builder
                                .addStroke(new GestureDescription.StrokeDescription(path, 10, 50000))
                                .build();
                    }
                }, 0);
            } else if (step == 22) {// release push and call menu context
                this.service.dispatchGesture(pushGestureDescription, null, null);
            } else if (step == 24) {// click for past
                this.click(150, 520);
            } else if (step > 26) { // close service
                this.service.disableSelf();
            }
        } catch (Exception ex) {

        }
    }

    public Facebook(AutoClickService service) {
        super(service);
    }
}
