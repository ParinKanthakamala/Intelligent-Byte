package app.android.intbytes.helpers;

import android.graphics.Point;

public class Click extends Event {
    Point to;

    @Override
    void movePath() {
        path.moveTo((float) to.x, (float) to.y);
    }

    public Click(Point to) {
        this.to = to;
    }
}
