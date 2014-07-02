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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.config.Common;
import com.example.config.Config;
import com.example.demozing.model.Program;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author kienbk1910
 *
 */
public class ProgramList  extends Fragment{
	ListCustomAdaper adaper;
	ListView listView;
	boolean isload;
    private View mFooterView;
    RelativeLayout main_content;
    View loading_screen;
    
	List<Program> programs =new ArrayList<Program>();
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
		adaper = new ListCustomAdaper(getActivity(), 0, programs);
		listView.addFooterView(mFooterView);
		listView.setAdapter(adaper);
	//	listView.setEmptyView(progressBar);	
	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent intent= new Intent(getActivity(), ProgramFragmentActivity.class);
				intent.putExtra(Common.PROGRAM, programs.get(position));
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
				      new AddData().execute(Config.URL_PROGRAM);
				   }
				
			}
		});
		new AddData().execute(Config.URL_PROGRAM);
		return root;
	}
	
	class ListCustomAdaper extends ArrayAdapter<Program>{
         private Context context;
         private List<Program> videos;
         AQuery aQuery;
		/**
		 * @param context
		 * @param resource
		 * @param objects
		 */
		public ListCustomAdaper(Context context, int resource,
				List<Program> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context=context;
			this.videos=objects;
			aQuery = new AQuery(context);
		}
		public void addMoreItem(List<Program> videos){
			this.videos.addAll(videos);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
		  
		   
			   ViewHolder holder;
		    if(convertView == null )
		    {
		        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();
		         holder = new ViewHolder();
		        convertView = inflater.inflate(R.layout.item_listview, parent, false);
		        holder.image = (ImageView) convertView.findViewById(R.id.image);
		        holder.progressBar=(ProgressBar)convertView.findViewById(R.id.progressBar1);
		        holder.title=(TextView)convertView.findViewById(R.id.title);
		        holder.category=(TextView)convertView.findViewById(R.id.category);
		        holder.numberLike=(TextView)convertView.findViewById(R.id.number_view);
		        convertView.setTag(holder);
		    }
		    else
		    {
		        holder = (ViewHolder) convertView.getTag();
		    }
		    Program info = videos.get(position);
		    AQuery query= aQuery.recycle(convertView);
		    query.id(holder.image).progress(holder.progressBar).image(info.getUrl(), true, true, 0, 0, null, 0,1.0f);
		    holder.title.setText(info.getTitle());
		    holder.category.setText(info.getCategory());
		    holder.numberLike.setText(String.valueOf(info.getLike()+" like"));
		    return convertView;
		}

		public  class ViewHolder
		{
		    ImageView image;
		    ProgressBar progressBar;
		    TextView title;
		    TextView category;
		    TextView numberLike;
		}
		    
	}
	class AddData extends AsyncTask<String, String, List<Program>>{
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
		protected  List<Program> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Program> programs = null;
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
				Type listType = new TypeToken<List<Program>>() {
				}.getType();
				programs = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return programs;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute( List<Program> result) {
			// TODO Auto-generated method stub
			isload=false;
			if(result!=null && result.size()>0)
		
				programs.addAll(result);
			
			mFooterView.setVisibility(View.GONE);
			loading_screen.setVisibility(View.GONE);
			main_content.setVisibility(View.VISIBLE);
		//	adaper.addMoreItem(videos);
			adaper.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}

}
