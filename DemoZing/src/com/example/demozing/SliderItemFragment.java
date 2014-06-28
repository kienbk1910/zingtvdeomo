/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import com.androidquery.AQuery;
import com.example.demozing.model.Video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author kienbk1910
 * 
 */

public class SliderItemFragment extends Fragment {
	private static String VIDEO = "video";

	/**
	 * 
	 */
	public static SliderItemFragment newInstance(Video video) {
		SliderItemFragment f = new SliderItemFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putSerializable(VIDEO, video);
		f.setArguments(args);

		return f;
	}

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
		View root = inflater.inflate(R.layout.slider_item, null);
		ImageView imageView = (ImageView) root.findViewById(R.id.image);
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						PlayVideoActivity.class);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});
		Video video = (Video) getArguments().getSerializable(VIDEO);
		AQuery aq = new AQuery(getActivity());
		aq.id(imageView).progress(root.findViewById(R.id.progressBar))
				.image(video.getUrlImage(), true, false);
		return root;
	}

}
