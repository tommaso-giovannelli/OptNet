package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AzzeratoreDFL implements Steppable {
	
	DFL dfl;

	public AzzeratoreDFL(DFL dfl) {
		super();
		this.dfl = dfl;
	}
	
	public void step(SimState state) {
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
		
			dfl.dFLWeeklyDemand = 0; 

			dfl.dFTOrder = 0; 
				
			dfl.weekVolSatisfied = 0; 
			
			dfl.inventoryWeight = 0; 
			
			dfl.kmVolDFTDFL[0] = 0; 
			
			dfl.kmVolDFTDFL[1] = 0;
			
			dfl.numberOfTrip = 0; 
			
			dfl.kmTravelled = 0; 
			
			dfl.transpCost = 0; 
		
		}
	}

}

