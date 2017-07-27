package optNet.prova;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderFile  {
	
	/** 
	 * @param model
	 * @return array di dimensione pari alla somma del numero di  potenziali DFT e DFL (12 + 248) che ha come elementi 0 (se il relativo impianto non è attivato) e 1 (se il relativo impianto è attivato)
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public int[] leggiFile() throws IOException, FileNotFoundException, Exception {
		
		BufferedReader reader = new BufferedReader(new FileReader("sim_legge.txt"));
		
		String riga;
		
		int[] rigaArray = new int[260];
		
		int i = 0;
		
		while(true) {
			
			  riga = reader.readLine();
			  
			  if( riga == null) {
			    break;
			  }
			  
			  if (i == (260)) {
				  throw new Exception("ATTENZIONE: il numero di righe è superiore al numero di DFT e DFL");
			  }
			  
			  rigaArray[i] = Integer.parseInt(riga);
			  i++;
		  
		}
		
		return rigaArray;
	}

}
