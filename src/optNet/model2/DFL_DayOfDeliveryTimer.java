package optNet.prova;

import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFL_DayOfDeliveryTimer implements Steppable  {
	
	DFL dfl;

	public DFL_DayOfDeliveryTimer(DFL dfl) {
		super();
		this.dfl = dfl;
	}
	
public void step(SimState state) { //processo StartDelivery -->  viene lanciato il secondo giorno della settimana
		
		Model model = (Model) state;
		
		List<CAP> listaCAPordinata = new ArrayList<CAP>(); //lista dei CAP serviti dal dfl ordinati per domanda decrescente
		
		listaCAPordinata.addAll(dfl.CAPassociati);
		
		listaCAPordinata.sort(new CAPDemandComparator());
		
		if (dfl.inventoryWeight > 0) {
			if (dfl.dFLWeeklyDemand > 0) {
				for (CAP cap : listaCAPordinata) {
					double qtaSpedita = 0;
					if (dfl.inventoryWeight > 0) {
						if (cap.actualDemand < dfl.inventoryWeight) {
							qtaSpedita = cap.actualDemand;
						} else {
							qtaSpedita = dfl.inventoryWeight;
						}
						dfl.inventoryWeight = dfl.inventoryWeight - qtaSpedita;
						dfl.dFLWeeklyDemand = dfl.dFLWeeklyDemand - qtaSpedita;
						cap.actualDemand = cap.actualDemand - qtaSpedita;
						CAP.totalDemandSatisfied = CAP.totalDemandSatisfied + qtaSpedita;
						cap.weekDemandSatisfied = cap.weekDemandSatisfied + qtaSpedita;					
					} else {
						break;
					}
				}
			}
		}

}

}
