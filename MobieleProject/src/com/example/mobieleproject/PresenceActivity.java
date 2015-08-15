package com.example.mobieleproject;

import java.util.Map;

import model.Datum;
import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PresenceActivity extends ActionBarActivity {
	
	private Facade facade;
	private int userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_presence);
		
		facade = Facade.getInstance();
		Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");
    	userID = value;
		insertData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.presence, menu);
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
	
	public void insertData(){
		
		TextView naamVoornaam = (TextView)findViewById(R.id.textNaamVoornaam);		
		naamVoornaam.setText(facade.getUser(userID).getNaam());
		
		TableLayout table = (TableLayout)findViewById(R.id.tableLayout2);		
		Map<Datum,Integer> mapBedragen = facade.getAanwezigheden(userID);
		
		for (Map.Entry<Datum, Integer> entry : mapBedragen.entrySet()){
			
		
			TableRow row = new TableRow(this);		
			TextView datum = new TextView(this);
			TextView uren = new TextView(this);
			
			String datumWaarde = Integer.toString(entry.getKey().getDag()) + "/" + Integer.toString(entry.getKey().getMaand());
			String uurWaarde = Integer.toString(entry.getValue());
			
	        datum.setText(datumWaarde);	       
	        datum.setGravity(Gravity.CENTER);	        
	        
	       
	        uren.setText(uurWaarde + "u");
	        uren.setGravity(Gravity.CENTER);        
	        
	        
	        
	        row.addView(datum);
	        row.addView(uren);
	        
	        table.addView(row);
		}
		
	}
	

}
