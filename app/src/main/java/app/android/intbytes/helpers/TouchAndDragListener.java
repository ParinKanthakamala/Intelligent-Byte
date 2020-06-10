package app.android.intbytes.helpers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TouchAndDragListener implements View.OnTouchListener {


    private int initialX = 0;
    private int initialY = 0;
    private float initialTouchX = 0;
    private float initialTouchY = 0;
    private boolean isDrag = false;
    WindowManager.LayoutParams params;
    public View.OnTouchListener onTouch;
    public View.OnTouchListener onDrag;
    public int startDragDistance = 10;

    public TouchAndDragListener(WindowManager.LayoutParams params) {
        this.params = params;
    }


    private boolean isDragging(MotionEvent event) {
        //((Math.pow((event.rawX - initialTouchX).toDouble(), 2.0) + Math.pow((event.rawY - initialTouchY).toDouble(), 2.0)) > startDragDistance * startDragDistance);
        return false;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isDrag = false;
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isDrag && isDragging(event)) {
                    isDrag = true;
                }
                if (!isDrag) return true;
                params.x = (int) (initialX + (event.getRawX() - initialTouchX));
                params.y = (int) (initialY + (event.getRawY() - initialTouchY));
//                onDrag ?.invoke();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (!isDrag) {
//                    this.onTouch();
//                    onTouch ?.invoke()
                    return true;
                }
            }

        }


        return false;
    }
}
