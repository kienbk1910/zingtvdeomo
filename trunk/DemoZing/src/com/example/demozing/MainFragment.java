/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import java.util.Timer;
import java.util.TimerTask;

import com.androidquery.AQuery;
import com.example.demozing.slider.IndexPointSlider;

import android.app.ActionBar;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnGenericMotionListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author kienbk1910
 *
 */
public class MainFragment extends Fragment {
	/**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private IndexPointSlider pointSlider;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    Timer timer= new Timer();
    int[] id={R.id.video0,R.id.video1,R.id.video2,R.id.video3,R.id.video4,R.id.video5,R.id.video6,R.id.video7,R.id.video8};
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mPager.setCurrentItem((mPager.getCurrentItem()+1)%5);
        }
    };
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root =inflater.inflate(R.layout.main_screen, null);
		  
		getActivity().getActionBar().removeAllTabs();
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);       
		 mPager = (ViewPager) root.findViewById(R.id.pager);
		 mPager.setOffscreenPageLimit(5);
		 pointSlider=(IndexPointSlider)root.findViewById(R.id.indexSlider);
		 pointSlider.setNumberPoint(5);
	        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
	        mPager.setAdapter(mPagerAdapter);
	       // mPager.setScrollDurationFactor(2);
	        mPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
				Log.d("kienbk1910", "selected"+arg0);
				pointSlider.setCurrentPoint(arg0);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					pointSlider.changePageOfset(arg0,arg1);
					  mPager.getParent().requestDisallowInterceptTouchEvent(true);
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
				//	pointSlider.setCurrentPoint(arg0);
					Log.d("kienbk1910", "selected--"+arg0);
				}
			});
	        mPager.setOnTouchListener(new View.OnTouchListener() {

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	           
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                return false;
	            }
	        });
	        String url="http://image.mp3.zdn.vn/tv_program_225_225/4/e/4e2b0ef28ab651d398a1883c71dbfaf4_1381907391.jpg";
            for(int i=0;i<id.length;i++){
            	AQuery aQuery = new AQuery(getActivity());
            	aQuery.id(root.findViewById(id[i]).findViewById(R.id.imageView1)).progress(root.findViewById(id[i]).findViewById(R.id.progressBar1)).image(url, true, false);
            }
	       
	       startTimer();
		return root;
	}
	
	 private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	        public ScreenSlidePagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int position) {
	            return new SliderItemFragment();
	        }

	        @Override
	        public int getCount() {
	            return 5;
	        }
	    }
	  protected  void startTimer() {
		   
	        timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	               
	                mHandler.obtainMessage(1).sendToTarget();

	            }
	        }, 1000*15, 1000*15);
	    };
	   

}
