package com.example.mobieleproject;



import java.util.ArrayList;
import java.util.List;

import model.Datum;
import model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DbConnector;
import scanner.IntentIntegrator;
import scanner.IntentResult;
import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends ActionBarActivity {
	
	private static boolean adminActive = false;
	private Facade facade;
	private static boolean isReedsOpgehaaldVanDB = false;
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facade = Facade.getInstance();         
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        
        if(!isReedsOpgehaaldVanDB){
        new GetAllUsersTask().execute(new DbConnector());
        new GetAllAanwezighedenTask().execute(new DbConnector());
        new GetAllBedragenTask().execute(new DbConnector());
        isReedsOpgehaaldVanDB = true;
        }

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
    		  } else if(nummer > facade.getUsers().size()-1){
    			  //backtohome
    		  }    		  
    		  else if(this.adminActive){
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void setUsers(JSONArray jsonArray)    {
    	
        
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                User u  =new User(500, "Alibaba");
                u.setUserID(Integer.parseInt(json.getString("USER_ID")));
                u.setNaam(json.getString("USER_Name"));
                
                facade.addUser(u);
                
               
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    
    public void setAanwezigheden(JSONArray jsonArray)    {
    	
        
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
               
                int userID = (Integer.parseInt(json.getString("USER_ID")));
                int day = (Integer.parseInt(json.getString("DAY")));
                int month = (Integer.parseInt(json.getString("MONTH")));
                int hours = (Integer.parseInt(json.getString("HOURS_PRESENT")));               
                
                facade.getUser(userID).setAanwezigheid(new Datum(day,month), hours);
                
                
               
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    
    public void setBedragen(JSONArray jsonArray)    {
    	
        
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
               
                int userID = (Integer.parseInt(json.getString("USER_ID")));
                int maand = (Integer.parseInt(json.getString("MAAND")));
                int bedrag = (Integer.parseInt(json.getString("BEDRAG")));
                facade.getUser(userID).setBedrag(maand, bedrag);
                
                
               
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private class GetAllUsersTask extends AsyncTask<DbConnector, Long, JSONArray> {
    	 
        @Override
        protected void onPostExecute(JSONArray jsonArray) {

                // setTextToTextView(jsonArray);
                setUsers(jsonArray);

        }

        @Override
        protected JSONArray doInBackground(DbConnector... params) {
                // TODO Auto-generated method stub
                return params[0].GetData("users");
                
                
        }
    }
    private class GetAllAanwezighedenTask extends AsyncTask<DbConnector, Long, JSONArray> {
   	 
        @Override
        protected void onPostExecute(JSONArray jsonArray) {

                // setTextToTextView(jsonArray);
                setAanwezigheden(jsonArray);

        }

        @Override
        protected JSONArray doInBackground(DbConnector... params) {
                // TODO Auto-generated method stub
                return params[0].GetData("aanwezigheden");
                
                
        }
    }
    
    private class GetAllBedragenTask extends AsyncTask<DbConnector, Long, JSONArray> {
   	 
        @Override
        protected void onPostExecute(JSONArray jsonArray) {

                // setTextToTextView(jsonArray);
                setBedragen(jsonArray);

        }

        @Override
        protected JSONArray doInBackground(DbConnector... params) {
                // TODO Auto-generated method stub
                return params[0].GetData("bedragen");
                
                
        }
    }
    

}
