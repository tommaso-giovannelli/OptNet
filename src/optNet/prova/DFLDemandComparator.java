package optNet.prova;

import java.util.Comparator;

public class DFLDemandComparator implements Comparator<DFL> {

	@Override
	public int compare(DFL dfl1, DFL dfl2) {
		
		if (dfl1.dFTOrder > dfl2.dFTOrder) {
			return -1;
		} else if (dfl1.dFTOrder < dfl2.dFTOrder) {
			return +1;
		} else {
			return -1;
		}
			
	}
	
	

}