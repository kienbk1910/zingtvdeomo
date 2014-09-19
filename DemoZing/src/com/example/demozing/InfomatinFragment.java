/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing;

import com.example.config.CommonConstants;
import com.example.demozing.dialog.AlertDialog;
import com.example.demozing.dialog.RateDialog;
import com.example.demozing.service.DownLoadFileService;

import android.R.bool;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * @author kienbk1910
 * 
 */
public class InfomatinFragment extends Fragment {
	DrawableAlignedButton rate;
	DrawableAlignedButton like;
	DrawableAlignedButton subcribe;
	ProgressBar progressBar1;
	ProgressBar progressBar2;
	ProgressBar progressBar3;
	boolean islike;
	boolean isSubcribe;
	private ImageButton download;

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
		View root = inflater.inflate(R.layout.infomation_frament, null);
		rate = (DrawableAlignedButton) root.findViewById(R.id.rate);
		like = (DrawableAlignedButton) root.findViewById(R.id.like);
		download =(ImageButton)root.findViewById(R.id.btn_download);
		subcribe = (DrawableAlignedButton) root.findViewById(R.id.subcribe);
		progressBar1 = (ProgressBar) root.findViewById(R.id.progressBar);
		progressBar2 = (ProgressBar) root.findViewById(R.id.progressBar1);
		progressBar3 = (ProgressBar) root.findViewById(R.id.progressBar2);
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
		download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlertDialog();
			}
		});
		return root;
	}
    private void showAlertDialog(){
    	AlertDialog dialog = AlertDialog.newAlertDialog("Xác Nhận", "Bạn muốn download Video này?");
    	dialog.show(getFragmentManager(), "alert dialot");
    	dialog.setAgreeListenerr(new AlertDialog.AgreeListener() {
			
			@Override
			public void OnAgreeListener() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), DownLoadFileService.class);
		    	intent.putExtra(CommonConstants.EXTRA_MESSAGE, com.example.config.Config.URL_VIDEO_DEMO);
		    	getActivity().startService(intent);
			}
		});
    	
    }
	private void showRateDialog() {
		RateDialog dialog = new RateDialog();
		dialog.show(getFragmentManager(), "ratedialog");
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
