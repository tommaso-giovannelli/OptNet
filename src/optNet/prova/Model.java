package optNet.prova;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.TriangularDistribution;

import optNet.connection.POIReadExcelFile;
import optNet.connection.RigaExcelCAP;
import optNet.connection.RigaExcelDFL;
import optNet.connection.RigaExcelDFT;
import optNet.connection.RigaExcelPlant;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.Continuous2D;

public class Model extends SimState {
	
	public final double TRANSP_CAPACITY = 10; //capacità del mezzo di trasporto
	
    public final double KM_COST = 1; //costo al km
	
	public int width = Integer.MAX_VALUE; //ampiezza del field model ////////////MASON
	
	public int height = Integer.MAX_VALUE; //altezza del field model ////////////MASON
	
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
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			TriangularDistribution CAPWeeklyDemand = new TriangularDistribution(new MTFApache(random), r.getCAPWeeklyDemandMin(), r.getCAPWeeklyDemandMedio(), r.getCAPWeeklyDemandMax());
			
			CAP cap = new CAP(r.getNome(),posizione,CAPWeeklyDemand.sample()); //costruisco l'oggetto CAP
			
			manager.putCAP(cap); //inserisco l'oggetto nel manager
		}
		
		for (RigaExcelPlant r : listPlant) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY());
			
			Plant plant = new Plant(r.getNome(), posizione, r.getPercentDFT(), r.getKmCost());
			
			manager.putPlant(plant);
		}
		
		//leggi file sim_legge.txt e determina i DFT e DFL da creare
		
		//crea DFT e DFL leggendoli dai file di input (DFT.csv e DFL.csv) e usando i relativi costruttori (tutti i fields saranno inizializzati a 0 eccetto nome e posizione); aggiungi questi oggetti nel manager
		for (RigaExcelDFT r : listDFT) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFT dft = new DFT(r.getNome(), posizione, r.getS_(), r.getS(), r.getEOQ(), r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFT(dft); //inserisco l'oggetto nel manager
		}
		
		for (RigaExcelDFL r : listDFL) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFL dfl = new DFL(r.getNome(),posizione, r.getS_(), r.getS(), r.getEOQ(), r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFL(dfl); //inserisco l'oggetto nel manager
		}
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
		return this.manager.mapDFT.size();
	}
	
	
	/**
	 * @return il numero di DFL
	 */
	public int contaDFL() { //conta il numero di oggetti che sono DFT
		return this.manager.mapDFL.size();
	}
	
	
	//il metodo start fa iniziare la simulazione	
	public void start() { ////////////MASON
		
		super.start();
		
		creaImpianti();
		
		numDFT = contaDFT();
		
		numDFL = contaDFL();
		
		//inserisco nello Schedule gli impianti memorizzati nel manager 
		for (Map.Entry<String, CAP> entry : manager.mapCAP.entrySet()) { /////CAP
			CAP cap = entry.getValue();
			modelField.setObjectLocation(cap, cap.posizione );
			schedule.scheduleOnce(Schedule.EPOCH, 0, cap);
			CAP_WeekForCAP CapDemand = new CAP_WeekForCAP(cap);
			schedule.scheduleRepeating(Schedule.EPOCH, 1, CapDemand, 7);
			CAP_WeekForCost CostProcess = new CAP_WeekForCost(cap);
			schedule.scheduleRepeating(Schedule.EPOCH + 6, 0, CostProcess, 7);
		}
		
		for (Map.Entry<String, DFL> entry : manager.mapDFL.entrySet()) { /////DFL
			DFL dfl = entry.getValue();
			modelField.setObjectLocation(dfl, dfl.posizione );
			schedule.scheduleOnce(Schedule.EPOCH, 0, dfl);
			DFL_R roc = new DFL_R(dfl);
			schedule.scheduleRepeating(Schedule.EPOCH, 1, roc, 7);
			DFL_DayOfDeliveryTimer startDelivery = new DFL_DayOfDeliveryTimer(dfl);
			schedule.scheduleRepeating(Schedule.EPOCH + 1, 0, startDelivery, 7);
			DFL_WeekForCost costProcess = new DFL_WeekForCost(dfl);
			schedule.scheduleRepeating(Schedule.EPOCH + 6, 0, costProcess, 7);
			DFL_OnRunEnding onRunEnding = new DFL_OnRunEnding(dfl);
			schedule.scheduleRepeating(Schedule.EPOCH + 6, 1, onRunEnding, 7);
		}
		
		for (Map.Entry<String, DFT> entry : manager.mapDFT.entrySet()) { /////DFT
			DFT dft = entry.getValue();
			modelField.setObjectLocation(dft, dft.posizione );
			schedule.scheduleOnce(Schedule.EPOCH, 0, dft);
			DFT_DayOfDeliveryTimer startDelivery = new DFT_DayOfDeliveryTimer(dft);
			schedule.scheduleRepeating(Schedule.EPOCH + 2, 0, startDelivery, 7);
			DFT_R roc = new DFT_R(dft);
			schedule.scheduleRepeating(Schedule.EPOCH, 1, roc, 7);
			DFT_OnRunEnding onRunEnding = new DFT_OnRunEnding(dft);
			schedule.scheduleRepeating(Schedule.EPOCH + 6, 1, onRunEnding, 7);
		}
		
		for (Map.Entry<String, Plant> entry : manager.mapPlant.entrySet()) { /////Plant
			Plant plant = entry.getValue();
			modelField.setObjectLocation(plant, plant.posizione );
			schedule.scheduleOnce(Schedule.EPOCH, 0, plant);
			Plant_OrderFromDFT startDelivery = new Plant_OrderFromDFT(plant);
			schedule.scheduleRepeating(Schedule.EPOCH, 2, startDelivery, 7);
		}
		
		Model_Prestazioni misureDiPrestazione = new Model_Prestazioni();
		schedule.scheduleRepeating(Schedule.EPOCH + 6,2,misureDiPrestazione,7);

	}	

	
	public static void main(String[] args) { ////////////MASON
		
		/*
		Model model = new Model(System.currentTimeMillis());
		
		model.nameThread();
		
		final int NUMERO_SIMULAZIONI = 50;
		
		final int NUMERO_REPLICHE = 4;
		
		int contatoreSimulazioni = 0;
		
	//	model.creaGrafo();
		
		while (contatoreSimulazioni < NUMERO_SIMULAZIONI) {
			
			//inserire codice per leggere info da sim_legge.txt
			
			int contatoreRepliche = 0;
			
		//	model.modificaGrafo();
			
			while(contatoreRepliche < NUMERO_REPLICHE) {
				
				model.setJob(contatoreRepliche);
				
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
	    		
	    		contatoreRepliche++ ;
	    		
	    		//inserire codice per memorizzare il costo totale e il livello di servizio del singolo run
	    		
			}
			
			//inserire codice per calcolare il valor medio del costo totale e del livello di servizio ottenuti nei vari run
			
			//inserire codice per riempire il file opt_legge.txt da dare in input all'ottimizzatore;
			
			//vedi pag 66 della tesi di marco per i diagrammi di flusso dell'ottimizzatore e del simulatore
			
			System.exit(0);  // make sure any threads finish up
			
			contatoreSimulazioni++;
		}
		*/
		
		
		doLoop(Model.class, args);
		System.exit(0);
		
	}

}
