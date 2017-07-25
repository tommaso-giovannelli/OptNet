package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFT_OnRunEnding implements Steppable {
	
	DFT dft;

	public DFT_OnRunEnding(DFT dft) {
		super();
		this.dft = dft;
	}
	
	public void step(SimState state) { //processo OnRunEnding -->  viene lanciato alla fine della simulazione e somma il costo di trasporto per servire il DFT al costo totale della rete
		
		if (Calendario.giorno == 7) {
			
			Model model = (Model) state;
		
			for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) {	
				Plant plant = entry.getValue();
				dft.plantDFTCost = dft.plantDFTCost + Math.ceil(dft.volPlantDFT[plant.numeroIDPlant-1]/dft.PLANT_DFT_CAPACITY_TRANSP)*dft.kmPlantDFT[plant.numeroIDPlant-1]*plant.KM_COST;
				dft.numberOfTrip = dft.numberOfTrip + Math.ceil(dft.volPlantDFT[plant.numeroIDPlant-1]/dft.PLANT_DFT_CAPACITY_TRANSP);			
				dft.kmTravelled = dft.kmTravelled + Math.ceil(dft.volPlantDFT[plant.numeroIDPlant-1]/dft.PLANT_DFT_CAPACITY_TRANSP)*dft.kmPlantDFT[plant.numeroIDPlant-1];
			}
		
			Model_Prestazioni.totalCost = Model_Prestazioni.totalCost + dft.plantDFTCost;
		
			Model_Prestazioni.toDFTCost = Model_Prestazioni.toDFTCost + dft.plantDFTCost;
		
			Model_Prestazioni.toDFTKm = Model_Prestazioni.toDFTKm + dft.kmTravelled;
		
			//System.out.println("DFT_OnRunEnding" + dft.name + " " + Calendario.giorno + " " + Calendario.steps);
		}
		
	}
	

}
