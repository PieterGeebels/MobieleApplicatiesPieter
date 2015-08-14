package com.example.mobieleproject;

import java.util.Map;





import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PaymentActivity extends ActionBarActivity {
	
	private Facade facade;
	private int userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		
		facade = Facade.getInstance();
		Bundle bundle = getIntent().getExtras();
    	int value = bundle.getInt("sessionID");		
        userID = value;
        insertData();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
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
		
		TableLayout table = (TableLayout)findViewById(R.id.tableLayout);		
		Map<String,Integer> mapBedragen = facade.getBedragen(userID);
		
		for (Map.Entry<String, Integer> entry : mapBedragen.entrySet()){
			
		
			TableRow row = new TableRow(this);		
			TextView maand = new TextView(this);
			TextView bedrag = new TextView(this);
			
			String bedragWaarde = Integer.toString(entry.getValue());
			
	        maand.setText(entry.getKey());	       
	        maand.setGravity(Gravity.CENTER);	        
	        
	       
	        bedrag.setText(bedragWaarde + "€");
	        bedrag.setGravity(Gravity.CENTER);
	        
	        
	        
	        
	        
	        row.addView(maand);
	        row.addView(bedrag);
	        
	        table.addView(row);
		}
		
	}
}
