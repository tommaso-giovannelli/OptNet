package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class CAP implements Steppable {
	
	public String name;
	
	public Double2D posizione; 

	public double weeklyDemandValue; //domanda settimanale generata dal CAP 

	public double actualDemand; //domanda attuale (comprende anche la domanda non soddisfatta nelle settimane precedenti)
	
	public double totalDemand; //domanda totale che il CAP ha generato dall'inizio della simulazione 
	
	public double totalDemandSatisfied; //domanda totale del CAP che è stata soddisfatta dall'inizio della simulazione
	
	public double weekDemandSatisfied; //domanda soddisfatta nella settimana considerata
	
	public double distanceFromDc; //distanza in Km dal centro di distribuzione (DFT o DFL) al CAP
	
	public double numberOfTrip; //numero di viaggi compiuti dal mezzo di trasporto tra il CAP e il DFT/DFL dall'inizio della simulazione
	
	public double transpCost; //costo totale di trasporto per raggiungere il CAP dal DFL
	
	public WeightedGraph<Object, DefaultWeightedEdge> grafo;
			
	//private double kmTravelled; //numero di km percorsi per raggiungere i CAP dai DFL
	
	//MANCA COSTRUTTORE
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 
		
		//processo OnRunInitialized
		distanceFromDc //= leggi il peso dell'arco che unisce il CAP al Dc;
	}
	
}

