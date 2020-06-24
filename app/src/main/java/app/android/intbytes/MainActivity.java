package app.android.intbytes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.android.intbytes.services.FloatingClickService;

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

    //
    private static Context context = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this.getApplicationContext();
        //
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
//        askPermission();
        //
        Button start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SharePoint.OnSession = true;
                    //startService(new Intent(MainActivity.this, FloatingClickService.class));
                    startService(new Intent(MainActivity.this, AutoClickService.class));
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//
//



    }

    private void uninstallApp(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
        //setIntentAndFinish(true, true);
    }

    private boolean checkAccess() {

//        return SharePoint.isAccessibilityServiceEnabled(this.getBaseContext(), AutoClickService.class);
        boolean enabled = SharePoint.isAccessibilityServiceEnabled(this, AutoClickService.class);
        return enabled;

    }

    @Override
    public void onResume() {
        super.onResume();
        boolean hasPermission = checkAccess();
        if (!hasPermission) {
            //Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            //startActivity(intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //askPermission();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			return it.disableSelf();
        }
        this.autoClickService = null;
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