package db;

public class DBFactory {
	
	private boolean status;
	
	public DBFactory(boolean status){
		this.status = status;
	}
	
	public DB getDatabank(){
		DB databank = null;
		

				databank = new MapDB();
				
		
		return databank;
	}

}
