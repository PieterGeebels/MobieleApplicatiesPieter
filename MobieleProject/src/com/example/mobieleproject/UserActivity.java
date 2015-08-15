package com.example.mobieleproject;

import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserActivity extends ActionBarActivity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		
		setContentView(R.layout.activity_user);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void goToBedragen(View view) {
    	Intent intent = new Intent(this, PaymentActivity.class);
    	Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	intent.putExtra("sessionID", value);
    	startActivity(intent);
    }
	public void goToAanwezigheden(View view) {
    	Intent intent = new Intent(this, PresenceActivity.class);
    	Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	intent.putExtra("sessionID", value);
    	startActivity(intent);
    }
	

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);    	
		startActivity(intent);
		finish();
	}
}
