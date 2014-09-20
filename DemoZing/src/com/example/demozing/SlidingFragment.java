/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import java.util.ArrayList;
import java.util.List;

import com.example.demozing.model.Category;
import com.example.demozing.model.TypeCategory;


import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

/**
 * @author kienbk1910
 *
 */
public class SlidingFragment extends Fragment {
	ListView listView;
	SilidingMenuAdapter adapter;
	List<Category>categories;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root =inflater.inflate(R.layout.sliding_menu, null);
		listView =(ListView)root.findViewById(R.id.list_menu);
		categories = new ArrayList<Category>();
		String[]title = getActivity().getResources().getStringArray(R.array.categories);
		TypedArray imgs = getResources().obtainTypedArray(R.array.categories_icon);
		categories.add(new Category(getActivity().getSharedPreferences(Constants.KEY_STORE, Context.MODE_PRIVATE).getString(Constants.USER_NAME, ""), TypeCategory.MAIN));
		for(int i=0;i<title.length;i++){
			Category category= new Category(imgs.getResourceId(i, 0),title[i] , TypeCategory.CATEGORY,true);
			categories.add(category);
			if(i==1){
				categories.add(new Category("Thể Loại", TypeCategory.GROUP));
			}
			if(i==9){
				categories.add(new Category("Công Cụ", TypeCategory.GROUP));
			}
			
		}
		imgs.recycle();
		adapter = new SilidingMenuAdapter(getActivity(), 0, categories);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				listView.setItemChecked(arg2, true);
				if(categories.get(arg2).getType()==TypeCategory.CATEGORY && arg2!=2 &&arg2!=1 && arg2!=12){
					switchFragment(new CategoryFragment());
				//	closeMenu();
					
				}
				if(arg2==1){
					switchFragment(new MainFragment());
				//	closeMenu();
				}
				if(arg2==12){
					setting();
				//	closeMenu();
				}
				if(arg2==14){
					logout();
				}
				if(arg2==2){
					switchFragment(new MyUpLoadFragment());
				}
				
			}
			
		});
		
	
		return root;
	}
	
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragment);
		}
	}
	private void logout(){
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.logout();
		}
	}
	private void setting(){
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.setting();
		}
	}

}
