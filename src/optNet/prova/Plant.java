package optNet.prova;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class Plant implements Steppable {
	
	public String name;
	
	public Double2D posizione; 
	
	public List<DFT> listaDFT;

	public double[] orderVector; //array di dimensione pari al numero di DFT; ogni elemento rappresenta l'ordine del corrispondente DFT
	
	public double[] qtaProdotta; //array di dimensione pari al numero di DFT; ogni elemento rappresenta la quantità prodotta per il corrispondente DFT
	
	public int numeroIDPlant; //serve per gestire il campo volPlantDFT del DFT in Plant_OrderFromDFT
	
	public final double PERCENT_DFT; //percentuale degli ordini del DFT che vengono soddisfatti da questo Plant
	
	public final double KM_COST; //costo al km per soddisfare gli ordini del DFT da questo Plant
	
	//costruttore
	public Plant(String name, Double2D posizione, double percentDFT, double kmCost) {
		super();
		this.name = name;
		this.posizione = posizione;
		this.numeroIDPlant = Integer.parseInt(this.name.substring(5,6));
		//this.orderVector = new double[0];
		//this.qtaProdotta = new double[0];
		this.listaDFT = new ArrayList<DFT>();
		this.PERCENT_DFT = percentDFT;
		this.KM_COST = kmCost;
	}
	
	public void impostaListaDFT(Model model) {
		
		for (Map.Entry<String, DFT> entry : model.manager.mapDFT.entrySet()) {
			DFT dft = entry.getValue();
			listaDFT.add(dft);
		}
		
	}

	public void step(SimState state) { ////////////MASON
		
		Model model = (Model) state;
		
		impostaListaDFT(model);
		
		orderVector = new double[model.numDFT];
		
		qtaProdotta = new double[model.numDFT];
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((posizione == null) ? 0 : posizione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plant other = (Plant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Plant [name=" + name + ", posizione=" + posizione +
			//	", listaDFT=" + listaDFT +
				", orderVector="
				+ Arrays.toString(orderVector) + ", qtaProdotta=" + Arrays.toString(qtaProdotta) + "]";
	}
	
	
}
