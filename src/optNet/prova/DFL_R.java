package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_R implements Steppable {

	DFL dfl;
	
	
	public DFL_R(DFL dfl) {
		super();
		this.dfl = dfl;
	}


	public void step(SimState state) { //process ROC
		
		if (Calendario.giorno == 1) {
			
			Model model = (Model) state;
		
			if (dfl.inventoryWeight < dfl.s_) {
			
				dfl.dFTOrder = dfl.dFTOrder + dfl.EOQ; //(R,s,Q)
			
				dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.EOQ; //(R,s,Q)
			
				//dfl.dFTOrder = dfl.dFTOrder + dfl.S - dfl.inventoryWeight; //(R,s,S)
			
				//dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.S - dfl.inventoryWeight; //(R,s,S)
			}
		
			//System.out.println("DFL_R" + dfl.name + " " + Calendario.giorno + " " + Calendario.steps);	
		
		}		
	}

}
