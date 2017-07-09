package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;

public class CAP_WeekForCAP implements Steppable {
	
	CAP cap;
	
	WeightedGraph<Object, DefaultWeightedEdge> grafo;

	public CAP_WeekForCAP(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) { //processo CapDemand --> viene lanciato all'inizio della settimana
		
		Model model = (Model) state;
		
		this.grafo = model.grafo;
		
		double CAPWeeklyDemand = //lo devo leggere dal file Excel degli input; 
		
		cap.weeklyDemandValue = CAPWeeklyDemandValue;
		
		cap.actualDemand = cap.actualDemand + cap.weeklyDemandValue;
		
		cap.totalDemand = cap.totalDemand + cap.weeklyDemandValue;
		
		Object obj = //estrai il centro di distribuzione associato a questo CAP dal grafo (o DFT o DFL)
				
	    if (obj != null && obj instanceof DFT) {
	    	DFT dft = (DFT) obj;
	    	dft.dFTWeeklyDemand = dft.dFTWeeklyDemand + cap.weeklyDemandValue;
	    } else if (obj != null && obj instanceof DFL) {
	    	DFL dfl = (DFL) obj;
	    	dfl.dFLWeeklyDemand = dfl.dFLWeeklyDemand + cap.weeklyDemandValue;
	    } else {
	    	throw new IllegalStateException("ATTENZIONE: Non è vero che il CAP è collegato a un DFT o un DFL");
	    }
		
	}
	
	
}
