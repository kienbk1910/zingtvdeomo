package com.example.demozing;

import java.io.IOException;
import java.util.ArrayList;

import com.example.demozing.R;
import com.example.demozing.dialog.ShareChooserDialog;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @u kienbk1910
 * @t 17 Jun 2014
 */

/**
 * @author kienbk1910
 * 
 */
public class PlayVideoActivity extends FragmentActivity implements
		SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,
		VideoControllerView.MediaPlayerControl, OnNavigationListener {

	SurfaceView videoSurface;
	MediaPlayer player;
	VideoControllerView controller;
	SurfaceHolder videoHolder;
	ProgressBar progressBar;
	ViewPager pager;
	TabsHostPagerAdapter adapter;
	TabHost myTabHost;
	int buffer=0;
	private ShareActionProvider mShareActionProvider;
boolean isfullScreen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		// action bar
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ArrayList<String> itemList = new ArrayList<String>();
		itemList.add("240p");
		itemList.add("360p");
		itemList.add("480p");
		ArrayAdapter<String> aAdpt = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				itemList);
		getActionBar().setListNavigationCallbacks(aAdpt, this);

		videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		pager = (ViewPager) findViewById(R.id.pager);

		myTabHost = (TabHost) findViewById(R.id.TabHost01);

		myTabHost.setup();

		setupTab(new TextView(this), "Thông tin");
		setupTab(new TextView(this), "Liên quan");
		setupTab(new TextView(this), "Bình luận");
		isfullScreen=false;
		myTabHost.setCurrentTab(1);
		myTabHost.setCurrentTab(0);
		adapter = new TabsHostPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				myTabHost.setCurrentTab(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		myTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(myTabHost.getCurrentTab());
			}
		});
		videoSurface.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				controller.show();
				return false;
			}
		});
		

		videoHolder = videoSurface.getHolder();
		resizeVideo();
		videoHolder.addCallback(this);

	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.d("kienbk1910", "onresume");
	     if(player!= null && player.isPlaying())
	    	 player.start();
		super.onResume();
	}

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("kienbk1910", "surfaceChanged");

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("kienbk1910", "surfaceCreated");

		player = new MediaPlayer();
		player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				// TODO Auto-generated method stub
				buffer=percent;
			}
		});
		player.setScreenOnWhilePlaying(true);
		controller = new VideoControllerView(this);

		try {
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource(this, Uri
					.parse("http://tv7.hot2.cache10.vcdn.vn/streaming/4b4b99222aa7e46b7d93ba0dcd51fd3d/53aa3e24/2014/0623/22/1cc2afeb8860d5b56a7d919ccde03145.mp4?format=f360&device=web_flash&start=0"));
			player.setOnPreparedListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.setDisplay(holder);
		player.prepareAsync();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("kienbk1910", "surfaceDestroyed");
		if(player.isPlaying())
			player.stop();
		player.release();
		player = null;
	}

	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener
	@Override
	public void onPrepared(MediaPlayer mp) {
		controller.setMediaPlayer(this);
		controller
				.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
		player.start();
		progressBar.setVisibility(View.GONE);
	}
	

	// End MediaPlayer.OnPreparedListener

	// Implement VideoMediaController.MediaPlayerControl
	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return buffer;
	}

	@Override
	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return player.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void seekTo(int i) {
		player.seekTo(i);
	}

	@Override
	public void start() {
		player.start();
	}

	@Override
	public boolean isFullScreen() {
		return isfullScreen;
	}

	@Override
	public void toggleFullScreen() {
		int configuration = getResources().getConfiguration().orientation;
		if (configuration != Configuration.ORIENTATION_LANDSCAPE){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

	}

	// End VideoMediaController.MediaPlayerControl
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onConfigurationChanged(android
	 * .content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		resizeVideo();
	}

	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(myTabHost.getContext(), tag);

		TabSpec setContent = myTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		myTabHost.addTab(setContent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		Log.d("kienbk1910", "onstart");
		// TODO Auto-generated method stub
		getWindow().setWindowAnimations(R.style.in_right_out_right_animation);

		super.onStart();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
			return true;
		case R.id.action_share:
			showChooser();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	public void resizeVideo() {
		int configuration = getResources().getConfiguration().orientation;
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getActionBar().hide();
			videoHolder.setFixedSize(screenWidth, screenHeight);
			isfullScreen=true;
		} else if (configuration == Configuration.ORIENTATION_PORTRAIT) {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

			getActionBar().show();
			float screenProportion = (float) screenWidth / (float) screenHeight;
			isfullScreen=false;
			videoHolder.setFixedSize(screenWidth,
					(int) (screenWidth * screenProportion));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.OnNavigationListener#onNavigationItemSelected(int,
	 * long)
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_play_video_activity, menu);
		// share action
		
		// search action
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.d("kienbk1910", "onPause");
		player.pause();
		super.onPause();
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("kienbk1910", "onDestroy");
		super.onDestroy();
	}
	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, "Whatever message you want to share");
		intent.setType("text/plain");
		return intent;
	}
	private void showChooser(){
		ShareChooserDialog dialog = new ShareChooserDialog();
		dialog.show(getSupportFragmentManager(), "chooser");
	}
}
