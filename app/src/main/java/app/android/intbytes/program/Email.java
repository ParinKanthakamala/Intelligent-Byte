package app.android.intbytes.program;

import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;

import java.util.List;

import app.android.intbytes.AutoClickService;

public class Email extends Program {

    @Override
    public int process(int step) {
        // NO-OP
        switch (step) {
            case 1: {
                // maxx : open inbox
                try {
                    Intent intent = service.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                    service.startActivity(intent);
                } catch (Exception ex) {
                    this.alert(ex.toString());
                }
            }
            break;
            case 2: {
                // maxx : scroll
                try {
                    double percentage_from_screen = (this.height * 0.3);
                    int dest = (this.height - 200);
                    Point from = new Point(200, dest);
                    Point to = new Point(200, (int) (from.y - percentage_from_screen));
                    this.drag(from, to, 500);
                } catch (Exception ex) {
                    this.alert(ex.toString());
                }
            }
            break;
            case 3: {
                // maxx : select email
                this.click(this.width / 2, this.height / 2);
            }
            break;
            case 4: {
                // maxx :  call context menu
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Path path = new Path();
                        path.moveTo(width / 4, (height / 2) - 10);
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        pushGestureDescription = builder
                                .addStroke(new GestureDescription.StrokeDescription(path, 10, 50000))
                                .build();

                    }
                }, 0);

            }
            break;
            case 5: {
                service.dispatchGesture(pushGestureDescription, null, null);
            }
            break;
            case 6: {
                // maxx : click copy
                this.click(5, (height / 2) - 50);

            }

            break;
            case 7: {
                ClipboardManager clipBoard = (ClipboardManager) service.getSystemService(service.CLIPBOARD_SERVICE);
                ClipData clipData = clipBoard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String text = item.getText().toString();
                this.alert(text);
            }
            break;
            case 8: {
                try {
                    Intent intent = service.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                    service.startActivity(intent);
                } catch (Exception ex) {
                    this.alert(ex.toString());
                }

            }
            break;

            case 10: {
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
            }
            break;
            case 11: {
                service.dispatchGesture(pushGestureDescription, null, null);
            }
            break;
            case 12: {
                this.click(150, 520);
            }
            break;
            case 98: {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                PackageManager pm = service.getPackageManager();
                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                service.startActivity(intent);
            }
            break;
            case 99: {
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
            }
            break;
            case 100: {
                service.dispatchGesture(pushGestureDescription, null, null);
            }
            default: {
                this.service.disableSelf();
            }
            break;
        }
        return step++;
    }

    public Email(AutoClickService service) {
        super(service);
    }
}
