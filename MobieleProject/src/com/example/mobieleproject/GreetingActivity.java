package com.example.mobieleproject;

import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GreetingActivity extends ActionBarActivity {
	
	private Facade facade;
	private int userID;
	private boolean aangemeld;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_greeting);
		
		facade = Facade.getInstance();
		Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	userID = value;    	
		
		initiatePage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.greeting, menu);
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
    public void returnToQR(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    
    public void initiatePage(){
    	TextView naamVoornaam = (TextView)findViewById(R.id.textVoornaamAchternaam);    	
		naamVoornaam.setText(facade.getUser(userID).getNaam());
		ImageView image = (ImageView)findViewById(R.id.imageView1);
		
		aangemeld = facade.getUser(userID).isAangemeld();
		
		if(!aangemeld){
			
			image.setImageResource(R.drawable.welkomscherm);
			facade.getUser(userID).meldAan();
			facade.startRegistratieTimer(userID);
			
		}else{			
			image.setImageResource(R.drawable.vaarwelfoto);
			facade.getUser(userID).meldAf();
			facade.stopRegistratieTimer(userID);
			
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
