package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class StampaImpianti implements Steppable {
	
	public void step(SimState state) {
		
		Model model = (Model) state;
				
	/*	if (Calendario.giorno == 1) {
		
			for (Map.Entry<String, CAP> entry : model.manager.mapCAP.entrySet()) { 
				CAP cap = entry.getValue();
				System.out.println("CAP: " + cap + "-----------DFTassociato: " + cap.DFTassociato + "---------DFLassociato: " + cap.DFLassociato);
				//System.out.println("Km tra CAP e impianto associato: " + cap.distanceFromDc);
			}
			
			for (Map.Entry<String, DFL> entry : model.manager.mapDFL.entrySet()) { 
				DFL dfl = entry.getValue();
				System.out.println("DFL: " + dfl + " -------CAPassociati" + dfl.CAPassociati + " ---------DFTassociato" + dfl.DFTassociato);
				//System.out.println("Km tra DFL e DFT: " + dfl.kmVolDFTDFL[0]);
			}
			
			for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) {
				DFT dft = entry.getValue();
				System.out.println("DFT: " + dft + " -------CAPassociati " + dft.CAPassociati + " ---------DFLassociati " + dft.DFLassociati);
				//System.out.println("Km tra DFT e Plant: " + dft.kmPlantDFT[0] + " " + dft.kmPlantDFT[1]);
			}
		
			for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) { 
				Plant plant = entry.getValue();
				System.out.println("Plant: " + plant + "-----------DFTassociati: " + plant.listaDFT);
			}	
		} */
		
	//	if ((Calendario.settimane == 1 && Calendario.giorno == 7) || (Calendario.settimane == 52 && Calendario.giorno == 7)) {
			for (Map.Entry<String, CAP> entry : model.manager.mapCAP.entrySet()) { 
				CAP cap = entry.getValue();
				System.out.println("CAP: " + cap);
			}			
			for (Map.Entry<String, DFL> entry : model.manager.mapDFL.entrySet()) { 
				DFL dfl = entry.getValue();
				System.out.println("DFL: " + dfl);
			}
			for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) {
				DFT dft = entry.getValue();
				System.out.println("DFT: " + dft);
			}
			for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) { 
				Plant plant = entry.getValue();
				System.out.println("Plant: " + plant);
			}
	//	}
		
		
	}	
	
}
