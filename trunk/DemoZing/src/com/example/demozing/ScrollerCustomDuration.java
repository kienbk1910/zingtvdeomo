/**kienbk1910
 *TODO
 * Jun 15, 2014
 */
package com.example.demozing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author kienbk1910
 *
 */
public class ScrollerCustomDuration extends Scroller {

    private double mScrollFactor = 1;

    public ScrollerCustomDuration(Context context) {
        super(context);
    }

    public ScrollerCustomDuration(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @SuppressLint("NewApi")
    public ScrollerCustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
    }

}