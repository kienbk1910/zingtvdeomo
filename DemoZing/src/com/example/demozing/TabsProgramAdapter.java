/**kienbk1910
 *TODO
 * Jun 15, 2014
 */
package com.example.demozing;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
 
public class TabsProgramAdapter extends FragmentStatePagerAdapter {
 
    public TabsProgramAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
            // Top Rated fragment activity
    	if(index==0)
            return new ProgramContent();
    	else
            return new CommentFragment();
     
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}