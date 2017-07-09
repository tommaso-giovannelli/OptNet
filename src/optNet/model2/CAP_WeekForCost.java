package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;

public class CAP_WeekForCost implements Steppable {
	
	CAP cap;
	
	WeightedGraph<Object, DefaultWeightedEdge> grafo;

	public CAP_WeekForCost(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) { //processo CostProcess -->  viene lanciato alla fine della settimana
		
		Model model = (Model) state;
		
		this.grafo = model.grafo;
		
		cap.numberOfTrip = cap.numberOfTrip + Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY);
		
		cap.transpCost = cap.numberOfTrip * cap.distanceFromDc * model.KM_COST;
		
		cap.kmTravelled = cap.kmTravelled +  Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY)*cap.distanceFromDc;
		
	}
	
	
}
