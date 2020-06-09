package com.phicomm.hu;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

public class AutoClickService extends AccessibilityService {


    //      val events = mutableListOf<Event>();
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // NO-OP
    }

    @Override
    public void onInterrupt() {
        // NO-OP
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

    }

    public void click(int x, int y) {


    }


    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
