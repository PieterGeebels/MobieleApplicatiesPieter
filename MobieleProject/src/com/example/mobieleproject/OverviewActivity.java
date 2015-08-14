package com.example.mobieleproject;

import model.User;
import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class OverviewActivity extends ActionBarActivity implements OnItemSelectedListener{
	
	private Spinner spinner;
    private static String[]paths = {"kindEEN", "kindTWEE", "kindDRIE","kindVIER","kindVIJF"};
    private int chosenSessionID;
    private Facade facade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		facade = Facade.getInstance();
        initialiseNames();
        
		spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(OverviewActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		switch (position) {
        case 0:
            chosenSessionID = 1;
            break;
        case 1:
        	chosenSessionID = 2;
            break;
        case 2:
        	chosenSessionID = 3;
            break;
        case 3:
        	chosenSessionID = 4;
            break;
        case 4:
        	chosenSessionID = 5;
            break;
            

    }
		
	}
	
	public void initialiseNames(){
		int i = 0;
		for(User u : facade.getUsers()){
			paths[i] = u.getNaam();
			i++;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public void goToBedragen(View view) {
    	Intent intent = new Intent(this, PaymentActivity.class);
    	intent.putExtra("sessionID", chosenSessionID);
    	startActivity(intent);
    }
	public void goToAanwezigheden(View view) {
    	Intent intent = new Intent(this, PresenceActivity.class);
    	intent.putExtra("sessionID", chosenSessionID);
    	startActivity(intent);
    }
}
