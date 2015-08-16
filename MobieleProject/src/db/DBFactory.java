package db;

public class DBFactory {
	
	private boolean status;
	
	public DBFactory(){
		
	}
	
	public DB getDatabank(){
		DB databank = null;
		

				databank = new MapDB();
				
		
		return databank;
	}

}
