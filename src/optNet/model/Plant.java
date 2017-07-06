package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class Plant implements Steppable {
	
	public String name;
	
	public Double2D posizione; 

	public int[] orderVector; //array di dimensione pari al numero di DFT; ogni elemento rappresenta l'ordine del corrispondente DFT
	
	public WeightedGraph<Object, DefaultWeightedEdge> grafo;
	
	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		this.grafo = model.grafo; 		
		
	}
	
}
