package service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.net.NetworkInfo;
import model.Datum;
import model.User;
import model.UserRepository;
import db.DB;
import db.DBFactory;
import db.MapDB;


public class Facade {
	
	private DB database;
	
	private UserRepository repository;
	private static Facade instance = null;
	private static boolean isOnline = false;
	
	
	public Facade(){
		
		DBFactory factory = new DBFactory(isOnline);
		database = factory.getDatabank();
		repository = new UserRepository();
		
		
//		for(User u : repository.getUsers()){
//			addUser(u);			
//			
//		}
		
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
	

}
