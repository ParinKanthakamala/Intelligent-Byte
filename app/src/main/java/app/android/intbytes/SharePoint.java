package app.android.intbytes;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.view.accessibility.AccessibilityManager;

import java.util.List;


// this class work like static property and method
public class SharePoint {
    // Context of MainActivity class
    public static Context context = null;

    // convert
//    public static int dp2px(float dpValue) {
//        float scale = Resources.getSystem().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }

    public static void OpenFacebook(Context context) {
        String uri = "fp://root";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }


    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        //List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        List<AccessibilityServiceInfo> listOfServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo enabledService : listOfServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            String myPackageName = context.getPackageName();
            String myServiceName = service.getName();
            //
            String packageName = enabledServiceInfo.packageName;
            String serviceName = enabledServiceInfo.name;
            //
            if (packageName.equals(myPackageName) && serviceName.equals(serviceName))
                return true;
        }

        return false;
    }
}
