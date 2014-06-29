/**
 * @u kienbk1910
 * @t 27 Jun 2014
 */
package com.example.demozing;

import com.androidquery.AQuery;
import com.example.config.Common;
import com.example.demozing.InfomatinFragment.LikeTask;
import com.example.demozing.InfomatinFragment.RatingTask;
import com.example.demozing.InfomatinFragment.SubcriabeTask;
import com.example.demozing.custom.SquareImageView;
import com.example.demozing.dialog.RateDialog;
import com.example.demozing.model.Program;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

/**
 * @author kienbk1910
 *
 */
public class ProgramFragmentActivity extends FragmentActivity {
	ViewPager pager;
	TabsProgramAdapter adapter;
	TabHost myTabHost;
	Program program;
	SquareImageView image;
	TextView title;
	TextView category;
	DrawableAlignedButton rate;
	DrawableAlignedButton like;
	DrawableAlignedButton subcribe;
	ProgressBar progressBar1;
	ProgressBar progressBar2;
	ProgressBar progressBar3;
	boolean islike;
	boolean isSubcribe;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.program_activity);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		pager = (ViewPager) findViewById(R.id.pager);
		program= (Program) getIntent().getSerializableExtra(Common.PROGRAM);
		// header
		title=(TextView)findViewById(R.id.title);
		image=(SquareImageView)findViewById(R.id.image);
		category=(TextView)findViewById(R.id.type);
		title.setText(program.getTitle());
		category.setText(program.getCategory());
		AQuery aQuery = new AQuery(this);
		aQuery.id(image).image(program.getUrl(), true, true);
		rate = (DrawableAlignedButton) findViewById(R.id.rate);
		like = (DrawableAlignedButton)findViewById(R.id.like);
		subcribe = (DrawableAlignedButton)findViewById(R.id.subcribe);
		progressBar1 = (ProgressBar)findViewById(R.id.progressBar);
		progressBar2 = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar3 = (ProgressBar) findViewById(R.id.progressBar2);
		progressBar1.setVisibility(View.GONE);
		progressBar2.setVisibility(View.GONE);
		progressBar3.setVisibility(View.GONE);
		isSubcribe=false;
		islike = false;
		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			       new LikeTask().execute(islike);
			}
		});
		rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRateDialog();
			}
		});
		subcribe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SubcriabeTask().execute(isSubcribe);
			}
		});
		myTabHost = (TabHost) findViewById(R.id.TabHost01);

		myTabHost.setup();

		setupTab(new TextView(this), "Nội dung");
		setupTab(new TextView(this), "Bình luận");
		myTabHost.setCurrentTab(1);
		myTabHost.setCurrentTab(0);
		adapter = new TabsProgramAdapter(getSupportFragmentManager());
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
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
		    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			return true;
		
		}
		return super.onOptionsItemSelected(item);
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

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	public void showRateDialog() {
		RateDialog dialog = new RateDialog();
		dialog.show(getSupportFragmentManager(), "ratedialog");
		dialog.setChangeRatingListener(new RateDialog.ChangeRatingListener() {

			@Override
			public void onChangeRatingDialog(float rating) {
				// TODO Auto-generated method stub
				new RatingTask().execute((int) rating);
			}
		});

	}

	class RatingTask extends AsyncTask<Integer, Integer, Integer> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			rate.setVisibility(View.INVISIBLE);
			progressBar1.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			rate.setVisibility(View.VISIBLE);
			rate.setText(String.valueOf((int) result) + "/10");
			rate.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.rate_star_big_on, 0, 0, 0);
			rate.setEnabled(false);
			progressBar1.setVisibility(View.GONE);
			super.onPostExecute(result);
		}

	}
	class LikeTask extends AsyncTask<Boolean, Integer, Boolean>{
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar2.setVisibility(View.VISIBLE);
			like.setVisibility(View.INVISIBLE);
			super.onPreExecute();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {
				like.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.like_med_off, 0, 0, 0);
				islike=false;
			} else {
				like.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.like_med_on, 0, 0, 0);
				islike=true;
			}
			
			progressBar2.setVisibility(View.GONE);
			like.setVisibility(View.VISIBLE);
			super.onPostExecute(result);
		}
	}
	class SubcriabeTask extends AsyncTask<Boolean, Integer, Boolean>{
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar3.setVisibility(View.VISIBLE);
			subcribe.setVisibility(View.INVISIBLE);
			super.onPreExecute();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {
				subcribe.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.ic_subscribe, 0, 0, 0);
				isSubcribe=false;
			} else {
				subcribe.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.ic_subscribed, 0, 0, 0);
				isSubcribe=true;
			}
			
			progressBar3.setVisibility(View.GONE);
			subcribe.setVisibility(View.VISIBLE);
			super.onPostExecute(result);
		}
	}

}
