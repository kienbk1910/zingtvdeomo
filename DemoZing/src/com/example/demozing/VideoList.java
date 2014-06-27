/**kienbk1910
 *TODO
 * Jun 16, 2014
 */
package com.example.demozing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.demozing.model.Program;
import com.example.demozing.model.Video;

/**
 * @author kienbk1910
 *
 */
public class VideoList  extends Fragment{
	ListCustomAdaper adaper;
	ListView listView;
	boolean isload;
    private View mFooterView;
	String url ="http://image.mp3.zdn.vn/content/9/4/9489cf5f9bf91d46ae71c9478866a180_1403774561.jpg";
	List<Video> videos =new ArrayList<Video>();
	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFooterView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_view, null);
		View root =inflater.inflate(R.layout.list_item_category, null);
		ProgressBar progressBar =(ProgressBar) root.findViewById(R.id.progressBar1);
		listView =(ListView) root.findViewById(R.id.list);
		isload=false;
		adaper = new ListCustomAdaper(getActivity(), 0, videos);
		listView.addFooterView(mFooterView);
		listView.setAdapter(adaper);
	//	listView.setEmptyView(progressBar);	
	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(getActivity(), PlayVideoActivity.class);
				startActivity(intent);
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				int lastInScreen = firstVisibleItem + visibleItemCount;    
				   if((lastInScreen == totalItemCount &&!isload) ){     
				       new AddData().execute();
				   }
				
			}
		});
		new AddData().execute();
		return root;
	}
	
	class ListCustomAdaper extends ArrayAdapter<Video>{
         private Context context;
         private List<Video> videos;
         AQuery aQuery;
		/**
		 * @param context
		 * @param resource
		 * @param videos2
		 */
		public ListCustomAdaper(Context context, int resource,
				List<Video> videos2) {
			super(context, resource, videos2);
			// TODO Auto-generated constructor stub
			this.context=context;
			this.videos=videos2;
			aQuery = new AQuery(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
		  
		   
			ViewHolder holder;
		    if(convertView == null )
		    {
		        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();
		        holder = new ViewHolder();
		        convertView = inflater.inflate(R.layout.video_item, parent, false);
		        holder.image=(ImageView) convertView.findViewById(R.id.image);
		        holder.title=(TextView)convertView.findViewById(R.id.title);
		        holder.duration=(TextView)convertView.findViewById(R.id.duration);
		        holder.progressBar=(ProgressBar)convertView.findViewById(R.id.progressBar1);
		        holder.titleshow=(TextView)convertView.findViewById(R.id.show_title);
		        holder.viewNumber=(TextView)convertView.findViewById(R.id.view_number);
		        convertView.setTag(holder);
		    }
		    else
		    {
		        holder = (ViewHolder) convertView.getTag();
		    }
		    Video video = videos.get(position);
		    AQuery query= aQuery.recycle(convertView);
		    query.id(holder.image).progress(holder.progressBar).image(video.getUrlImage(), true, true, 0, 0, null, 0,1.0f);

		    return convertView;
		}

		public  class ViewHolder
		{
		    ImageView image;
		    ProgressBar progressBar;
		    TextView title;
		    TextView titleshow;
		    TextView  viewNumber;
		    TextView duration;
		}
		    
	}
	class AddData extends AsyncTask<String, String, String>{
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mFooterView.setVisibility(View.VISIBLE);
			isload=true;

		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			isload=false;
			for(int i =0;i<10;i++){
				
				videos.add(new Video(url));
			
			
			}
			mFooterView.setVisibility(View.GONE);
		//	adaper.addMoreItem(videos);
			adaper.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}

}
