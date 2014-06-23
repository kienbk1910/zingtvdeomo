/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing.dialog;


import com.example.demozing.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;

/**
 * @author kienbk1910
 *
 */
public class RateDialog extends DialogFragment implements View.OnClickListener{
	Button cancel;
	Button ok;
	RatingBar bar1;
	RatingBar bar2;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.rate_dialog, container);
		bar1=(RatingBar)view.findViewById(R.id.ratingBar1);
		bar2=(RatingBar)view.findViewById(R.id.ratingBar2);
		cancel=(Button)view.findViewById(R.id.btn_cancel);
		ok=(Button)view.findViewById(R.id.btn_ok);
        bar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser)
					bar2.setRating(0);
				
			}
		});
        
        bar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			
     			@Override
     			public void onRatingChanged(RatingBar ratingBar, float rating,
     					boolean fromUser) {
     				// TODO Auto-generated method stub
     				if(fromUser)
     				bar1.setRating(5);
     				
     			}
     		});
		
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);
	
		return view;
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stu
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			
			break;
		case R.id.btn_ok:
			callback.onChangeRatingDialog(bar1.getRating()+bar2.getRating());
			dismiss();
			break;

		default:
			break;
		}
		
	}

	public interface ChangeRatingListener {
		void onChangeRatingDialog(float rating);
	}

	ChangeRatingListener callback;

	public void setChangeRatingListener(
			ChangeRatingListener changeRatingListener) {
		this.callback = changeRatingListener;
	}

}
