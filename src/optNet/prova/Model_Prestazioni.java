package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Model_Prestazioni implements Steppable {
	

	public static double allCapDemand = 0; //domanda totale generata dai CAP nel periodo considerato
	
	public static double allDemandSatisfied = 0; //domanda totale dei CAP che è stata soddisfatta nel periodo considerato
	
	public static double serviceLevel = 0; //livello di servizio (AllCapDemand/AllDemandSatisfied)
	
	public static double totalCost = 0; //costo totale di trasporto da sostenere con la configurazione di rete considerata
	
	public static double toCapCost = 0; //costo totale per raggiungere i CAP dai DFT o DFL 
	
	public static double toDFLCost = 0; //costo totale per raggiungere i DFL dai DFT 
	
	public static double toDFTCost = 0; //costo totale per raggiungere i DFT dai Plant 
	
	public static double toCapKm = 0; //km totali percorsi per raggiungere i CAP dai DFT o DFL 
	
	public static double toDFLKm = 0; //km totali percorsi per raggiungere i DFL dai DFT 
	
	public static double toDFTKm = 0; //km totali percorsi per raggiungere i DFT dai Plant
	
	
	public Model_Prestazioni() {
		super();
	}
	
	public void step(SimState state) { //il seguente codice faceva parte del processo OnRunEnding del Model di Simio
		
		Model_Prestazioni.allCapDemand = Model_Prestazioni.allCapDemand + CAP.totalDemand; 
				
		Model_Prestazioni.allDemandSatisfied = Model_Prestazioni.allDemandSatisfied + CAP.totalDemandSatisfied;
				
		Model_Prestazioni.totalCost = Model_Prestazioni.totalCost + CAP.transpCost;
				
		Model_Prestazioni.toCapCost = Model_Prestazioni.toCapCost + CAP.transpCost;
				
		Model_Prestazioni.serviceLevel = Model_Prestazioni.allDemandSatisfied / this.allCapDemand;
		
		//System.out.println(this);
		
	}

	@Override
	public String toString() {
		return "Model_Prestazioni [allCapDemand=" + allCapDemand + ", allDemandSatisfied=" + allDemandSatisfied
				+ ", serviceLevel=" + serviceLevel + ", totalCost=" + totalCost + ", toCapCost=" + toCapCost
				+ ", toDFLCost=" + toDFLCost + ", toDFTCost=" + toDFTCost + ", toCapKm=" + toCapKm + ", toDFLKm="
				+ toDFLKm + ", toDFTKm=" + toDFTKm + "]";
	}
	

}
