package com.example.mobieleproject;

import java.io.IOException;

import org.json.JSONException;

import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends ActionBarActivity{
	
	Facade facade;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		facade = Facade.getInstance();		
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
	
    private boolean haveNetworkConnection() {
        boolean haveWifi = false;
        boolean haveMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveMobile = true;
        }
        return haveWifi || haveMobile;
    }
	   
	   
	public void goToBedragen(View view) {
		
	
		if(haveNetworkConnection()){
			try {
				facade.setAanwezigheden();
				facade.setBedragen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	Intent intent = new Intent(this, PaymentActivity.class);
    	Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	intent.putExtra("sessionID", value);
    	startActivity(intent);
		}else{
    		Toast.makeText(getApplicationContext(), "No Internet Connection!",
					Toast.LENGTH_LONG).show();
    	}
    }
	

	   
	public void goToAanwezigheden(View view) {
		if(haveNetworkConnection()){
			try {
				facade.setAanwezigheden();
				facade.setBedragen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	Intent intent = new Intent(this, PresenceActivity.class);
    	Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	intent.putExtra("sessionID", value);
    	startActivity(intent);
		}else{
    		Toast.makeText(getApplicationContext(), "No Internet Connection!",
					Toast.LENGTH_LONG).show();
    	}
    }
	

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);    	
		startActivity(intent);
		finish();
	}
}
