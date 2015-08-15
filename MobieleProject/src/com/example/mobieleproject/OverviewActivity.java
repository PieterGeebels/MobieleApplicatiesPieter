package com.example.mobieleproject;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import model.UploadFile;
import model.User;
import service.Facade;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class OverviewActivity extends ActionBarActivity implements OnItemSelectedListener, View.OnClickListener{
	
	private Spinner spinner;
	private Button factuur;
	private Button login;
    private static String[]paths = {"kindEEN", "kindTWEE", "kindDRIE","kindVIER","kindVIJF"};
    private int chosenSessionID;
    private Facade facade;
    private static final String APP_KEY = "vg1tvissb7yrj10";
	private static final String APP_SECRET = "ar1znbrik4puiwb";
	private DropboxAPI<AndroidAuthSession> mDBApi;
	private final static String DROPBOX_DIR = "/files/";
	private final static String DROPBOX_NAME = "dropbox_prefs";
	private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
	private boolean isLoggedIn = false;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
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
	@SuppressWarnings("deprecation")
	public void initialiseNames(){
		int i = 0;
		for(User u : facade.getUsers()){
			paths[i] = u.getNaam();
			i++;
		}
		factuur = (Button) findViewById(R.id.ButtonFactuur);	
		login = (Button) findViewById(R.id.login);
		factuur.setOnClickListener(this);
		login.setOnClickListener(this);
		
		setLoggedIn(false);
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session;
		SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
		String key = prefs.getString(APP_KEY, null);
		String secret = prefs.getString(APP_SECRET, null);
		if (key != null && secret != null) {
			AccessTokenPair token = new AccessTokenPair(key, secret);
			session = new AndroidAuthSession(appKeys, ACCESS_TYPE, token);
		} else {
			session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		}

		mDBApi = new DropboxAPI(session);
		// setLoggedIn(isLoggedIn);
		
		setLoggedIn(mDBApi.getSession().isLinked());
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
	
	@SuppressWarnings("deprecation")
	public void onClick(View v) {

		if (v.equals(login)) {
			if (isLoggedIn) {
				mDBApi.getSession().unlink();
				setLoggedIn(false);
			} else {
				((AndroidAuthSession) mDBApi.getSession()).startAuthentication(OverviewActivity.this);
			}

		}
		if (v.equals(factuur)) {
			UploadFile fac = new UploadFile(this, mDBApi, DROPBOX_DIR,"factuur",chosenSessionID);
			fac.execute();
			
		}
		

	}
	
	private void setLoggedIn(boolean loggedIn) {
		isLoggedIn = loggedIn;
		factuur.setEnabled(isLoggedIn);		
		login.setText(isLoggedIn ? "Logout" : "Log in ");
		

	}
	
	@Override
	public void onResume() {
		super.onResume();

		AndroidAuthSession session = mDBApi.getSession();
		if (session.authenticationSuccessful()) {
			try {
				// Required to complete auth, sets the access token on the
				// session
				session.finishAuthentication();
				TokenPair tokens = session.getAccessTokenPair();
				SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
				Editor editor = prefs.edit();
				editor.putString(APP_KEY, tokens.key);
				editor.putString(APP_SECRET, tokens.secret);
				editor.commit();
				setLoggedIn(true);

			} catch (IllegalStateException e) {
				Toast.makeText(this, "Error during dropbox auth", Toast.LENGTH_SHORT).show();
			}
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
