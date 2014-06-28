/**kienbk1910
 *TODO
 * Jun 27, 2014
 */
package com.example.demozing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

/**
 * @author kienbk1910
 *
 */
public class GroupActivity  extends FragmentActivity {
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.responsive_content_frame);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, new VideoList(),"kien")
		.commit();

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
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

	}
}
