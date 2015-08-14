package db;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class MapDB implements DB{
	
	private List<User> lijstGebruikers = new ArrayList<User>();
	
	public MapDB(){
		
	}

	@Override
	public void add(User user) {
		
		if(user != null){
			lijstGebruikers.add(user);
		}
		
	}

	@Override
	public void update(User user) {
		
		for(User u : lijstGebruikers){
			if(u.getID() == user.getID()){
				u = user;
			}
		}
		
	}

	@Override
	public User get(int userID) {
		
		User user = null;
		
		for(User u : lijstGebruikers){
			if(u.getID() == userID){
				user = u;
			}
		}
		return user;
		
	}

	@Override
	public void delete(int userID) {
		
		for(User u : lijstGebruikers){
			if (u.getID() == userID){
				lijstGebruikers.remove(u);
			}
		}
		
	}

	@Override
	public List<User> getUsers() {
		
		return lijstGebruikers;
	}

}
