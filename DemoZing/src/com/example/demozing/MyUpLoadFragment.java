package com.example.demozing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.androidquery.AQuery;
import com.example.config.Common;
import com.example.config.Config;
import com.example.demozing.VideoList.AddData;
import com.example.demozing.VideoList.ListCustomAdaper;
import com.example.demozing.VideoList.ListCustomAdaper.ViewHolder;
import com.example.demozing.model.VideoMyUpLoad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MyUpLoadFragment extends Fragment{
	ListCustomAdaper adaper;
	ListView listView;
	boolean isload;
    private View mFooterView;
    RelativeLayout main_content;
    View loading_screen;

	List<VideoMyUpLoad> videos =new ArrayList<VideoMyUpLoad>();
	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFooterView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_view, null);
		View root =inflater.inflate(R.layout.list_item_category, null);
		main_content =(RelativeLayout)root.findViewById(R.id.main_content);
		loading_screen=(View)root.findViewById(R.id.loading_screen);
		main_content.setVisibility(View.INVISIBLE);
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
				intent.putExtra(Common.URL_VIDEO, videos.get(position).getUrl());
				startActivity(intent);
				 getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		/*listView.setOnScrollListener(new OnScrollListener() {
			
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
				       new AddData().execute(Config.ULR_VIDEO);
				   }
				
			}
		});*/
		new AddData().execute(Config.URL_VIDEO_MY_UPLOAD);
		return root;
	}
	
	class ListCustomAdaper extends ArrayAdapter<VideoMyUpLoad>{
         private Context context;
         private List<VideoMyUpLoad> videos;
         AQuery aQuery;
		/**
		 * @param context
		 * @param resource
		 * @param videos2
		 */
		public ListCustomAdaper(Context context, int resource,
				List<VideoMyUpLoad> videos2) {
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
		        holder.title=(TextView)convertView.findViewById(R.id.titlevideo);
		        holder.duration=(TextView)convertView.findViewById(R.id.duration);
		        holder.progressBar=(ProgressBar)convertView.findViewById(R.id.progressBar1);
		     
		        convertView.setTag(holder);
		    }
		    else
		    {
		        holder = (ViewHolder) convertView.getTag();
		    }
		    VideoMyUpLoad video = videos.get(position);
		    AQuery query= aQuery.recycle(convertView);
		    query.id(holder.image).progress(holder.progressBar).image(video.getUrlImage(), true, true, 0, 0, null, 0,1.0f);
		    holder.title.setText(video.getTitle());
		    holder.duration.setText(video.getDuration());
		    return convertView;
		}

		public  class ViewHolder
		{
		    ImageView image;
		    ProgressBar progressBar;
		    TextView title;
		    TextView duration;
		}
		
		    
	}
	class AddData extends AsyncTask<String, String, List<VideoMyUpLoad>>{
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
		protected List<VideoMyUpLoad> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<VideoMyUpLoad> session = null;
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				httpEntity.writeTo(out);
				out.close();
				String responseString = out.toString();
				Gson gson = new Gson();
				Type listType = new TypeToken<List<VideoMyUpLoad>>() {
				}.getType();
				session = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return session;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<VideoMyUpLoad> result) {
			// TODO Auto-generated method stub
			isload=false;
			if(result!=null && result.size()>0)
			      videos.addAll(result);
			mFooterView.setVisibility(View.GONE);
			loading_screen.setVisibility(View.GONE);
			main_content.setVisibility(View.VISIBLE);
			adaper.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}



}
