package com.example.mobieleproject;

import scanner.IntentIntegrator;
import scanner.IntentResult;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends ActionBarActivity {
	
	private static boolean adminActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {        
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onButtonClick(View view){
    	
    	IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
		integrator.initiateScan();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) {
    		  
    		  int nummer = Integer.parseInt(scanResult.getContents().toString());
    		  if(nummer == -1){
    		  this.adminActive = true;
    		  intent = new Intent(this, AdminActivity.class);    	
    		  startActivity(intent);
    		  } else if(this.adminActive){
    			  intent = new Intent(this, GreetingActivity.class);
    			  intent.putExtra("sessionID", nummer);
        		  startActivity(intent);
    		  } else{
    			  intent = new Intent(this, UserActivity.class);
    			  intent.putExtra("sessionID", nummer);
    			  startActivity(intent);
    		  }
    	  }
    	  
    	}
    
//    public void sendMessage(View view) {
//    	Intent intent = new Intent(this, AdminActivity.class);    	
//    	startActivity(intent);
//    }
//    public void userData(View view) {
//    	Intent intent = new Intent(this, UserActivity.class);
//    	intent.putExtra("sessionID", 4);
//    	startActivity(intent);    	
//    	
//    }
//    public void welcomePage(View view) {
//    	Intent intent = new Intent(this, GreetingActivity.class);    	
//    	startActivity(intent);
//    }
}
