package com.example.demozing;

import com.example.demozing.dialog.SettingLanguageDilog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SettingActivity extends FragmentActivity{
	private LinearLayout changeLanguage ;
 @Override
protected void onCreate(Bundle arg0) {
	// TODO Auto-generated method stub
		setContentView(R.layout.setting_fragmnet);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		changeLanguage =(LinearLayout) findViewById(R.id.changeLanguage);
		changeLanguage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSettingLanguageDialog();
				
			}
		});
		
	super.onCreate(arg0);
}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
		    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

			return true;
		case R.id.action_share:
		
			break;
		}
		return super.onOptionsItemSelected(item);
	}
 private void showSettingLanguageDialog(){
		SettingLanguageDilog dialog = new SettingLanguageDilog();
		dialog.show(getSupportFragmentManager(), "settinglanguage");
		
		
	}
}
