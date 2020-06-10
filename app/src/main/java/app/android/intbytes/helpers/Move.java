package app.android.intbytes.helpers;

import android.graphics.Point;

public class Move extends Event {
    Point to;

    @Override
    void movePath() {
        path.moveTo((float) to.x, (float) to.y);
    }

    public Move(Point to) {
        this.to = to;
    }
}
