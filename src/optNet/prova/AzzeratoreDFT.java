package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AzzeratoreDFT implements Steppable {
	
	DFT dft;

	public AzzeratoreDFT(DFT dft) {
		super();
		this.dft = dft;
	}
	
	public void step(SimState state) {
		
		Model model = (Model) state;
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
		
			dft.dFTWeeklyDemand = 0; 
			
			dft.plantOrder = 0; 
			
			dft.orderFromDFL = 0; 
			
			dft.inventoryWeight = 0; 
			
			for (int i = 0; i < dft.volPlantDFT.length; i++) {
				 dft.volPlantDFT[i] = 0;
			}
			
			for (int i = 0; i < dft.kmPlantDFT.length; i++) {
				 dft.kmPlantDFT[i] = 0;
			}
			
			dft.plantDFTCost = 0;  
			
			dft.numberOfTrip = 0; 
			
			dft.kmTravelled = 0; 
		
		}
	}

}

