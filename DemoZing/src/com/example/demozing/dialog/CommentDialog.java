/**kienbk1910
 *TODO
 * Jun 24, 2014
 */
package com.example.demozing.dialog;

import com.example.demozing.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author kienbk1910
 *
 */
public class CommentDialog extends DialogFragment implements OnClickListener{
	Button send;
	Button cancel;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.comment_dialog, container);
		EditText comment = (EditText) view.findViewById(R.id.comment);
		final TextView counter =(TextView) view.findViewById(R.id.counter);
		send =(Button)view.findViewById(R.id.btn_ok);
		cancel=(Button)view.findViewById(R.id.btn_cancel);
		send.setOnClickListener(this);
		cancel.setOnClickListener(this);
		comment.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				counter.setText(String.valueOf(s.length()));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		})
		;
		return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			
			break;

		case R.id.btn_ok:
			dismiss();
			break;
		}
		
	}

}
