package com.example.demozing.dialog;

import java.util.Locale;

import com.example.demozing.MainActivity;
import com.example.demozing.R;
import com.example.demozing.SettingActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;

public class SettingLanguageDilog extends DialogFragment implements View.OnClickListener{
	private RadioButton vn;
	private RadioButton en;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.setting_language_dialog, container);
	    vn =(RadioButton) view.findViewById(R.id.radioButton1);
	    en =(RadioButton) view.findViewById(R.id.radioButton2);
	    vn.setOnClickListener(this);
	    en.setOnClickListener(this);
		return view;
	}
	public void setOnChangeLanguage(){
		if(callBack!=null){
			//callBack.setChangeLanguage(language)
		}
	}
	ChangeLanguage callBack;
  interface ChangeLanguage{
	public void setChangeLanguage(String language);
  }
@Override
public void onClick(View v) {

	switch (v.getId()) {
	case R.id.radioButton1:
		setLocale("vn");
		break;
	case R.id.radioButton2:
		setLocale("en");
		break;

	default:
		break;
	}
	dismiss();
	
}
public void setLocale(String lang) { 
	Locale myLocale;
	if(!lang.equals("vn"))
	 myLocale = new Locale(lang); 
	else {
		myLocale = Locale.getDefault();
	}
Resources res = getResources(); 
DisplayMetrics dm = res.getDisplayMetrics(); 
Configuration conf = res.getConfiguration(); 
conf.locale = myLocale; 
res.updateConfiguration(conf, dm); 
Intent refresh = new Intent(getActivity(), SettingActivity.class); 
startActivity(refresh); 
getActivity().finish();
} 
}
