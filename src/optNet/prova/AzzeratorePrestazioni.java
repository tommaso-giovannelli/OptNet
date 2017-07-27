package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AzzeratorePrestazioni implements Steppable {

	public AzzeratorePrestazioni() {
		super();
	}
	
	public void step(SimState state) {
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
		
			Model_Prestazioni.allCapDemand = 0; 
			
			Model_Prestazioni.allDemandSatisfied = 0; 
			
			Model_Prestazioni.serviceLevel = 0; 
			
			Model_Prestazioni.totalCost = 0;
			
			Model_Prestazioni.toCapCost = 0; 
			
			Model_Prestazioni.toDFLCost = 0; 
			
			Model_Prestazioni.toDFTCost = 0; 
			
			Model_Prestazioni.toCapKm = 0; 
			
			Model_Prestazioni.toDFLKm = 0; 
			
			Model_Prestazioni.toDFTKm = 0; 
			
			Model_Prestazioni.qtaTotProdotta = 0;
		
		}
	}

}
