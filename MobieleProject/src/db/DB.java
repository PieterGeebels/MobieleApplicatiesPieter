package db;

import java.util.List;

import model.User;

public interface DB {
	
	public void add(User user);
	public void update(User user);
	public User get(int userID);
	public void delete(int userID);
	public List<User> getUsers();

}
