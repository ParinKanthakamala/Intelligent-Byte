package app.android.intbytes.helpers;

import android.graphics.Point;

public class Swipe extends Event {
    Point from;
    Point to;

    @Override
    void movePath() {

    }

    public Swipe(Point from, Point to) {
        path.moveTo((float) from.x, (float) from.y);
        path.lineTo((float) to.x, to.y);
    }
}
