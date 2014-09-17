package com.example.demozing.provider;



import com.androidquery.AQuery;
import com.example.demozing.PlayVideoActivity;
import com.example.demozing.R;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestionAdapter extends CursorAdapter{
	 private LayoutInflater mInflater;
	  AQuery aQuery;
	public SuggestionAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		 mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(final View arg0, final Context arg1, final Cursor arg2) {
		// TODO Auto-generated method stub
		ImageView image =(ImageView) arg0.findViewById(R.id.image);
		TextView  title =(TextView) arg0.findViewById(R.id.title);
		title.setText(arg2.getString(arg2.getColumnIndex("title")));
		arg0.setTag(arg2);
		 AQuery query = new AQuery(arg1);
		    query.id(image).image(arg2.getString(arg2.getColumnIndex("urlImage")), true, true, 0, 0, null, 0,1.0f);
		 
	}
	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return mInflater.inflate(R.layout.item_suggestion, arg2, false);
	}

}
