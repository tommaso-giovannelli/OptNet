package optNet.model;

import sim.util.Double2D;

public class Plant {
	
	private String name;
	
	private Double2D posizione; 
	
	private final int NUM_PLANT = 6; //numero dei Plant

	private int[] orderVector = new int[numPlant]; //array di dimensione 12; ogni elemento rappresenta l'ordine del corrispondente DFT
	
}
