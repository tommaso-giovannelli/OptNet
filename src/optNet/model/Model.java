package optNet.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.Continuous2D;

public class Model extends SimState {
	
	public double AllCapDemand; //domanda totale generata dai CAP nel periodo considerato
	
	public double AllDemandSatisfied; //domanda totale dei CAP che è stata soddisfatta nel periodo considerato
	
	public double ServiceLevel; //livello di servizio (AllCapDemand/AllDemandSatisfied)
	
	public double TotalCost; //costo totale di trasporto da sostenere con la configurazione di rete considerata
	
	public double ToCapCost; //costo totale per raggiungere i CAP dai DFT o DFL 
	
	public double ToDFLCost; //costo totale per raggiungere i DFL dai DFT 
	
	public double ToDFTCost; //costo totale per raggiungere i DFT dai Plant 
	
	public double ToCapKm; //km totali percorsi per raggiungere i CAP dai DFT o DFL 
	
	public double ToDFLKm; //km totali percorsi per raggiungere i DFL dai DFT 
	
	public double ToDFTKm; //km totali percorsi per raggiungere i DFT dai Plant
	
	public final double TRANSP_CAPACITY = 0; //capacità del mezzo di trasporto
	
    public final double KM_COST = 0; //costo al km
	
	public int width = 10000; //ampiezza del field model ////////////MASON
	
	public int height = 10000; //altezza del field model ////////////MASON
	
	public final int NUM_PLANT = 6; //numero dei Plant
	
	public final int NUM_CAP = 4413; //numero dei CAP
	
	public int numDFT; //numero dei DFT
	
	public int numDFL; //numero dei DFL
	
	public WeightedGraph<Object, DefaultWeightedEdge> grafo; //grafo non orientato pesato in cui i nodi sono gli impianti della Supply Chain,
															  //gli archi collegano due impianti associati e i pesi degli archi rappresentano
															  //le distanze tra questi ultimi
	
	public Continuous2D modelField = new Continuous2D(1.0,width,height); ////////////MASON
	
	public Model(long seed) ////////////MASON
    {
    super(seed);
    }
	
	/**
	 * metodo che legge il file sim_legge.txt e crea il grafo che ha come nodi i Plant, i CAP e i centri di distribuzione attivati 
	 */
	public void creaGrafo() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//da completare
		
		//leggi info da CAP.csv e da Plant.csv
		
		//crea Plant e CAP letti dai file
		
		//leggi file sim_legge.txt e determina i DFT e DFL da creare
		
		//leggi info da DFT.csv e DFL.csv
		
		//crea DFT e DFL letti dai file
		
		//aggiungi questi oggetti come nodi del grafo
		
		//per ogni Plant crea un arco verso ogni DFT
		
		//per ogni DFL crea un arco verso il DFT più vicino 
		
		//per ogni CAP crea un arco verso il DFT o DFL più vicino 
		
		/////////MASON per trovare l'oggetto più vicino si potrebbe usare questa funzione:
		/////////public Bag getNearestNeighbors(Double2D position,int atLeastThisMany,boolean toroidal,boolean nonPointObjects,boolean radial,Bag result)
		
	}
	
	/**
	 * @return il numero di DFT 
	 */
	public int contaDFT() { //conta il numero di vertici del grafo che sono DFT
		
	}
	
	/**
	 * @return il numero di DFL
	 */
	public int contaDFL() { //conta il numero di vertici del grafo che sono DFT
		
	}
	
	//il metodo start fa iniziare la simulazione
	//non c'è modo di passare al metodo start il grafo già costruito, quindi devo ricostruire da capo il grafo all'inizio di ogni run	
	public void start() { ////////////MASON
	
	//public void start(WeightedGraph<Object, DefaultWeightedEdge> grafo) { ////////////MASON
		
		super.start();
		
		creaGrafo();
		
		numDFT = contaDFT();
		
		numDFL = contaDFL();
		
		//da completare
	}

	public static void main(String[] args) { ////////////MASON
		doLoop(Model.class, args);
		System.exit(0);
	}

}
