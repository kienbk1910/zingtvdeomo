/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kienbk1910
 *
 */
public class CommentFragment extends Fragment{
  /* (non-Javadoc)
 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
 */
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View root =inflater.inflate(R.layout.relationship_frament, null);
	return root;
}
}
