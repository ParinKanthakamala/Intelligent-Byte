package app.android.intbytes.helpers;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;

public abstract class Event {
    private long startTime = 10L;
    private long duration = 10L;
    public Path path;

    abstract void movePath();

    public GestureDescription.StrokeDescription onEvent() {
        path = new Path();
        movePath();
        return new GestureDescription.StrokeDescription(path, startTime, duration);
    }


}
