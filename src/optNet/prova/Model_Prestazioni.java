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
	
	
	public Model_Prestazioni() {
		super();
	}
	
	public void step(SimState state) { //il seguente codice faceva parte del processo OnRunEnding del Model di Simio
		
		Model model = (Model) state;
		
		if (Calendario.giorno == 7) {
			
			for (Map.Entry<String, CAP> entry : model.manager.mapCAP.entrySet()) { 
				
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
			
			for (Map.Entry<String, DFL> entry : model.manager.mapDFL.entrySet()) {
				
				DFL dfl = entry.getValue();
				
				dfl.numberOfTrip = Math.ceil(dfl.weekVolSatisfied/model.TRANSP_CAPACITY);
				
				dfl.transpCost = dfl.numberOfTrip * dfl.kmVolDFTDFL[0] * model.KM_COST;
			
				dfl.kmTravelled = dfl.numberOfTrip * dfl.kmVolDFTDFL[0];
				
				totalCost = totalCost + dfl.transpCost;
				
				toDFLCost = toDFLCost + dfl.transpCost;
			
				toDFLKm = toDFLKm + dfl.kmTravelled;
			
				dfl.weekVolSatisfied = 0;
			}
			
			for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) { 
				
				DFT dft = entry.getValue();
				
				for (Map.Entry<String, Plant> entry2 : model.manager.mapPlant.entrySet()) {	
					
					Plant plant = entry2.getValue();
					
					dft.numberOfTrip = Math.ceil(dft.volPlantDFT[plant.numeroIDPlant-1]/dft.PLANT_DFT_CAPACITY_TRANSP);			
					
					dft.plantDFTCost = dft.numberOfTrip * dft.kmPlantDFT[plant.numeroIDPlant-1] * plant.KM_COST;
					
					dft.kmTravelled = dft.numberOfTrip * dft.kmPlantDFT[plant.numeroIDPlant-1];
					
					totalCost = totalCost + dft.plantDFTCost;
					
					toDFTCost = toDFTCost + dft.plantDFTCost;
				
					toDFTKm = toDFTKm + dft.kmTravelled;
				}
			}
				
			serviceLevel = allDemandSatisfied / allCapDemand;
		
			//System.out.println(this);
			
			//System.out.println("Model_Prestazioni" + " " + Calendario.giorno + " " + Calendario.steps);
		}
		
	}

	@Override
	public String toString() {
		return "Model_Prestazioni [allCapDemand=" + allCapDemand + ", allDemandSatisfied=" + allDemandSatisfied
				+ ", serviceLevel=" + serviceLevel + ", totalCost=" + totalCost + ", toCapCost=" + toCapCost
				+ ", toDFLCost=" + toDFLCost + ", toDFTCost=" + toDFTCost + ", toCapKm=" + toCapKm + ", toDFLKm="
				+ toDFLKm + ", toDFTKm=" + toDFTKm + "]";
	}
	

}
