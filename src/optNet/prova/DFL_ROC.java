package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_ROC implements Steppable {

	DFL dfl;
	
	
	public DFL_ROC(DFL dfl) {
		super();
		this.dfl = dfl;
	}


	public void step(SimState state) { //process ROC
		
		if (Calendario.giorno == 1) {
			
			Model model = (Model) state;
		
			if (dfl.inventoryWeight < dfl.s_) {
				
				if (model.LogicaDiRiordino == 1) {
					
					dfl.dFTOrder = dfl.dFTOrder + dfl.EOQ; //(R,s,Q)
			
					dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.EOQ; //(R,s,Q)
					
				} else if (model.LogicaDiRiordino == 2) {
			
					dfl.dFTOrder = dfl.dFTOrder + dfl.S - dfl.inventoryWeight; //(R,s,S)
			
					dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.S - dfl.inventoryWeight; //(R,s,S)
					
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino deve essere ROC");
				}
			}
		
			//System.out.println("DFL_R" + dfl.name + " " + Calendario.giorno + " " + Calendario.steps);	
		
		}		
	}

}
