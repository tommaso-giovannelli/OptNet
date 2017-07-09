package optNet.model2;

import java.util.List;

import optNet.connection.POIReadExcelFile;
import optNet.connection.RigaExcelCAP;
import optNet.connection.RigaExcelDFL;
import optNet.connection.RigaExcelDFT;
import optNet.connection.RigaExcelPlant;

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
	
	public Manager manager; //contiene le informazioni di tutti gli oggetti, anche gli impianti associati a monte e a valle
	
	
	
	public Continuous2D modelField = new Continuous2D(1.0,width,height); ////////////MASON
	
	
	
	public Model(long seed) ////////////MASON
    {
    super(seed);
    }
	
	
	
	/**
	 * metodo che legge il file sim_legge.txt, crea gli impianti e il manager e riempie quest'ultimo con i Plant, i CAP e i centri di distribuzione attivati 
	 */
	public void creaImpianti() {
		
		this.manager = new Manager();
		
		POIReadExcelFile poi = new POIReadExcelFile();
		
		List<RigaExcelCAP> listCAP = poi.riempiListaCAP();
		
		List<RigaExcelDFT> listDFT = poi.riempiListaDFT();
		
		List<RigaExcelDFL> listDFL = poi.riempiListaDFL();
		
		List<RigaExcelPlant> listPlant = poi.riempiListaPlant();
		
		//crea Plant e CAP leggendoli dai file di input (CAP.csv e Plant.csv) e usando i relativi costruttori (tutti i fields saranno inizializzati a 0 eccetto nome e posizione); aggiungi questi oggetti nel manager
		for (RigaExcelCAP r : listCAP) {
			
			Double2D posizione = new Double2D((double) r.getCoordinataX(),(double) r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			CAP cap = new CAP(r.getNome(),posizione); //costruisco l'oggetto CAP
			
			manager.putCAP(cap); //inserisco l'oggetto nel manager
		}
		
		for (RigaExcelPlant r : listPlant) {
			
			Double2D posizione = new Double2D((double) r.getCoordinataX(),(double) r.getCoordinataY());
			
			Plant plant = new Plant(r.getNome(),posizione);
			
			manager.putPlant(plant);
		}
		
		//leggi file sim_legge.txt e determina i DFT e DFL da creare
		
		//crea DFT e DFL leggendoli dai file di input (DFT.csv e DFL.csv) e usando i relativi costruttori (tutti i fields saranno inizializzati a 0 eccetto nome e posizione); aggiungi questi oggetti nel manager
		for (RigaExcelDFT r : listDFT) {
			
			Double2D posizione = new Double2D((double) r.getCoordinataX(),(double) r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFT dft = new DFT(r.getNome(),posizione, r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFT(dft); //inserisco l'oggetto nel manager
		}
		
		for (RigaExcelDFL r : listDFL) {
			
			Double2D posizione = new Double2D((double) r.getCoordinataX(),(double) r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFL dfl = new DFL(r.getNome(),posizione, r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFL(dfl); //inserisco l'oggetto nel manager
		}
	}
	
	/**
	 * metodo che associa ad ogni impianto l'impianto da cui sarà servito e/o l'impianto che servirà; tali info vengono memorizzate nei relativi campi di ogni oggetto
	 */
	public void creaSupplyChain() {
		
		
	
		//per ogni Plant memorizza i DFT 
		manager.
		
		//per ogni DFL memorizza il DFT più vicino 
		
		//per ogni CAP memorizza il DFT o DFL più vicino 
		
		/////////MASON per trovare l'oggetto più vicino si potrebbe usare questa funzione:
		/////////public Bag getNearestNeighbors(Double2D position,int atLeastThisMany,boolean toroidal,boolean nonPointObjects,boolean radial,Bag result)
		
	}
	
	
	/**
	 * dato il manager, il metodo legge il file sim_legge.txt e modifica il manager 
	 * @throws Exception 
	 */
	public void modificaManager() {
		
		if (this.manager == null) 
			throw new NullPointerException("Il manager è vuoto");
		
		//leggi file sim_legge.txt
		
		//determina DFT e DFL da eliminare
		
		//elimina tali centri dal manager
		
		//determina i DFT e DFL da creare; per crearli leggi info da DFT.csv e DFL.csv
		
		//collega ogni DFT creato ai Plant
		
		//per ogni DFL cerca il DFT più vicino e memorizzalo come DFTassociato; inoltre aggiungi il DFL nella lista dei DFL serviti dal DFT
		
		//per ogni CAP cerca il DFT o DFL più vicino e memorizzalo come impianto associato; inoltre aggiungi il CAP nella lista dei CAP serviti dal DFL
	
	}
	
		
	/**
	 * @return il numero di DFT 
	 */
	public int contaDFT() { //conta il numero di oggetti che sono DFT
		
	}
	
	
	/**
	 * @return il numero di DFL
	 */
	public int contaDFL() { //conta il numero di oggetti che sono DFT
		
	}
	
	
	//il metodo start fa iniziare la simulazione	
	public void start() { ////////////MASON
		
		super.start();
		
		numDFT = contaDFT();
		
		numDFL = contaDFL();
		
		//inserire nello Schedule gli impianti memorizzati nel manager  
		
		//da completare
	}	

	
	public static void main(String[] args) { ////////////MASON
		
		Model model = new Model(System.currentTimeMillis());
		
		final int NUMERO_SIMULAZIONI = 50;
		
		final int NUMERO_RUN = 4;
		
		int contatoreSimulazioni = 0;
		
		model.creaGrafo();
		
		while (contatoreSimulazioni < NUMERO_SIMULAZIONI) {
			
			//inserire codice per leggere info da sim_legge.txt
			
			int contatoreRun = 0;
			
			model.modificaGrafo();
			
			while(contatoreRun < NUMERO_RUN) {
				
	    		model.start();
	    		
	    		long steps;
	    		
	    		do
	    			{
	    			if (!model.schedule.step(model))
	    				break;
	    			steps = model.schedule.getSteps();
	    			if (steps % 500 == 0)
	    				System.out.println("Steps: " + steps + " Time: " + model.schedule.time());
	    			}
	    		while(steps < 5000);
	    		
	    		model.finish();
	    		
	    		//inserire codice per memorizzare il costo totale e il livello di servizio del singolo run
	    		
			}
			
			//inserire codice per calcolare il valor medio del costo totale e del livello di servizio ottenuti nei vari run
			
			//inserire codice per riempire il file opt_legge.txt da dare in input all'ottimizzatore;
			
			//vedi pag 66 della tesi di marco per i diagrammi di flusso dell'ottimizzatore e del simulatore
			
			System.exit(0);  // make sure any threads finish up
		}
		
		//doLoop(Model.class, args);
		//System.exit(0);
	}

}
