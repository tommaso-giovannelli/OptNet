package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_ROL implements Steppable {

	DFL dfl;
	
	
	public DFL_ROL(DFL dfl) {
		super();
		this.dfl = dfl;
	}


	public void step(SimState state) { //process ROC
			
			Model model = (Model) state;
		
			if (dfl.inventoryWeight < dfl.s_) {
				
				if (model.LogicaDiRiordino == 3) {
			
					dfl.dFTOrder = dfl.dFTOrder + dfl.EOQ; //(s,Q)
			
					dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.EOQ; //(s,Q)
					
				} else if (model.LogicaDiRiordino == 4) {
			
					dfl.dFTOrder = dfl.dFTOrder + dfl.S - dfl.inventoryWeight; //(s,S)
			
					dfl.DFTassociato.orderFromDFL = dfl.DFTassociato.orderFromDFL + dfl.S - dfl.inventoryWeight; //(s,S)
					
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino deve essere ROL");
				}
			
			}
		
			//System.out.println("DFL_ROL" + dfl.name + " " + Calendario.giorno + " " + Calendario.steps);	
				
	}

}
