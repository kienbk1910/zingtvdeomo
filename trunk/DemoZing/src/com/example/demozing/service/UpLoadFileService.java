package com.example.demozing.service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import android.widget.Toast;

import com.example.config.CommonConstants;
import com.example.config.Config;

public class UpLoadFileService extends IntentService {
	private String pathFile;
	private String imagePath;
	private String title;
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
		Log.d("kienbk1910","imagefile:"+ imagePath);
		createNotificatons();
		uploadFile();
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

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(pathFile);
		String fileName = sourceFile.getPath();
		File sourceImage = new File(imagePath);
		String imageName = sourceImage.getPath();
		if (!sourceFile.isFile() || !sourceImage.isFile()) {

			erroUpload();
		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				FileInputStream imageInputStream = new FileInputStream(
						sourceImage);
				URL url = new URL(Config.URL_UPLOAD_VIDEO);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setChunkedStreamingMode(maxBufferSize);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());
				// post field
				dos.writeBytes(twoHyphens + boundary + lineEnd);

				dos.writeBytes("Content-Disposition: form-data; name=\""
						+ "title" + "\"");
				dos.writeBytes(lineEnd);
				dos.writeBytes("Content-Type: text/plain; charset=" + "utf-8");
				dos.writeBytes(lineEnd);
				dos.writeBytes(lineEnd);
				dos.writeBytes(title);
				dos.writeBytes(lineEnd);
				dos.flush();
				// write file
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();
				int totalSize = bytesAvailable+imageInputStream.available();
				int uploadedSize = 0;
				Log.d("kienbk1910", "size" + bytesAvailable);
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bytesRead);
					uploadedSize += bytesRead;
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					if (uploadedSize * 100 / totalSize - percent > 5) {
						percent = uploadedSize * 100 / totalSize;
						updateProgress(uploadedSize, totalSize);
					}
				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
				// write file
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""
						+ imageName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = imageInputStream.available();
			
				Log.d("kienbk1910", "size" + bytesAvailable);
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = imageInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bytesRead);
					uploadedSize += bytesRead;
					bytesAvailable = imageInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					if (uploadedSize * 100 / totalSize - percent > 5) {
						percent = uploadedSize * 100 / totalSize;
						updateProgress(uploadedSize, totalSize);
					}
				}
				dos.flush();
				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				int serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {
					
					
					finshUpload();

				}

				// close the streams //
				fileInputStream.close();
				imageInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
				erroUpload();
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
				erroUpload();
			}

		} // End else block
	}

}
