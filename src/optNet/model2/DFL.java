package optNet.prova;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;
import sim.util.Double2D;

public class DFL implements Steppable {

	public String name;
	
	public Double2D posizione; 
	
	public DFT DFTassociato; //DFT che serve questo DFL
	
	public List<CAP> CAPassociati; //lista dei CAP che vengono serviti da questo DFL
	
	public double s_; //livello di riordino
	
	public double S; //quantità variabile riordinata
	
	public double EOQ; //lotto economico
	
	public double dFLWeeklyDemand; //domanda generata dai CAP di competenza che il DFL deve soddisfare

	public double dFTOrder; //ordine di prodotto inviato al DFT
		
	public double weekVolSatisfied; //volume di prodotto ricevuto dal DFT nella settimana considerata
	
	public double inventoryWeight; //scorte
	
	public double[] kmVolDFTDFL; //vettore a 2 componenti che tiene conto della distanza dal DFT da cui viene servito e della quantità di prodotto che è stata consegnata dal DFT al DFL
	
	public double numberOfTrip; //numero di viaggi compiuti tra il DFT e il DFL per soddisfare gli ordini del DFL
	
	public double kmTravelled; //numero di km percorsi per raggiungere il DFL dal DFT
	
	public double transpCost; //costo totale di trasporto per raggiungere il DFL dal DFT
	
	//private final double TRANSP_CAPACITY; //capacità del mezzo di trasporto per servire il DFL
	
	//private final double KM_COST; //costo al km
	
	//costruttore
	public DFL(String name, Double2D posizione, double s_, double S, double EOQ, double initialStock) {
		super();
		this.name = name;
		this.posizione = posizione;
		this.dFLWeeklyDemand = 0;
		this.dFTOrder = 0;
		this.weekVolSatisfied = 0;
		this.numberOfTrip = 0;
		this.kmTravelled = 0;
		this.transpCost = 0;
		this.s_ = s_;
		this.S = S;
		this.EOQ = EOQ;
		this.inventoryWeight = initialStock;
		this.kmVolDFTDFL = new double[1]; 
		this.CAPassociati = new ArrayList<CAP>();
	}
	
	public void trovaDFTassociato(Model model) { 
		
		double distanza;
		double bestDistanza = Double.MAX_VALUE;
		
		for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) { /////DFT
			DFT dft = entry.getValue();
			distanza = this.posizione.distance(dft.posizione);
			if (distanza < bestDistanza) {
				bestDistanza = new Double(distanza);
				this.DFTassociato = dft;
			}				
		}
		
		this.DFTassociato.DFLassociati.add(this);
		this.kmVolDFTDFL[0] = bestDistanza; //processo OnRunInitialized

	}
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		trovaDFTassociato(model);		
		
		System.out.println("DFL: " + this + " -------CAPassociati" + this.CAPassociati + " ---------DFTassociato" + this.DFTassociato);
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
		DFL other = (DFL) obj;
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
		return "DFL [name=" + name + ", posizione=" + posizione 
			//	+ ", DFTassociato=" + DFTassociato.name +
			//	", CAPassociati=" + CAPassociati + 
			//	", dFLWeeklyDemand=" + dFLWeeklyDemand + ", dFTOrder=" + dFTOrder
			//	+ ", weekVolSatisfied=" + weekVolSatisfied + ", inventoryWeight=" + inventoryWeight + "]"
				;
	}
	
	
}
