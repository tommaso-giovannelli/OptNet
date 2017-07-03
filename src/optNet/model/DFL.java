package optNet.model;

import sim.util.Double2D;

public class DFL {

	private String name;
	
	private Double2D posizione; 
	
	private int numDFL = 12; //in realtà i DFT sono variabili ma me ne occupo dopo
	
	private double dFLWeeklyDemand; //domanda generata dal CAP che il DFL deve soddisfare

	private double dFTOrder; //ordini assegnati al DFT dal DFL
	
	private double dFTWeeklyDemand; //domanda generata dal CAP che il DFT deve soddisfare
	
	private double kmVolDFTDFL; //km che separano il DFL dal DFT
	
	private double weekVolSatisfied; //quantità dell'ordine del DFL soddisfatto in una settimana
	
	private double inventoryWeight; //scorte
	
	private double numberOfTrip; //numero di viaggi compiuti tra i DFT e i DFL per soddisfare gli ordini dei DFL
	
	private double kmTravelled; //numero di km percorsi per raggiungere i DFL dai DFT
	
	private double transpCost; //costo totale di trasporto per raggiungere i DFL dai DFT
	
	private final double TRANSP_CAPACITY = 0; //capacità del mezzo di trasporto
	
	private final double KM_COST = 0; //costo al km
	
}
