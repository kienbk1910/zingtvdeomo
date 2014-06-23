/**kienbk1910
 *TODO
 * Jun 18, 2014
 */
package com.example.demozing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.androidquery.AQuery;
import com.example.demozing.ListItemCategory.ListCustomAdaper.ViewHolder;
import com.example.demozing.model.Comment;
import com.example.demozing.model.Video;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		View root = inflater.inflate(R.layout.comment_frament, null);
		listView= (ListView) root.findViewById(R.id.list);
		comments = new ArrayList<Comment>();
	
		adapter= new ListCommentAdaper(getActivity(), 0, comments);
		listView.setAdapter(adapter);
		new LoadCommentTask().execute();
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

			return convertView;
		}

		public class ViewHolder {
			ImageView avatar;
			TextView nickName;
			TextView date;
			TextView comment;

		}

	}
	class LoadCommentTask extends AsyncTask<String, String, String>{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			for(int i=0; i<5;i++){
				if(i%2==0)
				comments.add(new Comment("kienbk1910", "https://scontent-b-pao.xx.fbcdn.net/hphotos-xfp1/t1.0-9/10154346_746451368745600_7869285067688138781_n.jpg", new Date().getTime(), "hello"));
				else 
					comments.add(new Comment("kienbk1910","https://scontent-a-pao.xx.fbcdn.net/hphotos-prn2/t1.0-9/1476593_632292163494855_199830306_n.jpg", new Date().getTime(), "hello"));

			}
			return null;
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}
}
