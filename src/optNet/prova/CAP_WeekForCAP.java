package optNet.prova;

import org.apache.commons.math3.distribution.TriangularDistribution;

import sim.engine.SimState;
import sim.engine.Steppable;

public class CAP_WeekForCAP implements Steppable {
	
	CAP cap;

	public CAP_WeekForCAP(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) { //processo CapDemand --> viene lanciato all'inizio della settimana
		
		Model model = (Model) state;
		
		if (Calendario.giorno == 1) {
			
			TriangularDistribution CAPWeeklyDemand = new TriangularDistribution(new MTFApache(model.random), cap.CAPWeeklyDemandMin, cap.CAPWeeklyDemandMedio, cap.CAPWeeklyDemandMax);
		
			cap.weeklyDemandValue = CAPWeeklyDemand.sample();
			
			cap.actualDemand = cap.actualDemand + cap.weeklyDemandValue;
				
			if (cap.DFTassociato != null) {
				cap.DFTassociato.dFTWeeklyDemand = cap.DFTassociato.dFTWeeklyDemand + cap.weeklyDemandValue;
			} else if (cap.DFLassociato != null) {
				cap.DFLassociato.dFLWeeklyDemand = cap.DFLassociato.dFLWeeklyDemand + cap.weeklyDemandValue;
			} else {
				throw new IllegalStateException("ATTENZIONE: Non è vero che il CAP è collegato a un DFT o un DFL");
			}
	    
		}
		
	}
	
	
}
