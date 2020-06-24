package app.android.intbytes.program;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import app.android.intbytes.AutoClickService;
import app.android.intbytes.R;

public class Facebook extends Program {

    public void end() {
        this.service.disableSelf();
    }

    @Override
    public int process(int step) {
        // NO-OP

        //this.alert("onAccessibilityEvent");
        try {
            if (step == 0) {
                try {
                    this.service.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page"));
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile"));

                    this.service.startActivity(intent);
                } catch (Exception ex) {
                    this.alert(ex.toString());
                }

            } else if (step == 1) {
                // click menu
                int x = width - 100;
                int y = 350;
                View view = LayoutInflater.from(this.service).inflate(R.layout.widget, null);

                this.click(x, y);

            } else if (step == 2) {
                //click for my page
                this.end();
                this.click(300, 300);
            } else if (step == 3) {
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
                    }
                }, 0);

            } else if (step == 5) {
                this.service.dispatchGesture(pushGestureDescription, null, null);
                // maxx : for call past

            } else if (step == 6) {
                int height = metrics.heightPixels;
                this.click(100, (height / 2) - 10);

            } else if (step == 7) {
                this.OpenGmail();
                this.click(10, 580);

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

                    }
                }, 0);
            } else if (step == 9) {
                this.service.dispatchGesture(pushGestureDescription, null, null);

            } else if (step == 10) {
                this.click(150, 520);

            } else {
                this.alert("Everything complete");
                this.service.disableSelf();
            }
            this.alert("current step : " + step);

        } catch (Exception ex) {
            this.alert(ex.toString());
        }
        return step += 1;
    }


    public Facebook(AutoClickService service) {
        super(service);
    }
}
