/**
 * @u kienbk1910
 * @t 25 Jun 2014
 */
package com.example.demozing.dialog;

import java.util.List;

import com.example.demozing.R;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author kienbk1910
 *
 */
public class ShareChooserDialog extends DialogFragment{
	private GridView gridView;
	private List<ResolveInfo> pkgAppsList;
/* (non-Javadoc)
 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
 */
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view = inflater.inflate(R.layout.share_chooser_dialgo, container);
	gridView=(GridView)view.findViewById(R.id.gridview);
	getDialog().setTitle("Chia sẽ");
	 int divierId = getDialog().getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
     View divider = getDialog().findViewById(divierId);
     divider.setBackgroundColor(getActivity().getResources().getColor(R.color.text_green));
	final Intent mainIntent = new Intent(android.content.Intent.ACTION_SEND);
	mainIntent.setType("text/plain");
	mainIntent.putExtra(android.content.Intent.EXTRA_TEXT,"http://tv.zing.vn/video/One-And-A-Half-Summer-Mua-He-Nam-Ay-Tap-1/IWZA7A0O.html");
	pkgAppsList = getActivity().getPackageManager().queryIntentActivities( mainIntent, 0);

	gridView.setAdapter(new ImageAdapter(getActivity()));
	gridView.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            final ActivityInfo activity = pkgAppsList.get(position).activityInfo;
            final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            mainIntent.setComponent(name);
           getActivity().startActivity(mainIntent);
           dismiss();
        }
    });
	return view;
}
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return pkgAppsList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(pkgAppsList.get(position).activityInfo.loadIcon(getActivity().getPackageManager()));
        return imageView;
    }

    // references to our images
 
}

}
