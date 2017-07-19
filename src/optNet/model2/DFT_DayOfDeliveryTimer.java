package optNet.prova;

import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DFT_DayOfDeliveryTimer implements Steppable  {
	
	DFT dft;

	public DFT_DayOfDeliveryTimer(DFT dft) {
		super();
		this.dft = dft;
	}
	
public void step(SimState state) { //processo StartDelivery -->  viene lanciato il terzo giorno della settimana
		
		Model model = (Model) state;
		
		List<CAP> listaCAPordinata = new ArrayList<CAP>(); //lista dei CAP serviti dal dfl ordinati per domanda decrescente
		
		List<DFL> listaDFLordinata = new ArrayList<DFL>(); //lista dei DFL serviti dal dfl ordinati per domanda decrescente
		
		listaCAPordinata.addAll(dft.CAPassociati);
		
		listaDFLordinata.addAll(dft.DFLassociati);
		
		listaCAPordinata.sort(new CAPDemandComparator());
		
		listaDFLordinata.sort(new DFLDemandComparator());
		
		if (dft.inventoryWeight > 0) {
			if (dft.dFTWeeklyDemand > 0) {
				for (CAP cap : listaCAPordinata) {
					double qtaSpedita = 0;
					if (dft.inventoryWeight > 0) {
						if (cap.actualDemand < dft.inventoryWeight) {
							qtaSpedita = cap.actualDemand;
						} else {
							qtaSpedita = dft.inventoryWeight;
						}
						dft.inventoryWeight = dft.inventoryWeight - qtaSpedita;
						dft.dFTWeeklyDemand = dft.dFTWeeklyDemand - qtaSpedita;
						cap.actualDemand = cap.actualDemand - qtaSpedita;
						CAP.totalDemandSatisfied = CAP.totalDemandSatisfied + qtaSpedita;
						cap.weekDemandSatisfied = cap.weekDemandSatisfied + qtaSpedita;
					} else {
						break;
					}
				}
			} else {
				if (dft.orderFromDFL > 0) {
					for (DFL dfl : listaDFLordinata) {
						double qtaSpedita = 0;
						if (dft.inventoryWeight > 0) {
							if (dfl.dFTOrder < dft.inventoryWeight) {
								qtaSpedita = dfl.dFTOrder;
							} else {
								qtaSpedita = dft.inventoryWeight;
							}
							dft.inventoryWeight = dft.inventoryWeight - qtaSpedita;
							dft.orderFromDFL= dft.orderFromDFL - qtaSpedita;
							dfl.inventoryWeight = dfl.inventoryWeight + qtaSpedita;
							dfl.dFTOrder = dfl.dFTOrder - qtaSpedita;
							dfl.weekVolSatisfied = dfl.weekVolSatisfied + qtaSpedita;
						} else {
							break;
						}
					}
				}
			}
		}
		
		
		
	}

}
