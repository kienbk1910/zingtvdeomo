/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.androidquery.AQuery;
import com.example.config.Config;
import com.example.demozing.ProgramList.ListCustomAdaper.ViewHolder;
import com.example.demozing.VideoList.AddData;
import com.example.demozing.dialog.CommentDialog;
import com.example.demozing.dialog.RateDialog;
import com.example.demozing.model.Comment;
import com.example.demozing.model.Program;
import com.example.demozing.model.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author kienbk1910
 * 
 */
public class CommentFragment extends Fragment {
	private ListView listView;
	ListCommentAdaper adapter;
	List<Comment> comments;
	boolean isload;
	  private View mFooterView;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFooterView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_view, null);
		View root = inflater.inflate(R.layout.comment_frament, null);
		View headerList = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.comment_header, null);
		Button comment = (Button) headerList.findViewById(R.id.comment);
		listView= (ListView) root.findViewById(R.id.list);
		listView.addHeaderView(headerList,null,false);
		comments = new ArrayList<Comment>();
		adapter= new ListCommentAdaper(getActivity(), 0, comments);
		listView.addFooterView(mFooterView);
		listView.setAdapter(adapter);
		isload=false;
		comment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showCommentDialog();
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
						new LoadCommentTask().execute(Config.ULR_COMMENT);
				   }
				
			}
		});
		new LoadCommentTask().execute(Config.ULR_COMMENT);
		return root;
	}

	class ListCommentAdaper extends ArrayAdapter<Comment> {
		private Context context;
		private List<Comment> comments;
		AQuery aQuery;

		/**
		 * @param context
		 * @param resource
		 * @param objects
		 */
		public ListCommentAdaper(Context context, int resource,
				List<Comment> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.comments = objects;
			aQuery = new AQuery(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = ((FragmentActivity) context)
						.getLayoutInflater();
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.comment_item, parent,
						false);
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.avartar);
				holder.nickName = (TextView) convertView
						.findViewById(R.id.nickname);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.comment = (TextView) convertView
						.findViewById(R.id.comment);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Comment comment = comments.get(position);
			AQuery query = aQuery.recycle(convertView);
			query.id(holder.avatar).image(comment.getAvatar(), true, true, 0,
					0, null, 0, 1.0f);
			holder.nickName.setText(comment.getUserName());
			holder.comment.setText(comment.getComment());
			return convertView;
		}

		public class ViewHolder {
			ImageView avatar;
			TextView nickName;
			TextView date;
			TextView comment;

		}

	}
	class LoadCommentTask extends AsyncTask<String, String, List<Comment>>{
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
		protected List<Comment> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Comment> comments = null;
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
				Type listType = new TypeToken<List<Comment>>() {
				}.getType();
				comments = gson.fromJson(responseString, listType);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return comments;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<Comment> result) {
			// TODO Auto-generated method stub
			isload=false;
			mFooterView.setVisibility(View.GONE);
			if(result!=null && result.size()>0)
				comments.addAll(result);
				
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}
	private void showCommentDialog(){
		CommentDialog dialog = new CommentDialog();
		dialog.show(getFragmentManager(), "ratedialog");
	}
}
