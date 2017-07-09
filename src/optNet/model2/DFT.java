package optNet.model2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class DFT implements Steppable {
	
	public String name;
	
	public Double2D posizione; 
	
	public List<CAP> CAPassociati; //lista dei CAP che vengono serviti da questo DFT
	
	public List<DFL> DFLassociati; //lista dei DFL che vengono serviti da questo DFT(possono essere DFL o CAP)
	
	public double dFTWeeklyDemand; //raggruppa tutta la domanda attuale pervenuta dai CAP da servire
	
	public double plantOrder; //rappresenta il volume totale dell’ordine da inviare ai Plant
	
	public double orderFromDFL; //ordini ricevuti dai DFL
	
	public double inventoryWeight; //scorte
	
	public double[] volPlantDFT; //vettore che tiene conto della quantità totale che il DFT ha ricevuto dai Plant dall'inizio della simulazione
	
	//private double[] kmPlantDFT; //vettore che memorizza la distanza da ogni Plant
	
	//private double plantDFTCost; //costo di trasporto per raggiungere il DFT dai Plant 
	
	//private final double PLANT_DFT_CAPACITY_TRANSP; //capacità del mezzo di trasporto che consente di raggiungere il DFT dai Plant
	
	//private double numberOfTrip; //numero di viaggi compiuti tra i Plant e il DFT per soddisfare gli ordini del DFT
	
	//private double kmTravelled; //chilometri percorsi per consegnare prodotti dai Plant al CDc  
	
	//costruttore
	public DFT(String name, Double2D posizione, double initialStock) {
		super();
		this.name = name;
		this.posizione = posizione;
		this.dFTWeeklyDemand = 0;
		this.plantOrder = 0;
		this.orderFromDFL = 0;
		this.inventoryWeight = initialStock;
		this.volPlantDFT = new double[0];
	}
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 
		
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
		DFT other = (DFT) obj;
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
		return "DFT [name=" + name + ", posizione=" + posizione + ", clientiAssociati=" + clientiAssociati
				+ ", dFTWeeklyDemand=" + dFTWeeklyDemand + ", plantOrder=" + plantOrder + ", orderFromDFL="
				+ orderFromDFL + ", inventoryWeight=" + inventoryWeight + ", volPlantDFT="
				+ Arrays.toString(volPlantDFT) + "]";
	}
	
	
	
}
