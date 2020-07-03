package app.android.intbytes.program;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import app.android.intbytes.AutoClickService;

public class Facebook extends Program {

    public void end() {
        this.service.disableSelf();
    }

    private static Facebook instance = null;

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
                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile"));
                    this.service.startActivity(intent);


            } else if (step == 2) {
                // open my page
                int x = 100;
                int y = 350;
                this.click(x, y);
            } else if (step >= 4 && step <= 9) {
                // click drag
                //
                int duration = height / 3;
                Point from = new Point(20, duration);
                Point to = new Point(20, 0);
                this.drag(from, to, duration);

                if (num_flag > this.firstPost) {
                    num_flag = 0;
                }
                this.num_flag += duration;
            } else if (step == 10) {

//                // maxx

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
            } else if (step == 12) {
                this.service.dispatchGesture(pushGestureDescription, null, null);
                // maxx : for call past
            } else if (step == 14) {
                int height = metrics.heightPixels;
                this.click(width / 2, 400);
            } else if (step == 16) {
                this.OpenGmail();

            } else if (step == 18) {
                this.click(10, 580);
            } else if (step == 20) {
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
            } else if (step == 22) {
                this.service.dispatchGesture(pushGestureDescription, null, null);

            } else if (step == 24) {
                this.click(150, 520);

            } else if (step > 30) {
                this.service.disableSelf();
            }

        } catch (Exception ex) {
            this.alert(ex.toString());
        }

    }


    public Facebook(AutoClickService service) {
        super(service);
    }
}
