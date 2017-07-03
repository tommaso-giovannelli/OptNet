package optNet.model;

import sim.util.Double2D;

public class DFT {
	
	private String name;
	
	private Double2D posizione; 

	private int numDFT = 12; //in realtà i DFT sono variabili ma me ne occupo dopo
	
	private double dFTWeeklyDemand; //domanda generata dal CAP che il DFT deve soddisfare

	private double volPlantDFT; //quantità totale che il DFT ha ricevuto dal Plant dall'inizio della simulazione
	
	private double plantOrder; //quantità da ordinare ai Plant
	
	private double orderFromDFL; //ordini ricevuti dai DFL
	
	private double inventoryWeight; //scorte
	
	private double kmPlantDFT; //km che separano un Plant da un DFT
	
	private double plantDFTCost; //costo totale per raggiungere i DFT dai Plant 
	
	private double plantDFTCapacityTransp; //capacità del mezzo di trasporto che consente di raggiungere i DFT dai Plant
	
	private double numberOfTrip; //numero di viaggi compiuti tra i Plant e i DFT per soddisfare gli ordini dei DFT
	
	private double kmTravelled; //numero di km percorsi per raggiungere i DFT dai Plant  
}
