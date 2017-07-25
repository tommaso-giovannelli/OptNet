package optNet.prova;

import org.apache.commons.math3.distribution.TriangularDistribution;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Plant_OrderFromDFT implements Steppable {
	
	Plant plant;
	
	int leadTimeInt; //Lead time

	public Plant_OrderFromDFT(Plant plant) {
		super();
		this.plant = plant;
	}
	
	public void step(SimState state) { //processo StartDelivery
		
		Model model = (Model) state;
		
		if (Calendario.giorno == 1) {
		
			TriangularDistribution leadTime = new TriangularDistribution(new MTFApache(model.random), 3, 4, 7); //il LT può essere un qualunque numero intero compreso tra 3 e 6 (estremi inclusi)
		
			leadTimeInt = (int) leadTime.sample();
		}
		
		if (Calendario.giorno == (0 + leadTimeInt)) {
		
			int indexForDFT = 0;
		
			//assegno a orderVector gli ordini dei DFT a cui il Plant è associato
			for (DFT dft : plant.listaDFT) {
				
				if (plant.orderVector[indexForDFT] > 0) {
						
					plant.qtaProdotta[indexForDFT] = plant.orderVector[indexForDFT]; //quantità prodotta per questo DFT
				
					plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] - plant.qtaProdotta[indexForDFT];
						
					dft.inventoryWeight = dft.inventoryWeight + plant.qtaProdotta[indexForDFT];
					
					dft.volPlantDFT[plant.numeroIDPlant-1] = dft.volPlantDFT[plant.numeroIDPlant-1] + plant.qtaProdotta[indexForDFT];
					
			///		dft.plantOrder = dft.plantOrder - plant.qtaProdotta[indexForDFT];
				
					indexForDFT = indexForDFT + 1;
				
				}
				
			}
		
			//System.out.println("Plant_OrderFromDFT" + plant.name + " " + Calendario.giorno + " " + Calendario.steps);
		}
	
	}
	
	
}

