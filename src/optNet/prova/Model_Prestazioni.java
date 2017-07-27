package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Model_Prestazioni implements Steppable {
	

	public static double allCapDemand = 0; //domanda totale generata dai CAP nel periodo considerato
	
	public static double allDemandSatisfied = 0; //domanda totale dei CAP che è stata soddisfatta nel periodo considerato
	
	public static double serviceLevel = 0; //livello di servizio (AllCapDemand/AllDemandSatisfied)
	
	public static double totalCost = 0; //costo totale di trasporto da sostenere con la configurazione di rete considerata
	
	public static double toCapCost = 0; //costo totale per raggiungere i CAP dai DFT o DFL 
	
	public static double toDFLCost = 0; //costo totale per raggiungere i DFL dai DFT 
	
	public static double toDFTCost = 0; //costo totale per raggiungere i DFT dai Plant 
	
	public static double toCapKm = 0; //km totali percorsi per raggiungere i CAP dai DFT o DFL 
	
	public static double toDFLKm = 0; //km totali percorsi per raggiungere i DFL dai DFT 
	
	public static double toDFTKm = 0; //km totali percorsi per raggiungere i DFT dai Plant
	
	public static double qtaTotProdotta = 0; //quantità totale prodotta dai plant nel periodo considerato
	
	
	
	
	public static double allCapDemandTot = 0; //domanda totale generata dai CAP in tutte le repliche
	
	public static double allDemandSatisfiedTot = 0; //domanda totale dei CAP che è stata soddisfatta in tutte le repliche
	
	public static double serviceLevelTot = 0; //somma dei livelli di servizio ottenuti alla fine di ogni replica
	
	public static double totalCostTot = 0; //costo totale di trasporto da sostenere con la configurazione di rete considerata in tutte le repliche
	
	public static double toCapCostTot = 0; //costo totale per raggiungere i CAP dai DFT o DFL in tutte le repliche
	
	public static double toDFLCostTot = 0; //costo totale per raggiungere i DFL dai DFT in tutte le repliche
	
	public static double toDFTCostTot = 0; //costo totale per raggiungere i DFT dai Plant in tutte le repliche
	
	public static double toCapKmTot = 0; //km totali percorsi per raggiungere i CAP dai DFT o DFL in tutte le repliche
	
	public static double toDFLKmTot = 0; //km totali percorsi per raggiungere i DFL dai DFT in tutte le repliche
	
	public static double toDFTKmTot = 0; //km totali percorsi per raggiungere i DFT dai Plant in tutte le repliche
	
	public static double qtaTotProdottaTot = 0; //quantità totale prodotta dai plant in tutte le repliche
	
	
	
	
	public Model_Prestazioni() {
		super();
	}
	
	
	
	public void step(SimState state) { //il seguente codice faceva parte del processo OnRunEnding del Model di Simio
		
		Model model = (Model) state;
		
		
		
		if (Calendario.giorno == 7) {
			
			for (Map.Entry<String, CAP> entry : model.managerMutabile.mapCAP.entrySet()) { 
				
				CAP cap = entry.getValue();
				
				cap.numberOfTrip = Math.ceil(cap.weekDemandSatisfied/model.TRANSP_CAPACITY);
				
				cap.transpCost = cap.numberOfTrip * cap.distanceFromDc * model.KM_COST;
			
				cap.kmTravelled = cap.numberOfTrip * cap.distanceFromDc;
				
				allCapDemand = allCapDemand + cap.weeklyDemandValue;
				
				allDemandSatisfied = allDemandSatisfied + cap.weekDemandSatisfied;
			
				totalCost = totalCost + cap.transpCost;
				
				toCapCost = toCapCost + cap.transpCost;
				
				toCapKm = toCapKm + cap.kmTravelled;
				
				cap.weekDemandSatisfied = 0;
			}
			
			for (Map.Entry<String, DFL> entry : model.managerMutabile.mapDFL.entrySet()) {
				
				DFL dfl = entry.getValue();
				
				dfl.numberOfTrip = Math.ceil(dfl.weekVolSatisfied/model.TRANSP_CAPACITY);
				
				dfl.transpCost = dfl.numberOfTrip * dfl.kmVolDFTDFL[0] * model.KM_COST;
			
				dfl.kmTravelled = dfl.numberOfTrip * dfl.kmVolDFTDFL[0];
				
				totalCost = totalCost + dfl.transpCost;
				
				toDFLCost = toDFLCost + dfl.transpCost;
			
				toDFLKm = toDFLKm + dfl.kmTravelled;
			
				dfl.weekVolSatisfied = 0;
			}
			
			for (Map.Entry<String, DFT> entry : model.managerMutabile.mapDFT.entrySet()) { 
				
				DFT dft = entry.getValue();
				
				for (Map.Entry<String, Plant> entry2 : model.managerMutabile.mapPlant.entrySet()) {	
					
					Plant plant = entry2.getValue();
					
					dft.numberOfTrip = Math.ceil(dft.volPlantDFT[plant.numeroIDPlant-1]/dft.PLANT_DFT_CAPACITY_TRANSP);			
					
					dft.plantDFTCost = dft.numberOfTrip * dft.kmPlantDFT[plant.numeroIDPlant-1] * plant.KM_COST;
					
					dft.kmTravelled = dft.numberOfTrip * dft.kmPlantDFT[plant.numeroIDPlant-1];
					
					totalCost = totalCost + dft.plantDFTCost;
					
					toDFTCost = toDFTCost + dft.plantDFTCost;
				
					toDFTKm = toDFTKm + dft.kmTravelled;
				}
			}
			
			for (Map.Entry<String, Plant> entry : model.managerMutabile.mapPlant.entrySet()) { 
				
				Plant plant = entry.getValue();
				
				for (int i=0; i<plant.qtaProdotta.length; i++) {
				
					qtaTotProdotta = qtaTotProdotta + plant.qtaProdotta[i];	
					
				}
				
			}
				
			serviceLevel = allDemandSatisfied / allCapDemand;				
		
			System.out.println(this);
			
		}
		
		
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
			
			Model_Prestazioni.allCapDemandTot = Model_Prestazioni.allCapDemandTot + Model_Prestazioni.allCapDemand; 
			
			Model_Prestazioni.allDemandSatisfiedTot = Model_Prestazioni.allDemandSatisfiedTot +  Model_Prestazioni.allDemandSatisfied; 
			
			Model_Prestazioni.serviceLevelTot = Model_Prestazioni.serviceLevelTot + Model_Prestazioni.serviceLevel;
			
			Model_Prestazioni.totalCostTot = Model_Prestazioni.totalCostTot + Model_Prestazioni.totalCost; 
			
			Model_Prestazioni.toCapCostTot = Model_Prestazioni.toCapCostTot + Model_Prestazioni.toCapCost; 
			
			Model_Prestazioni.toDFLCostTot = Model_Prestazioni.toDFLCostTot + Model_Prestazioni.toDFLCost; 
			
			Model_Prestazioni.toDFTCostTot = Model_Prestazioni.toDFTCostTot + Model_Prestazioni.toDFTCost; 
			
			Model_Prestazioni.toCapKmTot = Model_Prestazioni.toCapKmTot + Model_Prestazioni.toCapKm; 
			
			Model_Prestazioni.toDFLKmTot = Model_Prestazioni.toDFLKmTot + Model_Prestazioni.toDFLKm; 
			
			Model_Prestazioni.toDFTKmTot = Model_Prestazioni.toDFTKmTot + Model_Prestazioni.toDFTKm; 
			
			Model_Prestazioni.qtaTotProdottaTot = Model_Prestazioni.qtaTotProdottaTot + Model_Prestazioni.qtaTotProdotta;
			
		}
		
		
		
		if (Calendario.steps == 365) {
			
			model.allCapDemandMedio = Model_Prestazioni.allCapDemandTot/model.contatoreRepliche ; 
			
			model.allDemandSatisfiedMedio = Model_Prestazioni.allDemandSatisfiedTot/model.contatoreRepliche; 
			
			model.serviceLevelMedio = Model_Prestazioni.serviceLevelTot/model.contatoreRepliche; 
			
			model.totalCostMedio = Model_Prestazioni.totalCostTot/model.contatoreRepliche; 
			
			model.toCapCostMedio = Model_Prestazioni.toCapCostTot/model.contatoreRepliche; 
			
			model.toDFLCostMedio = Model_Prestazioni.toDFLCostTot/model.contatoreRepliche; 
			
			model.toDFTCostMedio = Model_Prestazioni.toDFTCostTot/model.contatoreRepliche; 
			
			model.toCapKmMedio = Model_Prestazioni.toCapKmTot/model.contatoreRepliche; 
			
			model.toDFLKmMedio = Model_Prestazioni.toDFLKmTot/model.contatoreRepliche; 
			
			model.toDFTKmMedio = Model_Prestazioni.toDFTKmTot/model.contatoreRepliche; 
			
			model.qtaTotProdottaMedio = Model_Prestazioni.qtaTotProdottaTot/model.contatoreRepliche;
			
			System.out.println("Misure di prestazione totali [allCapDemandTot=" + Model_Prestazioni.allCapDemandTot + ", allDemandSatisfiedTot=" + Model_Prestazioni.allDemandSatisfiedTot
			+ ", serviceLevelTot=" + Model_Prestazioni.serviceLevelTot + ", totalCostTot=" + Model_Prestazioni.totalCostTot + ", toCapCostTot=" + Model_Prestazioni.toCapCostTot
			+ ", toDFLCostTot=" + Model_Prestazioni.toDFLCostTot + ", toDFTCostTot=" + Model_Prestazioni.toDFTCostTot + ", toCapKmTot=" + Model_Prestazioni.toCapKmTot + ", toDFLKmTot="
			+ Model_Prestazioni.toDFLKmTot + ", toDFTKmTot=" + Model_Prestazioni.toDFTKmTot + ", qtaTotProdottaTot=" + Model_Prestazioni.qtaTotProdottaTot + "]");
			
			System.out.println("Misure di prestazione medie [allCapDemandMedio=" + model.allCapDemandMedio + ", allDemandSatisfiedMedio=" + model.allDemandSatisfiedMedio
			+ ", serviceLevelMedio=" + model.serviceLevelMedio + ", totalCostMedio=" + model.totalCostMedio + ", toCapCostMedio=" + model.toCapCostMedio
			+ ", toDFLCostMedio=" + model.toDFLCostMedio + ", toDFTCostMedio=" + model.toDFTCostMedio + ", toCapKmMedio=" + model.toCapKmMedio + ", toDFLKmMedio="
			+ model.toDFLKmMedio + ", toDFTKmMedio=" + model.toDFTKmMedio + ", qtaTotProdottaMedio=" + model.qtaTotProdottaMedio + "]");
			
		} 
		
	}
	

	@Override
	public String toString() {
		return "Model_Prestazioni [allCapDemand=" + allCapDemand + ", allDemandSatisfied=" + allDemandSatisfied
				+ ", serviceLevel=" + serviceLevel + ", totalCost=" + totalCost + ", toCapCost=" + toCapCost
				+ ", toDFLCost=" + toDFLCost + ", toDFTCost=" + toDFTCost + ", toCapKm=" + toCapKm + ", toDFLKm="
				+ toDFLKm + ", toDFTKm=" + toDFTKm + ", qtaTotProdotta=" + qtaTotProdotta + "]";
	}
	

}
