/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import com.androidquery.AQuery;

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
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root =inflater.inflate(R.layout.slider_item, null);
		ImageView imageView=(ImageView) root.findViewById(R.id.image);
		String url="http://image.mp3.zdn.vn/content/2/b/2b8c1cacbac41e06c4c6650f5b4806e0_1403238900.jpg";
		AQuery aq = new AQuery(getActivity());   
		aq.id(root.findViewById(R.id.image)).progress(root.findViewById(R.id.progressBar)).image(url, true, false);
		return root;
	}

}
