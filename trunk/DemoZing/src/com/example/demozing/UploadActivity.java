package com.example.demozing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.example.config.Common;
import com.example.config.CommonConstants;
import com.example.demozing.MainActivity.searchTask;
import com.example.demozing.service.UpLoadFileService;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.Toast;

public class UploadActivity extends FragmentActivity{
	String pathFile;
	Bitmap thumb;
	EditText title;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.upload_activity);
		pathFile = getIntent().getStringExtra(Common.URI_FILE_UPLOAD);
		 thumb = ThumbnailUtils.createVideoThumbnail(pathFile,
                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
		ImageView imageView =(ImageView)findViewById(R.id.imagevideo);
		title = (EditText) findViewById(R.id.titlevideo);
		imageView.setImageBitmap(thumb);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.menu_upload, menu);
	
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			break;
		case R.id.upload:
			if (title.getText().length()==0) {
			  Toast.makeText(this, "You forget title", Toast.LENGTH_SHORT).show();
			  break;
			}
			Intent intent = new Intent(this, UpLoadFileService.class);
			intent.putExtra(CommonConstants.EXTRA_PATH_FILE, pathFile);
			intent.putExtra(CommonConstants.EXTRA_TITLE, title.getText().toString());
			intent.putExtra(CommonConstants.EXTRA_PATH_THUMS, getPicPatFormBitMap(thumb));
			startService(intent);
			break;

		}

		return super.onOptionsItemSelected(item);
	}
    private String getPicPatFormBitMap( Bitmap bm){
    	OutputStream fOut = null;
        String outputFileUri;
         try {
        File root = new File(Environment.getExternalStorageDirectory()
          + File.separator + "ZingTVFake" + File.separator);
        root.mkdirs();
       File sdImageMainDirectory = new File(root, "myPicName.jpg");
        outputFileUri = sdImageMainDirectory.getPath();
        fOut = new FileOutputStream(sdImageMainDirectory);
       } catch (Exception e) {
    	   Log.d("kienbk190", "erro");
    	   return "";
       }

       try {
        bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
       } catch (Exception e) {
    	   Log.d("kienbk190", "outputFileUri");
    	   return "";
       }
       Log.d("kienbk190", "erro");
       return outputFileUri;
    }

}
