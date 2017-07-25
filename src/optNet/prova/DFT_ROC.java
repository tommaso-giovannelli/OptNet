package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFT_ROC implements Steppable {

	DFT dft;
	
	
	public DFT_ROC(DFT dft) {
		super();
		this.dft = dft;
	}


	public void step(SimState state) { //process ROC
		
		if (Calendario.giorno == 1) {
		
			Model model = (Model) state;
		
			if (dft.inventoryWeight < dft.s_) {
				
				if (model.LogicaDiRiordino == 1) {
					
					dft.plantOrder = dft.EOQ; //(R,s,Q)
				
				} else if (model.LogicaDiRiordino == 2) { 
			
					dft.plantOrder = dft.S - dft.inventoryWeight; //(R,s,S)
					
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino deve essere ROC");
				}
			
				for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) {
				
					Plant plant = entry.getValue();
				
					int indexForDFT = plant.listaDFT.indexOf(dft);
				
					plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] + plant.PERCENT_DFT*(dft.plantOrder);
				
				}
			
			}
		
			//System.out.println("DFT_R" + dft.name + " " + Calendario.giorno + " " + Calendario.steps);
		}
		
	}

}
