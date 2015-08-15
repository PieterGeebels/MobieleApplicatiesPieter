package db;

public class DBFactory {
	
	private boolean status;
	
	public DBFactory(boolean status){
		this.status = status;
	}
	
	public DB getDatabank(){
		DB databank = null;
		
			if(status){
				databank = new OnlineDB();
			}
			else{
				databank = new MapDB();
			}		
		
		return databank;
	}

}
