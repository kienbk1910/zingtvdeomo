/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.androidquery.AQuery;
import com.example.config.Config;
import com.example.demozing.custom.ProgramComponent;
import com.example.demozing.custom.VideoComponent;
import com.example.demozing.model.Program;
import com.example.demozing.model.Video;
import com.example.demozing.slider.IndexPointSlider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;

/**
 * @author kienbk1910
 * 
 */
public class MainFragment extends Fragment implements View.OnClickListener {
	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */

	
	private ViewPager mPager;
	private IndexPointSlider pointSlider;
	AQuery aQuery;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;
	Timer timer = new Timer();

	int[] programId = { R.id.program1, R.id.program2, R.id.program3,
			R.id.program4, R.id.program5, R.id.program6, R.id.program7,
			R.id.program8 };
	int[] videoid = { R.id.video1, R.id.video2, R.id.video3, R.id.video4,
			R.id.video5, R.id.video6, R.id.video7, R.id.video8, R.id.video9,
			R.id.video10, R.id.video11, R.id.video12, R.id.video13,
			R.id.video14, R.id.video15, R.id.video16, R.id.video17,
			R.id.video18 };
	ProgramComponent[] programComponents = new ProgramComponent[8];
	VideoComponent[] videoComponents = new VideoComponent[18];
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			mPager.setCurrentItem((mPager.getCurrentItem() + 1) % 5);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.main_screen, null);

		getActivity().getActionBar().removeAllTabs();
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		mPager = (ViewPager) root.findViewById(R.id.pager);
		mPager.setOffscreenPageLimit(5);
		pointSlider = (IndexPointSlider) root.findViewById(R.id.indexSlider);
		for (int i = 0; i < programComponents.length; i++) {
			programComponents[i] = (ProgramComponent) root
					.findViewById(programId[i]);
			programComponents[i].setOnClickListener(this);
		}
		for (int i = 0; i < videoComponents.length; i++) {
			videoComponents[i] = (VideoComponent) root.findViewById(videoid[i]);
			videoComponents[i].setOnClickListener(this);
		}

		pointSlider.setNumberPoint(5);

		// mPager.setScrollDurationFactor(2);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.d("kienbk1910", "selected" + arg0);
				pointSlider.setCurrentPoint(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				pointSlider.changePageOfset(arg0, arg1);
				mPager.getParent().requestDisallowInterceptTouchEvent(true);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				// pointSlider.setCurrentPoint(arg0);
				Log.d("kienbk1910", "selected--" + arg0);
			}
		});
		mPager.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		startTimer();
		new LoadProgram().execute(Config.URL_PROGRAM);
		new LoadBanner().execute(Config.URL_BANNER);
		new LoadVideo().execute(Config.ULR_VIDEO);
		return root;
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		List<Video> videos;

		public ScreenSlidePagerAdapter(FragmentManager fm, List<Video> videos) {
			super(fm);
			this.videos = videos;
		}

		@Override
		public Fragment getItem(int position) {
			return SliderItemFragment.newInstance(videos.get(position));
		}

		@Override
		public int getCount() {
			return videos.size();
		}
	}

	protected void startTimer() {

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {

				mHandler.obtainMessage(1).sendToTarget();

			}
		}, 1000 * 15, 1000 * 15);
	};

	class LoadProgram extends AsyncTask<String, String, List<Program>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<Program> doInBackground(String... params) {
			List<Program> programs = null;
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				httpEntity.writeTo(out);
				out.close();
				String responseString = out.toString();
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Program>>() {
				}.getType();
				programs = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return programs;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<Program> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null)
				for (int i = 0; i < result.size(); i++) {
					Program program = result.get(i);
					AQuery aQuery = new AQuery(getActivity());
					aQuery.id(programComponents[i].getImageView()).image(
							program.getUrl(), true, true, 0, 0, null, 0, 1.0f);
					programComponents[i].setTitle(program.getTitle());
				}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("kienbk1910", "click");
		switch (v.getId()) {
		case R.id.program1:
			Intent intent = new Intent(getActivity(),
					ProgramFragmentActivity.class);
			getActivity().startActivity(intent);
			getActivity().overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			break;

		default:
			break;
		}

	}

	class LoadBanner extends AsyncTask<String, String, List<Video>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<Video> doInBackground(String... params) {
			// TODO Auto-generated method stub
			// make HTTP request
			List<Video> session = null;
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				httpEntity.writeTo(out);
				out.close();
				String responseString = out.toString();
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Video>>() {
				}.getType();
				session = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return session;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<Video> result) {
			// TODO Auto-generated method stub
			if (result != null) {
				mPagerAdapter = new ScreenSlidePagerAdapter(getActivity()
						.getSupportFragmentManager(), result);
				mPager.setAdapter(mPagerAdapter);
			}
			super.onPostExecute(result);
		}

	}

	class LoadVideo extends AsyncTask<String, String, List<Video>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<Video> doInBackground(String... params) {
			// TODO Auto-generated method stub
			// make HTTP request
			List<Video> session = null;
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				httpEntity.writeTo(out);
				out.close();
				String responseString = out.toString();
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Video>>() {
				}.getType();
				session = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return session;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<Video> result) {
			// TODO Auto-generated method stub
			if (result != null) {
				int i = 0;
				for (Video video : result) {
					AQuery aQuery = new AQuery(getActivity());
					aQuery.id(videoComponents[i].getImageView()).progress(videoComponents[i].getProgressBar()).image(
							video.getUrlImage(), true, true, 0, 0, null, 0,
							1.0f);
					i++;
				}
			}
			super.onPostExecute(result);
		}

	}
}
