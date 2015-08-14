package model;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
	
	private List<User> testUsers;
	
	public UserRepository(){
		
		testUsers = new ArrayList<User>();
		
		User u1 = new User(1,"Pieter Geebels");
		User u2 = new User(2,"Diewke Deneyer");
		User u3 = new User(3,"Hanne Vananne");
		User u4 = new User(4,"Clara Dehondt");
		User u5 = new User(5,"Koen Lamaar");
		
		
		u1.setBedrag(5, 100);
		u1.setBedrag(6, 325);
		u1.setBedrag(7, 250);
		
		u1.setAanwezigheid(new Datum(10,8), 6);
		u1.setAanwezigheid(new Datum(11,8), 5);
		u1.setAanwezigheid(new Datum(12,8), 1);
		u1.setAanwezigheid(new Datum(13,8), 8);		
		
		u2.setBedrag(5, 420);
		u2.setBedrag(6, 400);
		u2.setBedrag(7, 180);
		
		u2.setAanwezigheid(new Datum(10,8), 8);
		u2.setAanwezigheid(new Datum(11,8), 7);
		u2.setAanwezigheid(new Datum(12,8), 6);
		u2.setAanwezigheid(new Datum(13,8), 5);
		u2.setAanwezigheid(new Datum(14,8), 4);
		
		u3.setBedrag(5, 250);
		u3.setBedrag(6, 150);
		u3.setBedrag(7, 650);
		
		u3.setAanwezigheid(new Datum(10,8), 1);
		u3.setAanwezigheid(new Datum(11,8), 4);
		u3.setAanwezigheid(new Datum(12,8), 3);
		u3.setAanwezigheid(new Datum(13,8), 1);
		u3.setAanwezigheid(new Datum(14,8), 4);
		
		u4.setBedrag(5, 250);
		u4.setBedrag(6, 150);
		u4.setBedrag(7, 650);
		u4.setBedrag(4, 180);
		u4.setBedrag(3, 500);
		
		u4.setAanwezigheid(new Datum(10,8), 2);
		u4.setAanwezigheid(new Datum(11,8), 8);
		u4.setAanwezigheid(new Datum(12,8), 8);
		u4.setAanwezigheid(new Datum(13,8), 8);
		u4.setAanwezigheid(new Datum(14,8), 8);
		
		u5.setBedrag(5, 100);
		u5.setBedrag(6, 185);
		u5.setBedrag(7, 360);
		
		u5.setAanwezigheid(new Datum(10,8), 5);
		u5.setAanwezigheid(new Datum(11,8), 5);
		u5.setAanwezigheid(new Datum(12,8), 3);
		u5.setAanwezigheid(new Datum(13,8), 3);
		u5.setAanwezigheid(new Datum(14,8), 4);
		
		testUsers.add(u1);
		testUsers.add(u2);
		testUsers.add(u3);
		testUsers.add(u4);
		testUsers.add(u5);
	}
	
	public List<User> getUsers(){
		
		return testUsers;
	}

}
