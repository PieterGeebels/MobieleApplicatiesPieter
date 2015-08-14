package model;

public class Datum {
	
	private int maand;
	private int dag;
	
	public Datum(int dag,int maand){
		setDag(dag);
		setMaand(maand);
	}
	
	private void setDag(int dag){
		if(dag > 0){
			this.dag = dag;
		}
	}
	
	public int getDag(){
		return this.dag;
	}
	
	private void setMaand(int maand){
		if(maand > 0){
			this.maand=maand;
		}
	}
	public int getMaand(){
		return this.maand;
	}

}
