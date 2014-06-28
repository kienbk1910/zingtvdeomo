/**kienbk1910
 *TODO
 * Jun 27, 2014
 */
package com.example.demozing.custom;

import com.example.demozing.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author kienbk1910
 *
 */
public class VideoComponent extends RelativeLayout{
	private VideoImageView image;
	private TextView title;
	private TextView subtitle;
	private ProgressBar progressBar;

	/**
	 * @param context
	 */
	public VideoComponent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

  

    public VideoComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View root =inflate(getContext(), R.layout.video_small_item, this);
        image = (VideoImageView)root.findViewById(R.id.image);
        title =(TextView)root.findViewById(R.id.title);
        subtitle=(TextView)root.findViewById(R.id.subtitle);
        progressBar =(ProgressBar)root.findViewById(R.id.progressBar);
    }
    public void  setImage(int resId){
    	image.setImageResource(resId);
    }
    public VideoImageView getImageView(){
    	return image;
    }
    public ProgressBar getProgressBar(){
    	return progressBar;
    }
    public void setTitle(CharSequence text){
    	title.setText(text);
    }
    public void setSubTitle(CharSequence text){
    	subtitle.setText(text);
    }
}
