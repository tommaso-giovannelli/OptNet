package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFT_R implements Steppable {

	DFT dft;
	
	
	public DFT_R(DFT dft) {
		super();
		this.dft = dft;
	}


	public void step(SimState state) { //process ROC
		
		Model model = (Model) state;
		
		if (dft.inventoryWeight < dft.s_) {
			
			dft.plantOrder = dft.plantOrder + dft.EOQ; //(R,s,Q)
			
			//dft.plantOrder = dft.plantOrder + dft.S - dft.inventoryWeight; //(R,s,S)
			
			for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) {
				
				Plant plant = entry.getValue();
				
				int indexForDFT = plant.listaDFT.indexOf(dft);
				
				plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] + plant.PERCENT_DFT*(dft.plantOrder);
				
			}
			
		}
			
		
	}

}
