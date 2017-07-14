package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_OnRunEnding implements Steppable {
	
	DFL dfl;

	public DFL_OnRunEnding(DFL dfl) {
		super();
		this.dfl = dfl;
	}
	
	public void step(SimState state) { //processo OnRunEnding -->  viene lanciato alla fine della simulazione e somma il costo di trasporto per servire il DFL al costo totale della rete
		
		Model model = (Model) state;
		
		Model_Prestazioni.totalCost = Model_Prestazioni.totalCost + dfl.transpCost;
		
		Model_Prestazioni.toDFLCost = Model_Prestazioni.toDFLCost + dfl.transpCost;
		
		Model_Prestazioni.toDFLKm = Model_Prestazioni.toDFLKm + dfl.kmTravelled;
		
	}
}
