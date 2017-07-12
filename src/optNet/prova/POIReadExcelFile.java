package optNet.prova;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class POIReadExcelFile {
	
	public List<RigaExcelCAP> listCAP;
	
	public List<RigaExcelDFT> listDFT;
	
	public List<RigaExcelDFL> listDFL;
	
	public List<RigaExcelPlant> listPlant;
	
	/**
	 * @return Lista che fa le veci della tabella Excel riguardante i CAP (campi: nome,coordinataX,coordinataY,CAPWeeklyDemand)
	 */
	public List<RigaExcelCAP> riempiListaCAP() {
		
		try {
			
			listCAP = new ArrayList<>();
			
			FileInputStream fileInputStream = new FileInputStream("Cap.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("Cap"); //oppure HSSFSheet worksheet=wb.getSheetAt(0);
			
			HSSFRow row; 
			//HSSFCell cell;

			Iterator rows = worksheet.rowIterator();
			rows.next(); //perchè nella prima riga c'è l'intestazione della tabella quindi la devo saltare
			
			while (rows.hasNext()) {
				
				row = (HSSFRow) rows.next();

				//Iterator cells = row.cellIterator();	
				//while (cells.hasNext()) {
					//cell=(HSSFCell) cells.next();
				
				HSSFCell cellB = row.getCell((short) 1);				
				String valB = cellB.getStringCellValue();
				
				HSSFCell cellC = row.getCell((short) 2);				
				double valC = cellC.getNumericCellValue();
				
				HSSFCell cellE = row.getCell((short) 4);				
				double valE = cellE.getNumericCellValue();
				
				HSSFCell cellI = row.getCell((short) 8);				
				double valI = cellI.getNumericCellValue();
				
				HSSFCell cellJ = row.getCell((short) 9);				
				double valJ = cellI.getNumericCellValue();
				
				HSSFCell cellK = row.getCell((short) 10);				
				double valK = cellI.getNumericCellValue();
				
				RigaExcelCAP riga = new RigaExcelCAP(valB,valC,valE,valI,valJ,valK);
				
				listCAP.add(riga);
				
			}
			
			/* 
			HSSFRow row1 = worksheet.getRow(0);
			HSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();
			HSSFCell cellB1 = row1.getCell((short) 1);
			String b1Val = cellB1.getStringCellValue();
			HSSFCell cellC1 = row1.getCell((short) 2);
			boolean c1Val = cellC1.getBooleanCellValue();
			HSSFCell cellD1 = row1.getCell((short) 3);

			System.out.println("A1: " + a1Val);
			System.out.println("B1: " + b1Val);
			System.out.println("C1: " + c1Val);
			System.out.println("D1: " + d1Val);
			*/
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return listCAP;
	}
	
	/**
	 * @return Lista che fa le veci della tabella Excel riguardante i DFT (campi: nome,coordinataX,coordinataY,S,s_,EOQ,initialStock)
	 */
	public List<RigaExcelDFT> riempiListaDFT() {
		
		try {
			
			listDFT = new ArrayList<>();
			
			FileInputStream fileInputStream = new FileInputStream("DFT.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("DFT"); //oppure HSSFSheet worksheet=wb.getSheetAt(0);
			
			HSSFRow row; 
			//HSSFCell cell;

			Iterator rows = worksheet.rowIterator();
			rows.next(); //perchè nella prima riga c'è l'intestazione della tabella quindi la devo saltare

			while (rows.hasNext()) {

				row = (HSSFRow) rows.next();

				//Iterator cells = row.cellIterator();	
				//while (cells.hasNext()) {
					//cell=(HSSFCell) cells.next();
				
				HSSFCell cellB = row.getCell((short) 1);				
				String valB = cellB.getStringCellValue();
				
				HSSFCell cellC = row.getCell((short) 2);				
				double valC = cellC.getNumericCellValue();
				
				HSSFCell cellE = row.getCell((short) 4);				
				double valE = cellE.getNumericCellValue();
				
				HSSFCell cellG = row.getCell((short) 6);				
				double valG = cellG.getNumericCellValue();
				
				HSSFCell cellH = row.getCell((short) 7);				
				double valH = cellH.getNumericCellValue();
				
				HSSFCell cellI = row.getCell((short) 8);				
				double valI = cellI.getNumericCellValue();
				
				HSSFCell cellJ = row.getCell((short) 9);				
				double valJ = cellJ.getNumericCellValue();
				
				RigaExcelDFT riga = new RigaExcelDFT(valB,valC,valE,valG,valH,valI,valJ);
				
				listDFT.add(riga);
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return listDFT;
	}
	
	/**
	 * @return Lista che fa le veci della tabella Excel riguardante i DFL (campi: nome,coordinataX,coordinataY,S,s_,EOQ,initialStock)
	 */
	public List<RigaExcelDFL> riempiListaDFL() {
		
		try {
			
			listDFL = new ArrayList<>();
			
			FileInputStream fileInputStream = new FileInputStream("DFL.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("DFL"); //oppure HSSFSheet worksheet=wb.getSheetAt(0);
			
			HSSFRow row; 
			//HSSFCell cell;

			Iterator rows = worksheet.rowIterator();
			rows.next(); //perchè nella prima riga c'è l'intestazione della tabella quindi la devo saltare
			
			while (rows.hasNext()) {
				
				row = (HSSFRow) rows.next();

				//Iterator cells = row.cellIterator();	
				//while (cells.hasNext()) {
					//cell=(HSSFCell) cells.next();
				
				HSSFCell cellB = row.getCell((short) 1);				
				String valB = cellB.getStringCellValue();
				
				HSSFCell cellC = row.getCell((short) 2);				
				double valC = cellC.getNumericCellValue();
				
				HSSFCell cellE = row.getCell((short) 4);				
				double valE = cellE.getNumericCellValue();
				
				HSSFCell cellG = row.getCell((short) 6);				
				double valG = cellG.getNumericCellValue();
				
				HSSFCell cellH = row.getCell((short) 7);				
				double valH = cellH.getNumericCellValue();
				
				HSSFCell cellI = row.getCell((short) 8);				
				double valI = cellI.getNumericCellValue();
				
				HSSFCell cellJ = row.getCell((short) 9);				
				double valJ = cellJ.getNumericCellValue();
				
				RigaExcelDFL riga = new RigaExcelDFL(valB,valC,valE,valG,valH,valI,valJ);
				
				listDFL.add(riga);
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return listDFL;
	}
	
	/**
	 * @return Lista che fa le veci della tabella Excel riguardante i Plant (campi: nome,coordinataX,coordinataY)
	 */
	public List<RigaExcelPlant> riempiListaPlant() {
		
		try {
			
			listPlant = new ArrayList<>();
			
			FileInputStream fileInputStream = new FileInputStream("Plant.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("Plant"); //oppure HSSFSheet worksheet=wb.getSheetAt(0);
			
			HSSFRow row; 
			//HSSFCell cell;

			Iterator rows = worksheet.rowIterator();
			rows.next(); //perchè nella prima riga c'è l'intestazione della tabella quindi la devo saltare
			
			while (rows.hasNext()) {
				
				row = (HSSFRow) rows.next();

				//Iterator cells = row.cellIterator();	
				//while (cells.hasNext()) {
					//cell=(HSSFCell) cells.next();
				
				HSSFCell cellB = row.getCell((short) 1);				
				String valB = cellB.getStringCellValue();
				
				HSSFCell cellC = row.getCell((short) 2);				
				double valC = cellC.getNumericCellValue();
				
				HSSFCell cellE = row.getCell((short) 4);				
				double valE = cellE.getNumericCellValue();
				
				RigaExcelPlant riga = new RigaExcelPlant(valB,valC,valE);
				
				listPlant.add(riga);
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return listPlant;
	}	
	
	
	public static void main(String[] args) {
		//List<RigaExcelCAP> lista;
		POIReadExcelFile prova = new POIReadExcelFile();
		//lista = prova.riempiListaCAP();
		prova.riempiListaCAP();
		prova.riempiListaDFL();
		prova.riempiListaDFT();
		prova.riempiListaPlant();
		//System.out.println(lista);
		System.out.println(prova.listCAP);
		System.out.println(prova.listDFL);
		System.out.println(prova.listDFT);
		System.out.println(prova.listPlant);
	}
	
	
}
