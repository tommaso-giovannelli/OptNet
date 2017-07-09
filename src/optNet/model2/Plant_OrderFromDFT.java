package optNet.model;

import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Plant_OrderFromDFT implements Steppable {
	
	Plant plant;
	
	WeightedGraph<Object, DefaultWeightedEdge> grafo;

	public Plant_OrderFromDFT(Plant plant) {
		super();
		this.plant = plant;
	}
	
	public void step(SimState state) { //processo StartDelivery
		
		Model model = (Model) state;
		
		this.grafo = model.grafo;
		
		plant.orderVector = new double[model.numDFT];
		
		//assegno a orderVector gli ordini dei DFT a cui il Plant è collegato
		for (Object obj : Graphs.neighborListOf(grafo, plant)) {
			
			int indexForDFT = 0;
			
			if (obj != null && instanceof DFT) {
				
				DFT dft = (DFT) obj;
				
				while (indexForDFT < ) {
						
					plant.qtaProdotta[indexForDFT] = plant.orderVector[indexForDFT]; //quantità prodotta per questo DFT
				
					plant.orderVector[indexForDFT] = plant.orderVector[indexForDFT] - plant.qtaProdotta[indexForDFT];
					
					dft.inventoryWeight = dft.inventoryWeight + plant.qtaProdotta[indexForDFT];
				
					dft.volPlantDFT[indexForDFT] = dft.volPlantDFT[indexForDFT] + plant.qtaProdotta[indexForDFT];
				
					dft.plantOrder = dft.plantOrder - plant.qtaProdotta[indexForDFT];
				
					indexForDFT = indexForDFT + 1;
				}
				
			} else {
				
				throw new IllegalStateException("Il Plant non è collegato a un DFT");
				
			}
		}
	
	}
	
	
}

