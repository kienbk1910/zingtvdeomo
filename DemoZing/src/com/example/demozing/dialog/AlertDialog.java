/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing.dialog;


import com.example.demozing.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author kienbk1910
 *
 */
public class AlertDialog extends DialogFragment implements View.OnClickListener{
	Button cancel;
	Button ok;
	private final static String TITLE="title";
	private final static String MSG="msg";
	public static AlertDialog newAlertDialog (String title,String msg) {
		// TODO Auto-generated constructor stub
		AlertDialog dialog = new AlertDialog();
		Bundle bundle =new Bundle();
		bundle.putString(TITLE, title);
		bundle.putString(MSG, msg);
		dialog.setArguments(bundle);
		return dialog;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.alertdialog, container);
		TextView title =(TextView) view.findViewById(R.id.titlevideo);
		TextView msg =(TextView) view.findViewById(R.id.msg);
		title.setText(getArguments().getString(TITLE));
		msg.setText(getArguments().getString(MSG));
		cancel=(Button)view.findViewById(R.id.btn_cancel);
		ok=(Button)view.findViewById(R.id.btn_ok);
       
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
			if(callback !=null)
				callback.OnAgreeListener();
			dismiss();
			break;

		default:
			break;
		}
		
	}

public interface AgreeListener {
		void OnAgreeListener();
	}

AgreeListener callback;

	public void setAgreeListenerr(
			AgreeListener agreeListener) {
		this.callback = agreeListener;
	}

}
