/**kienbk1910
 *TODO
 * Jun 27, 2014
 */
package com.example.demozing.custom;

import com.example.demozing.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author kienbk1910
 *
 */
public class ProgramComponent extends RelativeLayout{
	private SquareImageView image;
	private TextView title;

	/**
	 * @param context
	 */
	public ProgramComponent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

  

    public ProgramComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgramComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View root =inflate(getContext(), R.layout.program_component, this);
        image = (SquareImageView)root.findViewById(R.id.image);
        title =(TextView)root.findViewById(R.id.title);
    }
    public void  setImage(int resId){
    	image.setImageResource(resId);
    }
    public SquareImageView getImageView(){
    	return image;
    }
    public void setTitle(CharSequence text){
    	title.setText(text);
    }
}
