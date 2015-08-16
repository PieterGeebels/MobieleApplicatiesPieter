package service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;










import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import model.Datum;
import model.User;
import model.UserRepository;
import db.DB;
import db.DBFactory;
import db.DbConnector;
import db.MapDB;


public class Facade {
	
	private DB database;
	
	private UserRepository repository;
	private static Facade instance = null;
	private boolean isOnline = false;
	private Map<Map<Datum,Integer>, Integer> nieuweAanwezigheden;
	private Map<Map<Integer,Integer>,Integer> nieuweBedragen;

	
	
	public Facade(){
		
		DBFactory factory = new DBFactory();
		database = factory.getDatabank();
		repository = new UserRepository();
		nieuweAanwezigheden = new HashMap<Map<Datum,Integer>, Integer>();
		nieuweBedragen = new HashMap<Map<Integer,Integer>,Integer>();
		try {
			setAanwezigheden();
			setBedragen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//dit was van voor de data van relationele db
		
//		for(User u : repository.getUsers()){
//			addUser(u);			
//			
//		}
		
	}
	

	
	public void addNieuweAanwezigheid(Datum datum, int aantalUur, int userID){
		Map<Datum,Integer> test = new HashMap<Datum,Integer>();
		test.put(datum, aantalUur);
		nieuweAanwezigheden.put(test,userID);
		
	}
	public void addNieuweBedrag(int maand, int bedrag, int userID){
		Map<Integer,Integer> test = new HashMap<Integer,Integer>();
		test.put(maand, bedrag);
		nieuweBedragen.put(test, userID);
		
	}
	
	public Map<Map<Datum,Integer>,Integer> getNieuweAanwezigheden(){
		
		return this.nieuweAanwezigheden;
	}
	
	public Map<Map<Integer,Integer>,Integer> getNieuweBedragen(){
		
		return this.nieuweBedragen;
	}
	
	public void clearNieuweAanwezigheden(){
		this.nieuweAanwezigheden.clear();
	}
	public void clearNieuweBedragen(){
		this.nieuweBedragen.clear();
	}

	

	
    public static Facade getInstance() {
    	 
        if (instance == null) {
                instance = new Facade();
        }

        return instance;
    }
    

	
	public void addUser(User user){		
		
		database.add(user);
	}
	
	public User getUser(int userID){
		
		return database.get(userID);
	}
	
	public void updateUser(User user){
		
		database.update(user);
	}
	
	public void deleteUser(int userID){
		
		database.delete(userID);
	}
	
	public List<User> getUsers(){
		
		return database.getUsers();
	}
	
	public void startRegistratieTimer(int userID){
		
		getUser(userID).start();
		System.out.println("Start Registratie");
		updateUser(getUser(userID));
		
	}
	
	public void stopRegistratieTimer(int userID){
		
		getUser(userID).stop();
		System.out.println("Stop registratie");
		updateUser(getUser(userID));
		
	}	
	
	public Map<Datum,Integer> getAanwezigheden(int userID){
		
		return getUser(userID).getAanwezigheden();
	}
	
	public Map<String,Integer> getBedragen(int userID){
		
		return getUser(userID).getBedragen();
	}
	
	
	public void setAanwezigheden() throws IOException, JSONException {
        
		for (Map.Entry<Map<Datum,Integer>, Integer> entry : getNieuweAanwezigheden().entrySet()){
			for(Map.Entry<Datum, Integer> innerEntry : entry.getKey().entrySet()){

			final List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			
			nameValuePairList.add(new BasicNameValuePair("USER_ID", Integer.toString(entry.getValue())));
			nameValuePairList.add(new BasicNameValuePair("DAY", Integer.toString(innerEntry.getKey().getDag())));
			nameValuePairList.add(new BasicNameValuePair("MONTH", Integer.toString(innerEntry.getKey().getMaand())));
			nameValuePairList.add(new BasicNameValuePair("HOURS_PRESENT", Integer.toString(innerEntry.getValue())));
			
			
			new AsyncTask<DbConnector, Long, Boolean>() {
			@Override
			protected Boolean doInBackground(DbConnector... dbconnectors) {
			        
			                return dbconnectors[0].SetData(nameValuePairList, "aanwezigheden");
			                }
			                }.execute(new DbConnector());
			                        }}
		
			clearNieuweAanwezigheden();
		}
	
	public void setBedragen() throws IOException, JSONException {
        
		for (Map.Entry<Map<Integer,Integer>, Integer> entry : getNieuweBedragen().entrySet()){
			for(Map.Entry<Integer, Integer> innerEntry : entry.getKey().entrySet()){

			final List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			
			nameValuePairList.add(new BasicNameValuePair("USER_ID", Integer.toString(entry.getValue())));
			nameValuePairList.add(new BasicNameValuePair("MAAND", Integer.toString(innerEntry.getKey())));
			nameValuePairList.add(new BasicNameValuePair("BEDRAG", Integer.toString(innerEntry.getValue())));			
			
			
			new AsyncTask<DbConnector, Long, Boolean>() {
			@Override
			protected Boolean doInBackground(DbConnector... dbconnectors) {
			        
			                return dbconnectors[0].SetData(nameValuePairList, "bedragen");
			                }
			                }.execute(new DbConnector());
			                        }
		}
		
			clearNieuweBedragen();
		}
	
}
