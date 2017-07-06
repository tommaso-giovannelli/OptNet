package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class DFT implements Steppable {
	
	public String name;
	
	public Double2D posizione; 
	
	public double dFTWeeklyDemand; //raggruppa tutta la domanda attuale pervenuta dai CAP da servire
	
	public double plantOrder; //rappresenta il volume totale dell’ordine da inviare ai Plant
	
	public double orderFromDFL; //ordini ricevuti dai DFL
	
	public double inventoryWeight; //scorte
	
	public WeightedGraph<Object, DefaultWeightedEdge> grafo;
	
	//private double[] volPlantDFT; //vettore che tiene conto della quantità totale che il DFT ha ricevuto dai Plant dall'inizio della simulazione
	
	//private double[] kmPlantDFT; //vettore che memorizza la distanza da ogni Plant
	
	//private double plantDFTCost; //costo di trasporto per raggiungere il DFT dai Plant 
	
	//private final double PLANT_DFT_CAPACITY_TRANSP; //capacità del mezzo di trasporto che consente di raggiungere il DFT dai Plant
	
	//private double numberOfTrip; //numero di viaggi compiuti tra i Plant e il DFT per soddisfare gli ordini del DFT
	
	//private double kmTravelled; //chilometri percorsi per consegnare prodotti dai Plant al CDc  
	
	//MANCA COSTRUTTORE
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 
		
	}
}
