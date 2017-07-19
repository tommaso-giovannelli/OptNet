package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class CAP_WeekForCost implements Steppable {
	
	CAP cap;

	public CAP_WeekForCost(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) { //processo CostProcess -->  viene lanciato alla fine della settimana
		
		Model model = (Model) state;
		
		cap.numberOfTrip = cap.numberOfTrip + Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY);
		
		CAP.transpCost = CAP.transpCost + Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY) * cap.distanceFromDc * model.KM_COST;
		
		cap.kmTravelled = cap.kmTravelled +  Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY)*cap.distanceFromDc;
		
		cap.weekDemandSatisfied = 0;
		
	}
	
	
}
