package optNet.model;

import sim.engine.Steppable;
import sim.util.Double2D;

public class CAP {
	
	private String name;
	
	private Double2D posizione; 
	
	private final int NUM_CAP = 4413; //numero dei Plant

	private double weeklyDemandValue; //domanda settimanale generata dal CAP 

	private double actualDemand; //domanda attuale, cioè la domanda nella settimana corrente
	
	private double totalDemand; //domanda totale che il CAP ha generato dall'inizio della simulazione 
	
	private double totalDemandSatisfied; //domanda totale del CAP che è stata soddisfatta dall'inizio della simulazione
	
	private double distanceFromDc; //distanza in Km dal centro di distribuzione (DFT o DFL) al CAP
	
	private double numberOfTrip; //numero di viaggi compiuti dal mezzo di trasporto tra il CAP e il DFT/DFL
	
	private double weekDemandSatisfied; //domanda settimanale soddisfatta
	
	private double kmTravelled; //numero di km percorsi per raggiungere i CAP dai DFL
	
	private double transpCost; //costo totale di trasporto per raggiungere i CAP dai DFL
	
	private final double TRANSP_CAPACITY = 0; //capacità del mezzo di trasporto
	
	private final double KM_COST = 0; //costo al km
}
