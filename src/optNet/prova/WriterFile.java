package optNet.prova;

import java.io.*;

public class WriterFile {	

	  public void scriviFile(Model model) {    
		  
	      try {

	          FileOutputStream file = new FileOutputStream("sim_scrive.txt");
	          PrintStream printer = new PrintStream(file);

        	  printer.println(model.serviceLevelMedio);     
        	  printer.println(model.totalCostMedio);
	          
	      } catch (IOException e) {
	          System.out.println("Errore: " + e);
	          System.exit(1);
	      }        
		  
	/*	  try {

	          FileWriter file = new FileWriter("sim_scrive.txt");
	          BufferedWriter writer = new BufferedWriter(file);

        	  writer.write("" + model.serviceLevelMedio);     
        	  writer.write("" + model.totalCostMedio);
	          
	      } catch (IOException e) {
	          System.out.println("Errore: " + e);
	          System.exit(1);
	      }   */

	  }
}
