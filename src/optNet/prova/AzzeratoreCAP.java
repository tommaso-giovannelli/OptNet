package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AzzeratoreCAP implements Steppable {
	
	CAP cap;

	public AzzeratoreCAP(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) {
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
		
			cap.weeklyDemandValue = 0; 
	
			cap.actualDemand = 0; 
			
		//	cap.totalDemand = 0;  
			
		//	cap.totalDemandSatisfied = 0; 
			
			cap.weekDemandSatisfied = 0;
					
			cap.numberOfTrip = 0; 
			
			cap.transpCost = 0; 
					
			cap.kmTravelled = 0;
		
		}
	}

}
