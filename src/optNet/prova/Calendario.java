package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Calendario implements Steppable {
	
	public static int giorno;
	public static int settimane;
	public static int steps;
	
	public Calendario() {
		Calendario.giorno = 0;
		Calendario.settimane = 1;
		Calendario.steps = 0;
	}
	
	public void step(SimState state) {
		
		Model model = (Model) state;
		
		steps++;
		giorno++;
		
		if (giorno == 8) {
			settimane++;
			giorno = 1;
		}
		
		if (steps != 365) {
			System.out.println(" Settimana: " + Calendario.settimane + " - step: " + Calendario.steps + " - Giorno: " + Calendario.giorno + " - Numero Replica: " + model.contatoreRepliche);
		}
		
	} 
}
