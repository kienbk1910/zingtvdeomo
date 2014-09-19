package com.example.demozing.service;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.URLUtil;
import com.example.config.CommonConstants;



public class DownLoadFileService extends IntentService{
    private String link;
    private File file;
    private int percent =0;
    private NotificationManager mNotificationManager;
    private  NotificationCompat.Builder builder;
	public DownLoadFileService() {
		super("com.example.demozing.service");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("kienbk1910","start service");
		link =intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
		file =getFileName(link);
		 createNotificatons();
		 downloadFile();
	}
	public File getFileName(String url){
		 File SDCardRoot =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	        //create a new file, specifying the path, and the filename
	        //which we want to save the file as.
		 String fileName = URLUtil.guessFileName(url, null, null);
		  File file= new File(SDCardRoot,fileName);
		 int i =1;
		  while(file.exists()){
			  file= new File(SDCardRoot,String.valueOf(i)+"_"+fileName);
			  i++;
			  Log.d("kienbk1910","start service"+file.getName());
		  }
			
		return file;
	}
	private void createNotificatons(){
			percent=0;
		 builder =new NotificationCompat.Builder(this)
	                .setSmallIcon(android.R.drawable.stat_sys_download)
	                .setContentTitle(file.getName())
	                .setContentText(String.format("%d%%", percent))
	                .setProgress(100, 0, false)
	               .setOngoing(true);
		// .setDefaults(Notification.DEFAULT_ALL);
		 mNotificationManager = (NotificationManager)
	                getSystemService(NOTIFICATION_SERVICE);
	        // Including the notification ID allows you to update the notification later on.
	        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
	}
	private void updateProgress(int downloadedSize, int totalSize){
    	builder.setProgress(totalSize, downloadedSize, false)
    	  .setContentText(String.format("%d%%", percent));
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }
    private void finshDownload(){
        builder 
                .setContentText(file.getName())
                .setProgress(0, 0, false)
                .setContentText("downloaded")
               .setOngoing(false)
                .setAutoCancel(true)
        	.setDefaults(Notification.DEFAULT_ALL) ;
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }
    private void erroDownload(){
    	 builder 
         .setContentText(file.getName())
         .setProgress(100, percent, false)
         .setContentText("download faild!")
        .setOngoing(false)
         .setAutoCancel(true)
 	.setDefaults(Notification.DEFAULT_ALL) ;
 mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }
	private void downloadFile(){
		try {
	        //set the download URL, a url that points to a file on the internet
	        //this is the file to be downloaded
	        URL url = new URL(link);

	        //create the new connection
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	        //set up some things on the connection
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setDoOutput(true);

	        //and connect!
	        urlConnection.connect();
	        FileOutputStream fileOutput = new FileOutputStream(file);

	        //this will be used in reading the data from the internet
	        InputStream inputStream = urlConnection.getInputStream();

	        //this is the total size of the file
	        int totalSize = urlConnection.getContentLength();
	       
	        //variable to store total downloaded bytes
	        int downloadedSize = 0;

	        //create a buffer...
	        byte[] buffer = new byte[1024];
	        int bufferLength = 0; //used to store a temporary size of the buffer
	        //now, read through the input buffer and write the contents to the file
	        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	                //add the data in the buffer to the file in the file output stream (the file on the sd card
	                fileOutput.write(buffer, 0, bufferLength);
	                //add up the size so we know how much is downloaded
	                downloadedSize += bufferLength;
	                //this is where you would do something to report the prgress, like this maybe
	                if(downloadedSize*100 /totalSize - percent >5){
	                	percent=downloadedSize*100 /totalSize;
	                	updateProgress(downloadedSize, totalSize);
	                }

	        }
	        //close the output stream when done
	        fileOutput.close();
	        finshDownload();
	        return;

	//catch some possible errors...
	} catch (MalformedURLException e) {
		 Log.d("kienbk1910", "erro");
	        e.printStackTrace();
	        Log.d("kienbk1910", "erro");
	} catch (IOException e) {
		 Log.d("kienbk1910", "erro1");
	        e.printStackTrace();
	}
		erroDownload();
		
	}
	
}
