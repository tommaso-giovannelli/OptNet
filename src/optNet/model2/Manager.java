package optNet.prova;

import java.util.HashMap;
import java.util.Map;

public class Manager {
	
	public Map<String,CAP> mapCAP;
	
	public Map<String,DFT> mapDFT;
	
	public Map<String,DFL> mapDFL;
	
	public Map<String,Plant> mapPlant;

	public Manager() {
		super();
		mapCAP = new HashMap<>();
		mapDFT = new HashMap<>();
		mapDFL = new HashMap<>();
		mapPlant = new HashMap<>();
	}
	
	public CAP getCAP(String name) {
		return mapCAP.get(name);
	}
	
	public DFT getDFT(String name) {
		return mapDFT.get(name);
	}
	
	public DFL getDFL(String name) {
		return mapDFL.get(name);
	}
	
	public Plant getPlant(String name) {
		return mapPlant.get(name);
	}
	
	public void putCAP(CAP cap) {
		CAP old = mapCAP.get(cap.name);
		if (old == null) {
			mapCAP.put(cap.name, cap);
		} else {
			throw new IllegalStateException("ATTENZIONE: il CAP è già presente nella mappa");
		}		
	}
	
	public void putDFT(DFT dft) {
		DFT old = mapDFT.get(dft.name);
		if (old == null) {
			mapDFT.put(dft.name, dft);
		} else {
			throw new IllegalStateException("ATTENZIONE: il DFT è già presente nella mappa");
		}		
	}
	
	public void putDFL(DFL dfl) {
		DFL old = mapDFL.get(dfl.name);
		if (old == null) {
			mapDFL.put(dfl.name, dfl);
		} else {
			throw new IllegalStateException("ATTENZIONE: il DFL è già presente nella mappa");
		}		
	}
	
	public void putPlant(Plant plant) {
		Plant old = mapPlant.get(plant.name);
		if (old == null) {
			mapPlant.put(plant.name, plant);
		} else {
			throw new IllegalStateException("ATTENZIONE: il Plant è già presente nella mappa");
		}		
	}

}
