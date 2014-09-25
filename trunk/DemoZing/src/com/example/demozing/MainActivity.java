package com.example.demozing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.example.config.Common;
import com.example.demozing.model.Comment;
import com.example.demozing.model.Video;
import com.example.demozing.provider.SuggestionAdapter;
import com.example.demozing.provider.SuggestionProvider;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity {
	private boolean  upload;
	public static final int PICK_FILE_UPLOAD=1910;
	private Fragment mContent;
	private Context context;
	boolean isOpen;
	boolean doubleBackToExitPressedOnce;
	private SearchView searchView;
	Cursor c ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsive_content_frame);
		context = this;
		upload =false;
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// show home as up so we can toggle
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setTitle("");
			isOpen = false;
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		if (!hasConnection())
			mContent = new DisConectFragment();
		else {
			// set the Above View Fragment
			if (savedInstanceState != null)
				mContent = getSupportFragmentManager().getFragment(
						savedInstanceState, "mContent");
			if (mContent == null)
				mContent = new MainFragment();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent, "kien").commit();

		// set the Behind View Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlidingFragment()).commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
		getActionBar().setDisplayUseLogoEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.main, menu);
		 MenuItem uploadItem = menu.findItem(R.id.upload);
		 uploadItem.setVisible(upload);
		 // search
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		 searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		getActionBar().setIcon(R.drawable.logo);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				new searchTask().execute();
				return true;
			}
		});
		searchView.setOnSuggestionListener(new OnSuggestionListener() {
			
			@Override
			public boolean onSuggestionSelect(int position) {
				// TODO Auto-generated method stub

				return false;
			}
			
			@Override
			public boolean onSuggestionClick(int position) {
				// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);

				return true;
			}
		});
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
	    break;
		case R.id.upload:
			  chooseFile();
		 break;
		}

		return super.onOptionsItemSelected(item);
	}
	public void chooseFile(){
		 Intent intent = new Intent();
		 intent.setType("video/*");
		 intent.setAction(Intent.ACTION_PICK);
		 startActivityForResult(intent, MainActivity.PICK_FILE_UPLOAD);
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);

	}

	public void toggleMenu() {
		toggle();

	}

	public void refresh() {
		if (hasConnection()) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, mContent).commit();
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
		if (hasConnection()){
			if ( fragment instanceof MyUpLoadFragment){
				
				upload=true;
			}
			else{
				upload =false;
			}
			invalidateOptionsMenu() ;
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

		}else {
			
			getSupportFragmentManager().beginTransaction()
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
		Toast.makeText(this, "Nhấn phím Back lần nữa để thoát",
				Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}

	public void logout() {
		Editor editor = getSharedPreferences(Constants.KEY_STORE,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(Constants.IS_LOGIN, false);
		editor.commit();
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	public boolean hasConnection() {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}
	public void setting(){
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		startActivity(intent);
	}
  class searchTask extends AsyncTask<String,String,String> {

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String URL = SuggestionProvider.URL;
		Uri friends = Uri.parse(URL);
		 c = getContentResolver().query(friends, null, null, null, "name");
	
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		searchView.setSuggestionsAdapter( new SuggestionAdapter(MainActivity.this, c));
		super.onPostExecute(result);
	}
	  
  }
  @Override
protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub
	  if (arg0 == PICK_FILE_UPLOAD) {
          if (arg1 == RESULT_OK) {
        	    Uri videoUri = arg2.getData();
                 String filePath="";                      
                if (videoUri != null && "content".equals(videoUri.getScheme())) {
                    Cursor cursor = this.getContentResolver().query(videoUri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                    cursor.moveToFirst();   
                    filePath = cursor.getString(0);
                    cursor.close();
                }
                else {
                    filePath = videoUri.getPath();
                }
              
                try {
                	  File file = new File(filePath);
					FileInputStream videoInput = new FileInputStream(file);
	
					if(videoInput.available()>(30*1024*1024)){
						Toast.makeText(this, "Sorry!File size must be less than 30M ", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent = new Intent(this, UploadActivity.class);
	              	intent.putExtra(Common.URI_FILE_UPLOAD, filePath);
	  				startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
          }
      }
	
}
}
