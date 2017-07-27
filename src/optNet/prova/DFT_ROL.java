package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFT_ROL implements Steppable {

	DFT dft;
	
	
	public DFT_ROL(DFT dft) {
		super();
		this.dft = dft;
	}


	public void step(SimState state) { //process ROC
		
			Model model = (Model) state;
		
			if (dft.inventoryWeight < dft.s_) {
				
				if (model.LogicaDiRiordino == 3) {
			
					dft.plantOrder = dft.EOQ; //(s,Q)
					
				} else if (model.LogicaDiRiordino == 4) {
			
					dft.plantOrder = dft.S - dft.inventoryWeight; //(s,S)
				
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino deve essere ROL");
				}
			
				for (Map.Entry<String, Plant> entry : model.managerMutabile.mapPlant.entrySet()) {
				
					Plant plant = entry.getValue();
				
					int indexForDFT = plant.listaDFT.indexOf(dft);
				
					plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] + plant.PERCENT_DFT*(dft.plantOrder);
				
				}
			
			}
		
			//System.out.println("DFT_ROL" + dft.name + " " + Calendario.giorno + " " + Calendario.steps);
		
	}

}

