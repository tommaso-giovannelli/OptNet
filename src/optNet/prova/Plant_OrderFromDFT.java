package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Plant_OrderFromDFT implements Steppable {
	
	Plant plant;

	public Plant_OrderFromDFT(Plant plant) {
		super();
		this.plant = plant;
	}
	
	public void step(SimState state) { //processo StartDelivery
		
		Model model = (Model) state;
		
		int indexForDFT = 0;
		
		//assegno a orderVector gli ordini dei DFT a cui il Plant è associato
		for (DFT dft : plant.listaDFT) {
						
			plant.qtaProdotta[indexForDFT] = plant.orderVector[indexForDFT]; //quantità prodotta per questo DFT
			
			plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] - plant.qtaProdotta[indexForDFT];
					
			dft.inventoryWeight = dft.inventoryWeight + plant.qtaProdotta[indexForDFT];
				
		//	dft.volPlantDFT[indexForDFT] = dft.volPlantDFT[indexForDFT] + plant.qtaProdotta[indexForDFT];
				
			dft.plantOrder = dft.plantOrder - plant.qtaProdotta[indexForDFT];
			
			indexForDFT = indexForDFT + 1;
				
		}
	
	}
	
	
}

