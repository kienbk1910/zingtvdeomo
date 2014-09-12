/**
 * @u kienbk1910
 * @t 17 Jun 2014
 */
package com.example.demozing;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.facebook.*;
import com.facebook.Session.StatusCallback;
import com.facebook.model.*;
import com.example.demozing.custom.LinearLayoutThatDetectsSoftKeyboard;
import  com.example.demozing.custom.LinearLayoutThatDetectsSoftKeyboard.Listener;;

/**
 * @author kienbk1910
 *
 */
public class LoginActivity extends FragmentActivity implements Listener{
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	   SharedPreferences sharedpreferences;
	private static final String TAG = "MainFragment";
	Context context;
	ImageView logo;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		/* try {
		        PackageInfo info = getPackageManager().getPackageInfo(
		                "com.example.demozing", 
		                PackageManager.GET_SIGNATURES);
		        for (Signature signature : info.signatures) {
		            MessageDigest md = MessageDigest.getInstance("SHA");
		            md.update(signature.toByteArray());
		            Log.d("kienbk1910", Base64.encodeToString(md.digest(), Base64.DEFAULT));
		            }
		    } catch (NameNotFoundException e) {

		    } catch (NoSuchAlgorithmException e) {

		    }*/
		super.onCreate(arg0);
		// Add code to print out the key hash
		
		setContentView(R.layout.login);
		logo=(ImageView)findViewById(R.id.logo);
		LinearLayoutThatDetectsSoftKeyboard root= (LinearLayoutThatDetectsSoftKeyboard) findViewById(R.id.root);
		root.setListener(this);
		Button facebook = (Button) findViewById(R.id.facebook);
		context=this;
		facebook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginFacebook();
			}
		});
		   sharedpreferences =getSharedPreferences(Constants.KEY_STORE, Context.MODE_PRIVATE);
		   if(sharedpreferences.contains(Constants.IS_LOGIN)){
			   if(sharedpreferences.getBoolean(Constants.IS_LOGIN, false)){
				   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
	                 startActivity(intent);
			   }
		   }
	/*   try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.example.demozing", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("kienbk1910", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }*/
		 // start Facebook Login
	
	    
	}
	public void LoginFacebook(){
	Session s = new Session(this);
		Session.OpenRequest request = new Session.OpenRequest(this);
		request.setCallback(new StatusCallback() {
			
			public void call(Session session, SessionState state, Exception exception) {
				// TODO Auto-generated method stub
				if (session.isOpened()) {

			          // make request to the /me API
			          Request.newMeRequest(session, new Request.GraphUserCallback() {

			            // callback after Graph API response with user object
			            @Override
			            public void onCompleted(GraphUser user, Response response) {
			              if (user != null) {
			            	  Log.d("kienbk1910",  user.toString());
			            	  setUser(user.getName(),user.getId());
			                 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			                 startActivity(intent);
			              }
			            }
			          }).executeAsync();
			        }
			}
		});
		request.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK); // <-- this is the important line
		s.openForRead(request);
		Session.setActiveSession(s);
	}
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
public void setUser(String username,String userID){
	 Editor editor = sharedpreferences.edit();
     editor.putString(Constants.USER_NAME, username);
     editor.putString(Constants.USER_ID, userID);
     editor.putBoolean(Constants.IS_LOGIN, true);
     editor.commit(); 
}
/* (non-Javadoc)
 * @see com.example.demozing.custom.LinearLayoutThatDetectsSoftKeyboard.Listener#onSoftKeyboardShown(boolean)
 */
@Override
public void onSoftKeyboardShown(boolean isShowing) {
	// TODO Auto-generated method stub
	if(isShowing){
		logo.setVisibility(View.GONE);
	}else{
		logo.setVisibility(View.VISIBLE);
	}
	
}
}
