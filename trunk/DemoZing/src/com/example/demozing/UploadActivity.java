package com.example.demozing;

import com.example.config.Common;
import com.example.demozing.MainActivity.searchTask;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;

public class UploadActivity extends FragmentActivity{
	String pathFile;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.upload_activity);
		pathFile = getIntent().getStringExtra(Common.URI_FILE_UPLOAD);
		Bitmap thumb = ThumbnailUtils.createVideoThumbnail(pathFile,
                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
		ImageView imageView =(ImageView)findViewById(R.id.imagevideo);
		imageView.setImageBitmap(thumb);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.menu_upload, menu);
	
		return true;
	}


}
