package com.example.demozing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyUpLoadFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.my_upload_fragment, null);
		Button upload =(Button)root.findViewById(R.id.upload);
		upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseFile();
		
			}
		});
		return root;
	}
	public void chooseFile(){
		 Intent intent = new Intent();
		 intent.setType("video/*");
		 intent.setAction(Intent.ACTION_PICK);
		 getActivity().startActivityForResult(intent, MainActivity.PICK_FILE_UPLOAD);
		
	}

}
