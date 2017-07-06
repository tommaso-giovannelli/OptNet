package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.util.Double2D;

public class DFL {

	public String name;
	
	public Double2D posizione; 
	
	public double dFLWeeklyDemand; //domanda generata dai CAP di competenza che il DFL deve soddisfare

	public double dFTOrder; //ordine di prodotto inviato al DFT
		
	public double weekVolSatisfied; //volume di prodotto ricevuto dal DFT nella settimana considerata
	
	public double inventoryWeight; //scorte
	
	public WeightedGraph<Object, DefaultWeightedEdge> grafo;
	
	//private double[] kmVolDFTDFL; //vettore a 2 componenti che tiene conto della distanza dal DFT da cui viene servito e della quantità di prodotto che è stata consegnata al DFL
	
	//private double numberOfTrip; //numero di viaggi compiuti tra il DFT e il DFL per soddisfare gli ordini del DFL
	
	//private double kmTravelled; //numero di km percorsi per raggiungere il DFL dal DFT
	
	//private double transpCost; //costo totale di trasporto per raggiungere il DFL dal DFT
	
	//private final double TRANSP_CAPACITY; //capacità del mezzo di trasporto per servire il DFL
	
	//private final double KM_COST; //costo al km
	
	//MANCA COSTRUTTORE
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 
		
		
	}
	
}
