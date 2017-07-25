package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AzzeratorePlant implements Steppable {
	
	Plant plant;

	public AzzeratorePlant(Plant plant) {
		super();
		this.plant = plant;
	}
	
	public void step(SimState state) {
		
		Model model = (Model) state;
		
		if (Calendario.settimane == 52 && Calendario.giorno == 7) {
		
			for (int i = 0; i < plant.orderVector.length; i++) {
				 plant.orderVector[i] = 0;
			}
			
			for (int i = 0; i < plant.qtaProdotta.length; i++) {
				 plant.qtaProdotta[i] = 0;
			}
		
		}
	}

}

