package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.Continuous2D;

public class Model extends SimState {
	
	private int width = 10000; //ampiezza del field model
	
	private int height = 10000; //altezza del field model
	
	public Continuous2D modelField = new Continuous2D(1.0,width,height); 
	
	private WeightedGraph<Object, DefaultWeightedEdge> grafo;
	
	public Model(long seed)
    {
    super(seed);
    }
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public void start() {
		super.start();
		//da completare
	}

	public static void main(String[] args) {
		doLoop(Model.class, args);
		System.exit(0);
	}

}
