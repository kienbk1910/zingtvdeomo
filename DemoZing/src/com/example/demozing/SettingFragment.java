package com.example.demozing;

import com.example.demozing.ProgramFragmentActivity.RatingTask;
import com.example.demozing.dialog.RateDialog;
import com.example.demozing.dialog.SettingLanguageDilog;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SettingFragment extends Fragment {
	private LinearLayout changeLanguage ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.setting_fragmnet, null);
		getActivity().getActionBar().removeAllTabs();
		getActivity().getActionBar().setNavigationMode(
		ActionBar.NAVIGATION_MODE_STANDARD);
		changeLanguage =(LinearLayout) root.findViewById(R.id.changeLanguage);
		changeLanguage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSettingLanguageDialog();
				
			}
		});
		
		return root;
	}
	private void showSettingLanguageDialog(){
		SettingLanguageDilog dialog = new SettingLanguageDilog();
		dialog.show(getFragmentManager(), "settinglanguage");
		
		
	}
}
