package com.phicomm.hu;
//courtesy of http://blog.csdn.net/stevenhu_223/article/details/8504058
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.List;
public class MainActivity extends Activity {
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    //创建浮动窗口设置布局参数的对象
    WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
    WindowManager mWindowManager;
    //** Called when the activity is first created. 
    private static final String TAG = "MainActivity";
    private Intent serviceIntent = null;
    private int PERMISSION_CODE = 110;
	private AutoClickService autoClickService = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //FloatingWindowActivity的布局视图按钮
        Button start = (Button) findViewById(R.id.start_id);
        Button remove = (Button) findViewById(R.id.remove_id);
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this, FxService.class);
//				startService(intent);
//				finish();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N || Settings.canDrawOverlays(MainActivity.this)) {
                    Intent serviceIntent = new Intent(MainActivity.this, AutoClickService.class);
                    startService(serviceIntent);
                    onBackPressed();
                } else {
                    askPermission();
                    //shortToast("You need System Alert Window Permission to do this");
                }
            }
        });
        remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //uninstallApp("com.phicomm.hu");
                Intent intent = new Intent(MainActivity.this, FxService.class);
                stopService(intent);
            }
        });
    }
    private void uninstallApp(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
        //setIntentAndFinish(true, true);
    }
    private boolean checkAccess() {
        String string = getString(R.string.accessibility_service_id);
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> list = manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo id : list) {
            if (string == id.getId()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onResume() {
        super.onResume();
        boolean hasPermission = checkAccess();
//        "has access? $hasPermission".logd()
        if (!hasPermission) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {
            askPermission();
        }
    }
    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"));
        startActivityForResult(intent, PERMISSION_CODE);
    }
    @Override
    public void onDestroy() {
    	//this.serviceIntent = this.stopService(it);
        this.autoClickService.stopSelf();
    	if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
//			return it.disableSelf();
		}
        this.autoClickService=null;
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    
   /* private void forceStopApp(String packageName) 
    {
    	 ActivityManager am = (ActivityManager)getSystemService(
                 Context.ACTIVITY_SERVICE);
    		 am.forceStopPackage(packageName);
    	 
    	Class c = Class.forName("com.android.settings.applications.ApplicationsState");
    	Method m = c.getDeclaredMethod("getInstance", Application.class);
    	
    	  //mState = ApplicationsState.getInstance(this.getApplication());
    }*/
}