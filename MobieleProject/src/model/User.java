package model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import service.Facade;


public class User {
	
	private int userID;
	private String naam;
	private Map<Datum,Integer> mapAanwezigheden;
	private Map<String,Integer> mapBedragen;
	private Timer timer;
	private int timeCounter;	
	private TimerTask myTimerTask;
	private Calendar cal;
	private Date date;
	private static boolean isAangemeld;
	private int laatstToegevoegdeMaand;
	private boolean maandVeranderd;
	private Facade facade;
	
	
	
	public User(int userID, String naam){
		
		setUserID(userID);
		setNaam(naam);
		mapAanwezigheden = new HashMap<Datum,Integer>();
		mapBedragen = new HashMap<String,Integer>();
		cal = Calendar.getInstance();
		date = new Date();	
		isAangemeld = false;
		laatstToegevoegdeMaand = cal.get(Calendar.MONTH) + 1;
		maandVeranderd = false;
		
		
	}
	
	public void setUserID(int id){
		if(id >= 0){
			this.userID = id;
		}
	}
	
	public int getID(){
		
		return this.userID;		
	}
	
	public void setNaam(String naam){
		if(naam != "" || naam != null){
			this.naam = naam;
		}
	}
	
	public String getNaam(){
		
		return this.naam;
	}	
	
	public Map<Datum,Integer> getAanwezigheden(){
		
		return this.mapAanwezigheden;
	}
	
	public Map<String,Integer> getBedragen(){
		
		return this.mapBedragen;
	}
	
	public void setAanwezigheid(Datum datum, int uur){
		if(datum != null && uur > 0){
			getAanwezigheden().put(datum, uur);
		}
	}
	
	public void setBedrag(int maand, int bedrag){
		if(maand > 0){
			getBedragen().put(getMaandNL(maand), bedrag);
		}
	}
	
	//////Registratie - Start - Stop//////
	
	public void start(){
		timeCounter = 0;
		timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run(){
				countTime();				
			}
		},1000,1000);
		
	}
	public void countTime(){
		this.timeCounter++;
	}

	
	public void stop(){
		System.out.println("Passert in User- Stop()");
		int invoerTime = timeCounter;
		createAanwezigheid(invoerTime);
		if(maandIsVeranderd()){
			createBedrag();
		}
		
		
	}
	
	//createAanwezigheid:
	//uur is minimaal 1. 3601, want als het exact 3600 is, zou het op 2 uur belanden, wat niet de bedoeling is
	//maand en dag concatineren in een string
	//alles meegeven aan map
	//MONTH +1, omdat calendar van 0 - 11 gaat.
	//if-lus om te controleren of de maand gelijk is aan de vorige ingevoegde waarde
	//indien dit niet het geval is, moet er een nieuw bedrag worden opgemaakt
	
	private void createAanwezigheid(int timeInSeconds){
		
		int aantalUur = (timeInSeconds/3601) + 1;		
		cal.setTime(date);
		int maand = cal.get(Calendar.MONTH) + 1;
		
		if(maand != laatstToegevoegdeMaand){
			maandVeranderd = true;
			laatstToegevoegdeMaand = maand;
		}
		
		int dag = cal.get(Calendar.DAY_OF_MONTH);
		Datum datum = new Datum(dag,maand);
		setAanwezigheid(datum,aantalUur);
		
		
		facade = Facade.getInstance();
		facade.addNieuweAanwezigheid(datum,aantalUur,getID());
		
		
		System.out.println("Aanwezigheid aangemaakt! Op " + datum.getDag() + "/" + datum.getMaand() + " voor " + aantalUur +" uur");
	}
	
	//controleren of maand is veranderd sinds vorige intrede
	
	private boolean maandIsVeranderd(){
		boolean x = false;
		if(maandVeranderd){
			maandVeranderd = false;
			x = true;
		}		
		return x;
	}
	
	//createBedrag
	//default prijs die ik heb gegeven = 5 euro per uur aanwezigheid
	//itereer door de getAanwezigheden-map om bij elke maand die matcht, het juiste aantal uren aan aantalUUR toevoegen
	//MONTH niet +1, omdat calendar van 0 - 11 gaat en we spreken over de vorige maand.
	
	public void createBedrag(){
		cal.setTime(date);		
		int maand = cal.get(Calendar.MONTH);		
		int aantalUUR = 0;
		
		for (Map.Entry<Datum, Integer> entry : getAanwezigheden().entrySet()){
		    
		    if((entry.getKey().getMaand()) == maand){
		    	aantalUUR += entry.getValue();
		    }
		}
		
		int bedrag = aantalUUR*5;
		
		
		facade.addNieuweBedrag(maand,bedrag,getID());
		
		
		setBedrag(maand,bedrag);		
		
	}
	
	public boolean isAangemeld(){
		return isAangemeld;
	}
	
	public void meldAan(){
		isAangemeld = true;
	}
	
	public void meldAf(){
		isAangemeld = false;
	}
	
	//getMaandNL
	//nummers van maanden omzetten in NL maanden
	
	public String getMaandNL(int nr){
		
		String monthString = "";
		int jaar = cal.get(Calendar.YEAR);
		
		switch (nr) {
        case 1:  monthString = "Januari";
                 break;
        case 2:  monthString = "Februari";
                 break;
        case 3:  monthString = "Maart";
                 break;
        case 4:  monthString = "April";
                 break;
        case 5:  monthString = "Mei";
                 break;
        case 6:  monthString = "Juni";
                 break;
        case 7:  monthString = "Juli";
                 break;
        case 8:  monthString = "Augustus";
                 break;
        case 9:  monthString = "September";
                 break;
        case 10: monthString = "Oktober";
                 break;
        case 11: monthString = "November";
                 break;
        case 12: monthString = "December";
                 break;
        default: monthString = "Invalid month";
                 break;
    }
		
		return monthString + " " + jaar;
	}
	
	

	
	
	
	
	


}
