/**kienbk1910
 *TODO
 * Jun 16, 2014
 */
package com.example.demozing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import com.example.config.Config;
import com.example.demozing.custom.VideoImageView;
import com.example.demozing.model.Group;
import com.example.demozing.model.Program;
import com.example.demozing.model.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author kienbk1910
 *
 */
public class ProgramContent  extends Fragment{
	ListCustomAdaper adaper;
	ListView listView;
	boolean isload;
    private View mFooterView;
	List<Group> groups =new ArrayList<Group>();
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
		adaper = new ListCustomAdaper(getActivity(), 0, groups);
		listView.addFooterView(mFooterView);
		listView.setAdapter(adaper);
	//	listView.setEmptyView(progressBar);	
	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(getActivity(), GroupActivity.class);
				startActivity(intent);
				 getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
				       new AddData().execute(Config.URL_GROUP);
				   }
				
			}
		});
		new AddData().execute(Config.URL_GROUP);
		return root;
	}
	
	class ListCustomAdaper extends ArrayAdapter<Group>{
         private Context context;
         private	List<Group> groups;
         AQuery aQuery;
		/**
		 * @param context
		 * @param resource
		 * @param videos2
		 */
		public ListCustomAdaper(Context context, int resource,
				List<Group> videos2) {
			super(context, resource, videos2);
			// TODO Auto-generated constructor stub
			this.context=context;
			this.groups=videos2;
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
		        convertView = inflater.inflate(R.layout.content_item, parent, false);
		        holder.image1=(VideoImageView) convertView.findViewById(R.id.image1);
		        holder.image2=(VideoImageView) convertView.findViewById(R.id.image2);
		        holder.image3=(VideoImageView) convertView.findViewById(R.id.image3);
		        holder.image4=(VideoImageView) convertView.findViewById(R.id.image4);
		        holder.title=(TextView)convertView.findViewById(R.id.name);
		        holder.number=(TextView)convertView.findViewById(R.id.number);
		        convertView.setTag(holder);
		    }
		    else
		    {
		        holder = (ViewHolder) convertView.getTag();
		    }
		    Group group = groups.get(position);
		    AQuery query= aQuery.recycle(convertView);
		    query.id(holder.image1).image(group.getUrlImage1(), true, true, 0, 0, null, 0,1.0f);
		    query.id(holder.image2).image(group.getUrlImage2(), true, true, 0, 0, null, 0,1.0f);
		    query.id(holder.image3).image(group.getUrlImage3(), true, true, 0, 0, null, 0,1.0f);
		    query.id(holder.image4).image(group.getUrlImage4(), true, true, 0, 0, null, 0,1.0f);
		    holder.title.setText(group.getTitle());
		    holder.number.setText(String.valueOf(group.getNumber()));
		    return convertView;
		}

		public  class ViewHolder
		{
			VideoImageView image1;
			VideoImageView image2;
			VideoImageView image3;
			VideoImageView image4;
		    TextView title;
		    TextView  number;
	
		}
		    
	}
	class AddData extends AsyncTask<String, String, List<Group>>{
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
		protected List<Group> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Group> groups = null;
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
				Type listType = new TypeToken<List<Group>>() {
				}.getType();
				groups = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return groups;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<Group> result) {
			// TODO Auto-generated method stub
			isload=false;
			if(result!=null && result.size()>0)
				groups.addAll(result);
			mFooterView.setVisibility(View.GONE);
		//	adaper.addMoreItem(videos);
			adaper.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}

}
