/**kienbk1910
 *TODO
 * Jun 27, 2014
 */
package com.example.demozing.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author kienbk1910
 *
 */
public class VideoImageView extends ImageView {

    public VideoImageView(Context context) {
        super(context);
    }

    public VideoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int high = width/16*9;
        setMeasuredDimension(width, high);
    }

}