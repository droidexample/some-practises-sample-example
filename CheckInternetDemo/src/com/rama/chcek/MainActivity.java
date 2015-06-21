package com.rama.chcek;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStatus = (Button) findViewById(R.id.btn_check);

		btnStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable()) {
					Toast.makeText(getApplicationContext(),
							"internet  Connected", Toast.LENGTH_LONG).show();
				} else {
					// Internet connection is not present
					Toast.makeText(getApplicationContext(),
							"no internet Connected", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.isAvailable() && networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}
}
