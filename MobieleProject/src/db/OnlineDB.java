package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import model.User;


public class OnlineDB implements DB{
	
	
	private final String userName = "sql287008";	
	private final String password = "nE7!qN4%";	
	private final String serverName = "sql2.freesqldatabase.com";	
	private final int portNumber = 3306;	
	private final String dbName = "sql287008";
	
	private final String tableUsers = "Gebruikers";
	private final String tableAmounts = "Bedragen";
	private final String tablePresences = "Aanwezigheden";
	private InputStream is;
	
	
	
	public OnlineDB(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		is = null;
		
	}	

	@Override
	public void add(User user) {
//		String userid = Integer.toString(user.getID());
//		
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//		nameValuePairs.add(new BasicNameValuePair("USER_ID",userid));
//		nameValuePairs.add(new BasicNameValuePair("USER_Name",user.getNaam()));
//		
//		
//		try{
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost("sql2.freesqldatabase.com/insert.php");
//			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			HttpResponse response = httpClient.execute(httpPost);
//			HttpEntity entity = response.getEntity();
//			is=entity.getContent();
//			
//			
//		}catch(ClientProtocolException e){
//			Log.e("ClientProtocolException", "Log Tag");
//			e.printStackTrace();
//		}catch(IOException e){
//			Log.e("Log Tag", "IOException");
//			e.printStackTrace();
//		}		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User get(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}


}
