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
	
	public void step(SimState state) { //processo CapDemand --> viene lanciato all'inizio della settimana
		
		Model model = (Model) state;
		
		this.grafo = model.grafo;
		
		plant.orderVector = new int[model.numDFT];
		
		//assegno a orderVector gli ordini dei DFT a cui il Plant è collegato
		for (Object obj : Graphs.neighborListOf(grafo, plant)) {
			if (obj != null && instanceof DFT) {
				orderVector[] = //ordini di questo DFT
			} else {
				throw new Exception("Il Plant non è collegato a un DFT");
			}
		}
	
	}
	
	
}

