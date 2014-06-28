package com.example.demozing;



import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.GetChars;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity {


	private Fragment mContent;
	private Context context;
	boolean isOpen;
	boolean doubleBackToExitPressedOnce;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsive_content_frame);
context=this;
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// show home as up so we can toggle
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setTitle("");
			isOpen=false;
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
        if(!hasConnection())
        	mContent = new DisConectFragment();
        else{
		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new MainFragment();	
        }
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent,"kien")
		.commit();

		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SlidingFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
		

		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			

		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	
	}
	public void toggleMenu(){
		toggle();
		
	}
	public void refresh(){
		if(hasConnection()){
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame,mContent)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	
		}
	}
	public void switchContent(final Fragment fragment) {
		
		mContent = fragment;
		if(hasConnection())
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		
		else{
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, new DisConectFragment())
			.commit();
		}
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	
	}
	@Override
	protected void onResume() {
	    super.onResume();
	    // .... other stuff in my onResume ....
	    this.doubleBackToExitPressedOnce = false;
	}
	@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	} 
	public void logout(){
		Editor editor = getSharedPreferences(Constants.KEY_STORE, Context.MODE_PRIVATE).edit();
	     editor.putBoolean(Constants.IS_LOGIN, false);
	     editor.commit(); 
		finish();
		   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	 public  boolean hasConnection() {
	
			ConnectivityManager cm = (ConnectivityManager) context .getSystemService(
	            Context.CONNECTIVITY_SERVICE);

	        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	        if (wifiNetwork != null && wifiNetwork.isConnected()) {
	          return true;
	        }

	        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	        if (mobileNetwork != null && mobileNetwork.isConnected()) {
	          return true;
	        }

	        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	        if (activeNetwork != null && activeNetwork.isConnected()) {
	          return true;
	        }

	        return false;
	      }

}
