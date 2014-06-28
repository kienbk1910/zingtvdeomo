/**
 * @u kienbk1910
 * @t 27 Jun 2014
 */
package com.example.demozing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

}
