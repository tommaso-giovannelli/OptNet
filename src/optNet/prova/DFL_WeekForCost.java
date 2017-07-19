package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_WeekForCost implements Steppable {
	
	DFL dfl;

	public DFL_WeekForCost(DFL dfl) {
		super();
		this.dfl = dfl;
	}
	
	public void step(SimState state) { //processo CostProcess -->  viene lanciato alla fine della settimana
		
		if (Calendario.giorno == 7) {
			
			Model model = (Model) state;
		
			dfl.numberOfTrip = dfl.numberOfTrip + Math.ceil(dfl.weekVolSatisfied/model.TRANSP_CAPACITY);
		
			dfl.transpCost = dfl.transpCost + Math.ceil(dfl.weekVolSatisfied/model.TRANSP_CAPACITY)*dfl.kmVolDFTDFL[0]*model.KM_COST;
		
			dfl.kmTravelled = dfl.kmTravelled +  Math.ceil(dfl.weekVolSatisfied/model.TRANSP_CAPACITY)*dfl.kmVolDFTDFL[0];
		
			dfl.weekVolSatisfied = 0;
		
			//System.out.println("DFL_WeekForCost" + dfl.name + " " + Calendario.giorno + " " + Calendario.steps);
		}
		
	}

}
