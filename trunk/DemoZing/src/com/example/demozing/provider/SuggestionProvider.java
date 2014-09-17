package com.example.demozing.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

public class SuggestionProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "com.example.demozing.provider.SuggestionProvider";
	public static final String URL = "content://" + PROVIDER_NAME + "/products";
	public static final Uri CONTENT_URI = Uri.parse(URL);
	static final int PRODUCTS = 1;
	static final int PRODUCT_ID = 2;


	public static final String ULR_VIDEO = "http://giaoducviet.vn/demozingtv/video.php";
	// projection map for a query
	private static HashMap<String, String> BirthMap;

	// maps content URI "patterns" to the integer values that were set above
	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);
		uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCT_ID);
	}

	// database declarations
	private SQLiteDatabase database;

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
	
			return false;
	
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		MatrixCursor mCursor = null;
	    //  switch (uriMatcher.match(uri)){
	      // maps all database column names
	      		mCursor = new MatrixCursor(new String[] { "_id","title","urlImage" });
	      		String stringJson= getSuget("");
	      		try {
	      		    JSONArray json = new JSONArray(stringJson);

	      		    for(int i = 0; i < json.length(); i++){
	      		        JSONObject jsonObj = json.getJSONObject(i);
	      		        try {
	      		        mCursor.addRow(new String[]{String.valueOf(i+1),jsonObj.getString("title"),jsonObj.getString("urlImage")});
	      		        } catch (JSONException e) {
	      		            e.printStackTrace();
	      		        }
	      		    }
	      		} catch (JSONException e) {
	      		    e.printStackTrace();
	      		}
	   //   }
	      return mCursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		// Get all friend-birthday records
		case PRODUCTS:
			return "vnd.android.cursor.dir/vnd.example.suggestion";
			// Get a particular friend
		case PRODUCT_ID:
			return "vnd.android.cursor.item/vnd.example.suggestion";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		throw new SQLException("Fail to add a new record into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;

		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;

		return count;
	}

private String downloadUrl(String strUrl) {
	 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
     StrictMode.setThreadPolicy(policy);
	String data = "";
	Log.d("kienbk1910", "1111");
	try {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet(strUrl);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		httpEntity.writeTo(out);
		out.close();
		data = out.toString();
	} catch (UnsupportedEncodingException e) {
		Log.d("kienbk1910", "222");
		e.printStackTrace();
		
	} catch (ClientProtocolException e) {
		Log.d("kienbk1910", "33");
		e.printStackTrace();
		
	} catch (IOException e) {
		Log.d("kienbk1910", "44");
		e.printStackTrace();
	}
	Log.d("kienbk1910", data);
	return data;
}
private String getSuget(String params){
    // For storing data from web service
    String data = "";
    String url = ULR_VIDEO;
  
        // Fetching the data from web service in background
        data = downloadUrl(url);
   
    Log.d("kienbk1910",data);
    return data;
}
}
