package optNet.prova;

import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;

public class StampaImpianti implements Steppable {
	
	public void step(SimState state) {
				
		
		if (Calendario.giorno == 1) {
		
			Model model = (Model) state;
		
			for (Map.Entry<String, CAP> entry : model.manager.mapCAP.entrySet()) { 
				CAP cap = entry.getValue();
				System.out.println("CAP: " + cap + "-----------DFTassociato: " + cap.DFTassociato + "---------DFLassociato: " + cap.DFLassociato);
			}
			
			for (Map.Entry<String, DFL> entry : model.manager.mapDFL.entrySet()) { 
				DFL dfl = entry.getValue();
				System.out.println("DFL: " + dfl + " -------CAPassociati" + dfl.CAPassociati + " ---------DFTassociato" + dfl.DFTassociato);
			}
			
			for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) {
				DFT dft = entry.getValue();
				System.out.println("DFT: " + dft + " -------CAPassociati " + dft.CAPassociati + " ---------DFLassociati " + dft.DFLassociati);
			}
		
			for (Map.Entry<String, Plant> entry : model.manager.mapPlant.entrySet()) { 
				Plant plant = entry.getValue();
				System.out.println("Plant: " + plant + "-----------DFTassociati: " + plant.listaDFT);
			}	
		}
		
		
	}	
	
}
