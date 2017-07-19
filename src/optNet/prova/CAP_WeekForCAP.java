package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class CAP_WeekForCAP implements Steppable {
	
	CAP cap;

	public CAP_WeekForCAP(CAP cap) {
		super();
		this.cap = cap;
	}
	
	public void step(SimState state) { //processo CapDemand --> viene lanciato all'inizio della settimana
		
		if (Calendario.giorno == 1) {
			
			Model model = (Model) state;
		
			cap.actualDemand = cap.actualDemand + cap.weeklyDemandValue;
		
			CAP.totalDemand = CAP.totalDemand + cap.weeklyDemandValue;
				
			if (cap.DFTassociato != null) {
				cap.DFTassociato.dFTWeeklyDemand = cap.DFTassociato.dFTWeeklyDemand + cap.weeklyDemandValue;
			} else if (cap.DFLassociato != null) {
				cap.DFLassociato.dFLWeeklyDemand = cap.DFLassociato.dFLWeeklyDemand + cap.weeklyDemandValue;
			} else {
				throw new IllegalStateException("ATTENZIONE: Non � vero che il CAP � collegato a un DFT o un DFL");
			}
	    
			//System.out.println("CAP_WeekForCAP" + cap.name + " " + Calendario.giorno + " " + Calendario.steps);
		}
		
	}
	
	
}
