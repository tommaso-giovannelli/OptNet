package optNet.prova;

import java.util.Map;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;
import sim.util.Double2D;

public class CAP implements Steppable {
	
	public String name;
	
	public Double2D posizione; 
	
	public DFT DFTassociato; //il DFT che serve il CAP (è null se il CAP è servito da un DFL)
	
	public DFL DFLassociato; //il DFL che serve il CAP (è null se il CAP è servito da un DFT)

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
	public CAP(String name, Double2D posizione, double CAPWeeklyDemand) {
		super();
		this.name = name;
		this.posizione = posizione;
		this.weeklyDemandValue = CAPWeeklyDemand;
		this.actualDemand = 0;
		this.totalDemand = 0;
		this.totalDemandSatisfied = 0;
		this.weekDemandSatisfied = 0;
		this.numberOfTrip = 0;
		this.transpCost = 0;
	}
	
	public void trovaImpiantoAssociato(Model model) { 
		
		double distanza;
		double bestDistanza = Double.MAX_VALUE;
		
		for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) { /////DFT
			DFT dft = entry.getValue();
			distanza = this.posizione.distance(dft.posizione);
			if (distanza < bestDistanza) {
				bestDistanza = distanza;
				this.DFTassociato = dft;
			}
		}
		
		for (Map.Entry<String, DFL> entry : model.manager.mapDFL.entrySet()) { /////DFT
			DFL dfl = entry.getValue();
			distanza = this.posizione.distance(dfl.posizione);
			if (distanza < bestDistanza) {
				bestDistanza = distanza;
				this.DFLassociato = dfl;
				this.DFTassociato = null;
			}	
		}	
		
		if (this.DFTassociato != null) {
			this.DFTassociato.CAPassociati.add(this);
			distanceFromDc = bestDistanza; //processo OnRunInitialized
		} else if (this.DFLassociato != null) {
			this.DFLassociato.CAPassociati.add(this);
			distanceFromDc = bestDistanza; //processo OnRunInitialized
		} else {
			throw new IllegalStateException("ATTENZIONE: A monte del CAP non c'è un DFT o un DFL associato");
		}
		
	}
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		trovaImpiantoAssociato(model);
		
		/* inserisco il processo OnRunInitialized nel metodo trovaImpiantoAssociato
		//processo OnRunInitialized
		if (DFTassociato != null)
			distanceFromDc = this.posizione.distance(DFTassociato.posizione); //distanza il CAP e il DFT associato;
		else if (DFLassociato != null)
			distanceFromDc = this.posizione.distance(DFLassociato.posizione); //distanza il CAP e il DFL associato;
		else
			throw new IllegalStateException("ATTENZIONE: A monte del CAP non c'è un DFT o un DFL associato");
		*/
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

	@Override
	public String toString() {
		return "CAP [name=" + name + ", posizione=" + posizione +
			//	", DFTassociato=" + DFTassociato.name + ", DFLassociato=" + DFLassociato.name +
				", weeklyDemandValue=" + weeklyDemandValue + ", actualDemand=" + actualDemand
				+ ", totalDemand=" + totalDemand + ", totalDemandSatisfied=" + totalDemandSatisfied
				+ ", weekDemandSatisfied=" + weekDemandSatisfied + ", distanceFromDc=" + distanceFromDc
				+ ", numberOfTrip=" + numberOfTrip + ", transpCost=" + transpCost + ", kmTravelled=" + kmTravelled
				+ "]";
	}

	
	
	
}

