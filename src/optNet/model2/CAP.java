package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class CAP implements Steppable {
	
	public String name;
	
	public Double2D posizione; 
	
	public Object impiantoAssociato; //il centro di distribuzione che serve il CAP (può essere un DFT o DFL)

	public double weeklyDemandValue; //domanda settimanale generata dal CAP 

	public double actualDemand; //domanda attuale (comprende anche la domanda non soddisfatta nelle settimane precedenti)
	
	public double totalDemand; //domanda totale che il CAP ha generato dall'inizio della simulazione 
	
	public double totalDemandSatisfied; //domanda totale del CAP che è stata soddisfatta dall'inizio della simulazione
	
	public double weekDemandSatisfied; //domanda soddisfatta nella settimana considerata
	
	public double distanceFromDc; //distanza in Km dal centro di distribuzione (DFT o DFL) al CAP
	
	public double numberOfTrip; //numero di viaggi compiuti dal mezzo di trasporto tra il CAP e il DFT/DFL dall'inizio della simulazione
	
	public double transpCost; //costo totale di trasporto per raggiungere il CAP dal DFL
			
	public double kmTravelled; //numero di km percorsi per raggiungere il CAP dal DFL
	
	//costruttore
	public CAP(String name, Double2D posizione) {
		super();
		this.name = name;
		this.posizione = posizione;
		this.weeklyDemandValue = 0;
		this.actualDemand = 0;
		this.totalDemand = 0;
		this.totalDemandSatisfied = 0;
		this.weekDemandSatisfied = 0;
		this.numberOfTrip = 0;
		this.transpCost = 0;
	}
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 
		
		//processo OnRunInitialized
		distanceFromDc //= leggi il peso dell'arco che unisce il CAP al Dc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((posizione == null) ? 0 : posizione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CAP other = (CAP) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		return true;
	}
	
	
}

