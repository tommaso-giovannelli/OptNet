package optNet.prova;

import java.util.Comparator;

public class CAPDemandComparator implements Comparator<CAP> {

	@Override
	public int compare(CAP cap1, CAP cap2) {
		
		if (cap1.weeklyDemandValue > cap2.weeklyDemandValue) {
			return -1;
		} else if (cap1.weeklyDemandValue < cap2.weeklyDemandValue) {
			return +1;
		} else {
			return 0;
		}
			
	}
	
	

}
