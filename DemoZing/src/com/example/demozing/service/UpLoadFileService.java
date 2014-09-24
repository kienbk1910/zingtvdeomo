package com.example.demozing.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.config.CommonConstants;
import com.example.config.Config;

public class UpLoadFileService extends IntentService {
	private String pathFile;
	private String imagePath;
	private String title;
	private String duration;
	private int percent = 0;
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder builder;

	public UpLoadFileService() {
		super("com.example.demozing.service");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("kienbk1910", "start service");
		pathFile = intent.getStringExtra(CommonConstants.EXTRA_PATH_FILE);
		title = intent.getStringExtra(CommonConstants.EXTRA_TITLE);
		imagePath = intent.getStringExtra(CommonConstants.EXTRA_PATH_THUMS);
		duration = intent.getStringExtra(CommonConstants.EXTRA_DURATION);
		Log.d("kienbk1910","imagefile:"+ imagePath);
		Log.d("kienbk1910","file:"+ pathFile);
		createNotificatons();
		uploadFile();
		//uploadFileMutiPart();
	}

	
	private void createNotificatons() {
		percent = 0;
		builder = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.stat_sys_upload)
				.setContentTitle(title)
				.setContentText(String.format("%d%%", percent))
				.setProgress(100, 0, false).setOngoing(true);
		// .setDefaults(Notification.DEFAULT_ALL);
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Including the notification ID allows you to update the notification
		// later on.
		mNotificationManager.notify(CommonConstants.UPLOAD_NOTIFICATION_ID,
				builder.build());
		
	}

	private void updateProgress(int downloadedSize, int totalSize) {
		builder.setProgress(totalSize, downloadedSize, false).setContentText(
				String.format("%d%%", percent));
		mNotificationManager.notify(CommonConstants.UPLOAD_NOTIFICATION_ID,
				builder.build());
	}

	private void finshUpload() {
		builder.setContentText(title).setProgress(0, 0, false)
				.setContentText("uploaded").setOngoing(false)
				.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
		mNotificationManager.notify(CommonConstants.UPLOAD_NOTIFICATION_ID,
				builder.build());
	}

	private void erroUpload() {
		builder.setContentText(title).setProgress(100, percent, false)
				.setContentText("upload faild!").setOngoing(false)
				.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
		mNotificationManager.notify(CommonConstants.NOTIFICATION_ID,
				builder.build());
	}
	
	private void uploadFile() {
		 String boundary;
		  String LINE_FEED = "\r\n";
		 HttpURLConnection httpConn;
		 String charset="UTF-8";
		OutputStream outputStream;
		 PrintWriter writer;
		boundary = "===" + System.currentTimeMillis() + "===";
		try{
		URL url = new URL(Config.URL_UPLOAD_VIDEO);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setUseCaches(false);
		httpConn.setDoOutput(true);	// indicates POST method
		httpConn.setDoInput(true);
	//	httpConn.setChunkedStreamingMode(1024);
		httpConn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
		httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
		httpConn.setRequestProperty("Test", "Bonjour");
		outputStream = httpConn.getOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
				true);
		// and field text 
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append("Content-Disposition: form-data; name=\"title\"")
				.append(LINE_FEED);
		writer.append("Content-Type: text/plain; charset=" + charset).append(
				LINE_FEED);
		writer.append(LINE_FEED);
		writer.append(title).append(LINE_FEED);
		writer.flush();
		// and field text 
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append("Content-Disposition: form-data; name=\"duration\"")
						.append(LINE_FEED);
		writer.append("Content-Type: text/plain; charset=" + charset).append(
						LINE_FEED);
		writer.append(LINE_FEED);
		writer.append(duration).append(LINE_FEED);
		writer.flush();
		// add file
		File video = new File(pathFile);
		File image = new File(imagePath);
		FileInputStream videoInput = new FileInputStream(video);
		FileInputStream  imageInput = new FileInputStream(image);
		int totalsize = videoInput.available()+imageInput.available();
		int uploaded =0;
		// write video
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append(
				"Content-Disposition: form-data; name=\"video\"; filename=\"" + video.getName() + "\"")
				.append(LINE_FEED);
		writer.append(
				"Content-Type: "
						+ URLConnection.guessContentTypeFromName(video.getName()))
				.append(LINE_FEED);
		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.flush();


		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = videoInput.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
			uploaded+=bytesRead;
			Log.d("kienbk1910", ""+percent);
			if(uploaded*100 /totalsize -percent>5){
				percent=uploaded*100 /totalsize;
				updateProgress(uploaded, totalsize);
			}
		}
		outputStream.flush();
		videoInput.close();
		
		writer.append(LINE_FEED);
		writer.flush();
		// write image
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append(
				"Content-Disposition: form-data; name=\"image\"; filename=\"" + image.getName() + "\"")
				.append(LINE_FEED);
		writer.append(
				"Content-Type: "
						+ URLConnection.guessContentTypeFromName(image.getName()))
				.append(LINE_FEED);
		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.flush();


		
		bytesRead = -1;
		while ((bytesRead = imageInput.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
			uploaded+=bytesRead;
			Log.d("kienbk1910",""+uploaded+"_"+totalsize);
			if( uploaded*100 /totalsize - percent>5){
				percent=uploaded*100 /totalsize;
				updateProgress(uploaded, totalsize);
			}
		}
		outputStream.flush();
		imageInput.close();
		
		writer.append(LINE_FEED);
		writer.flush();
		// finish
		List<String> response = new ArrayList<String>();

		writer.append(LINE_FEED).flush();
		writer.append("--" + boundary + "--").append(LINE_FEED);
		writer.close();

		// checks server's status code first
		int status = httpConn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
			
				Log.d("kienbk1910", line);
			}
			reader.close();
			httpConn.disconnect();
		} else {
			erroUpload();
			throw new IOException("Server returned non-OK status: " + status);
		}
		}catch(Exception e){
			erroUpload();
		}
		finshUpload();
	}
	/*public void uploadFileMutiPart(){
		try {
			MultipartUtility multipart = new MultipartUtility(Config.URL_UPLOAD_VIDEO, "UTF8");
			
			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			
		//	multipart.addFormField("title",title);
	
			
			multipart.addFilePart("video", new File(pathFile));
			multipart.addFilePart("image",  new File(imagePath));

			List<String> response = multipart.finish();
			
			System.out.println("SERVER REPLIED:");
			
			for (String line : response) {
			//	Log.d("kienbk1910",line);
			}
		} catch (IOException ex) {
			Log.d("kienbk1910","+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.err.println(ex);
		}
		finshUpload();
	}*/
	

}
