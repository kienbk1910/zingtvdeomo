/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing;

import java.util.List;

import com.example.demozing.model.Category;
import com.example.demozing.model.TypeCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author kienbk1910
 * 
 */
public class SilidingMenuAdapter extends ArrayAdapter<Category> {
	private List<Category> categories;
	private Context context;

	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public SilidingMenuAdapter(Context context, int resource,
			List<Category> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.categories = objects;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Category category = categories.get(position);
		TextView title;
		switch (category.getType()) {
		case MAIN:
			convertView = inflater.inflate(R.layout.item_main_cate, parent,
					false);
			title = (TextView) convertView.findViewById(R.id.title);
			title.setText(category.getTitle());
			break;

		case GROUP:
			convertView = inflater.inflate(R.layout.item_group, parent,
					false);
			title = (TextView) convertView.findViewById(R.id.title);
			title.setText(category.getTitle());
			break;
		case CATEGORY:
			convertView = inflater.inflate(R.layout.item_category, parent,
					false);
			
			ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
			title = (TextView) convertView.findViewById(R.id.title);

			icon.setImageResource(category.getIcon());
			title.setText(category.getTitle());
			break;
		default:
			break;
		}

		return convertView;
	}

}
