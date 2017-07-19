package optNet.prova;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Calendario implements Steppable {
	
	public static int giorno;
	public static int contatoreGiorni;
	public static int settimane;
	public static int steps;
	public int contatoreRepliche;
	
	public Calendario() {
		Calendario.giorno = 0;
		Calendario.settimane = 1;
		Calendario.steps = 0;
		contatoreRepliche = 1;
	}
	
	public void step(SimState state) {
		
		Model model = (Model) state;
		
		steps++;
		giorno++;
		
		if (giorno == 8) {
			settimane++;
			giorno = 1;
		}
		
		System.out.println(" Settimana: " + Calendario.settimane + " - step: " + Calendario.steps + " - Giorno: " + Calendario.giorno);
	
		/*
		if (contatoreRepliche <= model.NUMERO_REPLICHE) {
			if(steps==364) {
				model.schedule.reset();
				model.start();
				contatoreRepliche++;
			}
		} else {
			model.finish();
		}
		*/
		
	//	if (steps == 365 && contatoreRepliche == model.NUMERO_REPLICHE) {
	//		model.finish();
	//	}
		
		
	}
}
