/**kienbk1910
 *TODO
 * Jun 15, 2014
 */
package com.example.demozing.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author kienbk1910
 *
 */
public class IndexPointSlider extends View{
	private int numberPoint;
	private int currentPoint;
	private Paint paint;
	private Paint mpaint;
	private float point;

	/**
	 * @param context
	 */
	public IndexPointSlider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */

	public IndexPointSlider (Context context, AttributeSet attrs) {
		super (context, attrs);
	}
	public IndexPointSlider (Context context, AttributeSet attrs, int style) {
		super (context, attrs, style);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		paint= new Paint();
		mpaint = new Paint();
		paint.setColor(Color.WHITE);
		mpaint.setColor(Color.parseColor("#7ebd03"));
	}
	/**
	 * @param numberPoint the numberPoint to set
	 */
	public void setNumberPoint(int numberPoint) {
		this.numberPoint = numberPoint;
	}
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		  canvas.save();
		 for(int i=0;i<numberPoint;i++){
			 canvas.drawCircle(10+20*i, 20, 8, paint);
		 }
		 canvas.drawCircle(10+point, 20, 8, mpaint);
	    canvas.restore();
		super.onDraw(canvas);
	}
 /**
 * @param currentPoint the currentPoint to set
 */
public void setCurrentPoint(int currentPoint) {
	this.currentPoint = currentPoint;
	point=20*currentPoint;
	invalidate();
}
public void changePageOfset(int index,float offset){
	point=(float) (20*index+20*offset);
	invalidate();
}
}
