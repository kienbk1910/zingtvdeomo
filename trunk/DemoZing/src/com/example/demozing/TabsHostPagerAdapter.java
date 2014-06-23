/**kienbk1910
 *TODO
 * Jun 15, 2014
 */
package com.example.demozing;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
 
public class TabsHostPagerAdapter extends FragmentStatePagerAdapter {
 
    public TabsHostPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
            // Top Rated fragment activity
    	switch (index) {
		case 0:
			return new InfomatinFragment();
		case 1:
			return new ListItemCategory();
	
		case 2:
			return new CommentFragment();

		}
		return new ListItemCategory();

    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}