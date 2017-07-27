package optNet.prova;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
////public int width = 100; //ampiezza del field model ////////////MASON
	
////public int height = 100; //altezza del field model ////////////MASON

    public final int NUM_PLANT = 6; //numero dei Plant
	
////public final int NUM_PLANT = 2; //per validazione modello
	
	public final int NUM_CAP = 4413; //numero dei CAP
	
////public final int NUM_CAP = 4; //per validazione modello
	
	public int numDFT; //numero dei DFT
	
	public int numDFL; //numero dei DFL
	
	public Manager manager; //contiene le informazioni di tutti gli oggetti (anche gli impianti non attivati)
	
	public Manager managerMutabile; //contiene le informazioni di tutti e soli gli oggetti attivati
	
	public final int NUMERO_SIMULAZIONI = 2;
	
	public final int NUMERO_REPLICHE = 2;
	
	public int contatoreRepliche = 1;
	
	public int contatoreSimulazioni = 1;
	
	public int LogicaDiRiordino = 2; //1 ROC (R,s,Q)
									 //2 ROC (R,s,S)
								     //3 ROL (s,Q)
								     //4 ROL (s,S)
	
	
	
	
	
	public double allCapDemandMedio; //domanda totale generata dai CAP in media nelle repliche
	
	public double allDemandSatisfiedMedio; //domanda totale dei CAP che è stata soddisfatta in media nelle repliche
	
	public double serviceLevelMedio; //livello di servizio medio (AllCapDemand/AllDemandSatisfied) 
	
	public double totalCostMedio; //costo totale di trasporto medio da sostenere con la configurazione di rete considerata
	
	public double toCapCostMedio; //costo totale medio per raggiungere i CAP dai DFT o DFL 
	
	public double toDFLCostMedio; //costo totale medio per raggiungere i DFL dai DFT 
	
	public double toDFTCostMedio; //costo totale medio per raggiungere i DFT dai Plant 
	
	public double toCapKmMedio; //km totali percorsi per raggiungere i CAP dai DFT o DFL in media nelle repliche
	
	public double toDFLKmMedio; //km totali percorsi per raggiungere i DFL dai DFT in media nelle repliche
	
	public double toDFTKmMedio; //km totali percorsi per raggiungere i DFT dai Plant in media nelle repliche
	
	public double qtaTotProdottaMedio; //quantità totale prodotta dai plant in media nelle repliche
	
	
	
	
	
	public Continuous2D modelField = new Continuous2D(1.0,width,height); 
	
	
	
	
	
	public Model(long seed) 
    {
    super(seed);
    }
	

	
	
	
	/**
	 * metodo che legge il file sim_legge.txt, crea gli impianti e il manager e riempie quest'ultimo con i Plant, i CAP e i centri di distribuzione attivati 
	 * @throws Exception 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void creaImpianti() throws FileNotFoundException, IOException, Exception {
		
		this.manager = new Manager();
		
		this.managerMutabile = new Manager();
		
		POIReadExcelFile poi = new POIReadExcelFile();
		
		List<RigaExcelCAP> listCAP = poi.riempiListaCAP();
		
		List<RigaExcelDFT> listDFT = poi.riempiListaDFT();
		
		List<RigaExcelDFL> listDFL = poi.riempiListaDFL();
		
		List<RigaExcelPlant> listPlant = poi.riempiListaPlant();
		
		//crea tutti i Plant e CAP leggendoli dai file di input (CAP.csv e Plant.csv) e usando i relativi costruttori (tutti i fields saranno inizializzati a 0 eccetto nome e posizione); aggiungi questi oggetti nel manager
		for (RigaExcelCAP r : listCAP) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()-2000000000); //costruisco la posizione ricavando le coordinate da r
			
////////////Double2D posizione = new Double2D(r.getCoordinataX(), r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r

////////////double CAPWeeklyDemand = r.getCAPWeeklyDemandMin(); ///per prova validazione modello
			
			CAP cap = new CAP(r.getNome(),posizione,r.getCAPWeeklyDemandMin(), r.getCAPWeeklyDemandMedio(),r.getCAPWeeklyDemandMax()); //costruisco l'oggetto CAP
			
			manager.putCAP(cap); //inserisco l'oggetto nel manager
			
			managerMutabile.putCAP(cap);
		}
		
		for (RigaExcelPlant r : listPlant) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()-2000000000);
			
////////////Double2D posizione = new Double2D(r.getCoordinataX(), r.getCoordinataY());
			
			Plant plant = new Plant(r.getNome(), posizione, r.getPercentDFT(), r.getKmCost());
			
			manager.putPlant(plant);
			
			managerMutabile.putPlant(plant);
		}
		
		//crea tutti i DFT e DFL leggendoli dai file di input (DFT.csv e DFL.csv) e usando i relativi costruttori (tutti i fields saranno inizializzati a 0 eccetto nome e posizione); aggiungi questi oggetti nel manager
		for (RigaExcelDFT r : listDFT) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()-2000000000); //costruisco la posizione ricavando le coordinate da r
			
////////////Double2D posizione = new Double2D(r.getCoordinataX(), r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFT dft = new DFT(r.getNome(), posizione, r.getS_(), r.getS(), r.getEOQ(), r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFT(dft); //inserisco l'oggetto nel manager
			
			managerMutabile.putDFT(dft);
		}
		
		for (RigaExcelDFL r : listDFL) {
			
			Double2D posizione = new Double2D(r.getCoordinataX()+2000000000, r.getCoordinataY()-2000000000); //costruisco la posizione ricavando le coordinate da r
			
////////////Double2D posizione = new Double2D(r.getCoordinataX(), r.getCoordinataY()); //costruisco la posizione ricavando le coordinate da r
			
			DFL dfl = new DFL(r.getNome(),posizione, r.getS_(), r.getS(), r.getEOQ(), r.getInitialStock()); //costruisco l'oggetto CAP
			
			manager.putDFL(dfl); //inserisco l'oggetto nel manager
			
			managerMutabile.putDFL(dfl);
		}
		
		//leggi file sim_legge.txt e determina i DFT e DFL da eliminare per la prima simulazione
		ReaderFile reader = new ReaderFile();
				
		int[] arrayAttivazione = reader.leggiFile(); //nell'array i primi 12 elementi riguardano i DFT, gli altri 248 i DFL
		
		for (int i=0; i<arrayAttivazione.length; i++) {
			if (i<12) {
				if (arrayAttivazione[i] == 0) {
					int numeroIdDFT = i+1;
					String dftName = "DFT" + numeroIdDFT;
					managerMutabile.eliminaDFT(dftName);
				}
			} else {
				if (arrayAttivazione[i] == 0) {
					int numeroIdDFL = i-11;
					String dflName = "DFL" + numeroIdDFL;
					managerMutabile.eliminaDFL(dflName);
				}
			}
		}		
		
	}
	
	
	
	
	
	/**
	 * dato il manager, il metodo legge il file sim_legge.txt e modifica il managerMutabile nelle simulazioni successive alla prima 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public void riempiManagerMutabile() throws FileNotFoundException, IOException, Exception {
		
		if (this.manager == null) 
			throw new NullPointerException("Il manager è vuoto");
		
		managerMutabile.mapDFT.putAll(manager.mapDFT);
		
		managerMutabile.mapDFL.putAll(manager.mapDFL);
		
		//leggi file sim_legge.txt e determina i DFT e DFL da eliminare per le simulazioni successive alla prima
		ReaderFile reader = new ReaderFile();
				
		int[] arrayAttivazione = reader.leggiFile(); //nell'array i primi 12 elementi riguardano i DFT, gli altri 248 i DFL
		
		for (int i=0; i<arrayAttivazione.length; i++) {
			if (i<12) {
				if (arrayAttivazione[i] == 0) {
					int numeroDFT = i+1;
					String dftName = "DFT" + numeroDFT;
					managerMutabile.eliminaDFT(dftName);
				}
			} else {
				if (arrayAttivazione[i] == 0) {
					int numeroDFL = i-11;
					String dflName = "DFL" + numeroDFL;
					managerMutabile.eliminaDFL(dflName);
				}
			}
		}
	
	}
	
	
	
	
	//metodi getter per il Model Inspector
	public double getAllCapDemand() {
		return Model_Prestazioni.allCapDemand;
	}	
	
	public double getAllDemandSatisfied() {
		return Model_Prestazioni.allDemandSatisfied;
	}
	
	public double getServiceLevel() {
		return Model_Prestazioni.serviceLevel;
	}
	
	public double getTotalCost() {
		return Model_Prestazioni.totalCost;
	}
	
	public double getToCapCost() {
		return Model_Prestazioni.toCapCost;
	}
	
	public double getToDFTCost() {
		return Model_Prestazioni.toDFTCost;
	}
	
	public double getToDFLCost() {
		return Model_Prestazioni.toDFLCost;
	}
	
	public double getToCapKm() {
		return Model_Prestazioni.toCapKm;
	}
	
	public double getToDFTKm() {
		return Model_Prestazioni.toDFTKm;
	}
	
	public double getToDFLKm() {
		return Model_Prestazioni.toDFLKm;
	}
	
	
	
		
	/**
	 * @return il numero di DFT 
	 */
	public int contaDFT() { //conta il numero di oggetti che sono DFT
		return this.managerMutabile.mapDFT.size();
	}
	
	
	/**
	 * @return il numero di DFL
	 */
	public int contaDFL() { //conta il numero di oggetti che sono DFT
		return this.managerMutabile.mapDFL.size();
	}
	
	/**
	 * @return il numero di CAP 
	 */
	public int contaCAP() { //conta il numero di oggetti che sono DFT
		return this.managerMutabile.mapCAP.size();
	}
	
	/**
	 * @return il numero di Plant 
	 */
	public int contaPlant() { //conta il numero di oggetti che sono DFT
		return this.managerMutabile.mapPlant.size();
	}
	
	
	
	
	
	
	//il metodo start fa iniziare la simulazione	
	public void start() { ////////////MASON
		
		super.start();
		
		try {
			
			if (this.contatoreSimulazioni == 1) { 
				creaImpianti();
			} else {
				riempiManagerMutabile();
			} 
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		numDFT = contaDFT();
		
		numDFL = contaDFL();
		
		//imposto il calendario di esecuzione dei processi
		Calendario calendario = new Calendario();
		schedule.scheduleRepeating(Schedule.EPOCH,0,calendario);
		
		//inserisco nello Schedule gli impianti memorizzati nel manager 
			for (Map.Entry<String, CAP> entry : managerMutabile.mapCAP.entrySet()) { /////CAP
				CAP cap = entry.getValue();
				modelField.setObjectLocation(cap, cap.posizione );
				schedule.scheduleOnce(Schedule.EPOCH, 0, cap);
				CAP_WeekForCAP CapDemand = new CAP_WeekForCAP(cap);
				schedule.scheduleRepeating(Schedule.EPOCH, 20, CapDemand);
				AzzeratoreCAP azzeraCAP = new AzzeratoreCAP(cap);
				schedule.scheduleOnce(Schedule.EPOCH,Integer.MAX_VALUE,azzeraCAP);
			}
			
			for (Map.Entry<String, DFL> entry : managerMutabile.mapDFL.entrySet()) { /////DFL
				DFL dfl = entry.getValue();
				modelField.setObjectLocation(dfl, dfl.posizione );
				schedule.scheduleOnce(Schedule.EPOCH, 0, dfl);
				if (this.LogicaDiRiordino == 1 || this.LogicaDiRiordino == 2) {
					DFL_ROC roc = new DFL_ROC(dfl);
					schedule.scheduleRepeating(Schedule.EPOCH, 30, roc);
				} else if (this.LogicaDiRiordino == 3 || this.LogicaDiRiordino == 4) {
					DFL_ROL rol = new DFL_ROL(dfl);
					schedule.scheduleRepeating(Schedule.EPOCH, 30, rol);
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino può essere solo di 4 tipi: 1,2,3,4");
				}
				DFL_DayOfDeliveryTimer startDelivery = new DFL_DayOfDeliveryTimer(dfl);
				schedule.scheduleRepeating(Schedule.EPOCH, 10, startDelivery);
				AzzeratoreDFL azzeraDFL = new AzzeratoreDFL(dfl);
				schedule.scheduleOnce(Schedule.EPOCH,Integer.MAX_VALUE,azzeraDFL);
			}
			
			for (Map.Entry<String, DFT> entry : managerMutabile.mapDFT.entrySet()) { /////DFT
				DFT dft = entry.getValue();
				modelField.setObjectLocation(dft, dft.posizione );
				schedule.scheduleOnce(Schedule.EPOCH, 0, dft);
				DFT_DayOfDeliveryTimer startDelivery = new DFT_DayOfDeliveryTimer(dft);
				schedule.scheduleRepeating(Schedule.EPOCH, 10, startDelivery);
				if (this.LogicaDiRiordino == 1 || this.LogicaDiRiordino == 2) {
					DFT_ROC roc = new DFT_ROC(dft);
					schedule.scheduleRepeating(Schedule.EPOCH, 30, roc);
				} else if (this.LogicaDiRiordino == 3 || this.LogicaDiRiordino == 4) {
					DFT_ROL rol = new DFT_ROL(dft);
					schedule.scheduleRepeating(Schedule.EPOCH, 30, rol);
				} else {
					throw new IllegalStateException("ATTENZIONE: la logica di riordino può essere solo di 4 tipi: 1,2,3,4");
				}
				AzzeratoreDFT azzeraDFT = new AzzeratoreDFT(dft);
				schedule.scheduleOnce(Schedule.EPOCH,Integer.MAX_VALUE,azzeraDFT);
			}
			
			for (Map.Entry<String, Plant> entry : managerMutabile.mapPlant.entrySet()) { /////Plant
				Plant plant = entry.getValue();
				modelField.setObjectLocation(plant, plant.posizione );
				schedule.scheduleOnce(Schedule.EPOCH, 0, plant);
				Plant_OrderFromDFT startDelivery = new Plant_OrderFromDFT(plant);
				schedule.scheduleRepeating(Schedule.EPOCH, 50, startDelivery);
				AzzeratorePlant azzeraPlant = new AzzeratorePlant(plant);
				schedule.scheduleOnce(Schedule.EPOCH,Integer.MAX_VALUE,azzeraPlant);
			}
			
			Model_Prestazioni misureDiPrestazione = new Model_Prestazioni();
			schedule.scheduleRepeating(Schedule.EPOCH,10,misureDiPrestazione); //il -1 è per non sovrapporlo all'azzeratore nello schedule
			AzzeratorePrestazioni azzeraPrestazioni = new AzzeratorePrestazioni();
			schedule.scheduleRepeating(Schedule.EPOCH,Integer.MAX_VALUE,azzeraPrestazioni);
		//	StampaImpianti stampa = new StampaImpianti();
		//	schedule.scheduleRepeating(Schedule.EPOCH,Integer.MAX_VALUE - 1,stampa); //il -1 è per non sovrapporlo all'azzeratore nello schedule

	}	

	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, Exception { ////////////MASON
		

		Model model = new Model(System.currentTimeMillis());
		
		model.nameThread();
		
		while (model.contatoreSimulazioni <= model.NUMERO_SIMULAZIONI) {
			
			model.contatoreRepliche = 1;
		
			while(model.contatoreRepliche <= model.NUMERO_REPLICHE) {
				
				System.out.println("\nSimulazione numero " + model.contatoreSimulazioni + " - Replica numero " + model.contatoreRepliche);
					
	    		model.schedule.reset();
				
				model.setJob(model.contatoreRepliche);
				
	    		model.start();
	    		
	    		long steps;
	    		
	    		do {
	    				
	    			if (!model.schedule.step(model))
	    				break;
	    			steps = model.schedule.getSteps();
	    			
	    		} while(steps < 365); 
	    		
	    		model.contatoreRepliche++ ;
	    		
			}  
			
			//codice per riempire il file sim_scrive.txt da dare in input all'ottimizzatore;
			WriterFile writer = new WriterFile();
			
			writer.scriviFile(model);
			
			Model_Prestazioni.allCapDemandTot = 0; 
			
			Model_Prestazioni.allDemandSatisfiedTot = 0; 
			
			Model_Prestazioni.serviceLevelTot = 0;
			
			Model_Prestazioni.totalCostTot = 0; 
			
			Model_Prestazioni.toCapCostTot = 0; 
			
			Model_Prestazioni.toDFLCostTot = 0; 
			
			Model_Prestazioni.toDFTCostTot = 0; 
			
			Model_Prestazioni.toCapKmTot = 0; 
			
			Model_Prestazioni.toDFLKmTot = 0; 
			
			Model_Prestazioni.toDFTKmTot = 0; 
			
			Model_Prestazioni.qtaTotProdottaTot = 0;
			
			model.allCapDemandMedio = 0; 
			
			model.allDemandSatisfiedMedio = 0; 
			
			model.serviceLevelMedio = 0; 
			
			model.totalCostMedio = 0; 
			
			model.toCapCostMedio = 0; 
			
			model.toDFLCostMedio = 0; 
			
			model.toDFTCostMedio = 0; 
			
			model.toCapKmMedio = 0; 
			
			model.toDFLKmMedio = 0; 
			
			model.toDFTKmMedio = 0; 
			
			model.qtaTotProdottaMedio = 0;
			
			//vedi pag 66 della tesi di marco per i diagrammi di flusso dell'ottimizzatore e del simulatore
			
		    //model.finish();
			
			try{
	            Process pr = Runtime.getRuntime().exec("main.exe");
	        //    InputStream in = pr.getInputStream();
	        //    OutputStream out = pr.getOutputStream();
	        //    InputStream err = pr.getErrorStream();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
			
			Thread.sleep(10000);
			
			model.contatoreSimulazioni++;
		}
		
		System.exit(0);  // make sure any threads finish up

/*		

		doLoop(Model.class, args);
		System.exit(0);
	
*/		
	}

}
