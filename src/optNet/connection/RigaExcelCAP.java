package optNet.connection;

public class RigaExcelCAP {

	private String nome;
	
	private double coordinataX;
	
	private double coordinataY;
	
	public double CAPWeeklyDemandMin; //serve per la distribuzione triangolare
	
	public double CAPWeeklyDemandMedio; //serve per la distribuzione triangolare
	
	public double CAPWeeklyDemandMax; //serve per la distribuzione triangolare

	public String getNome() {
		return nome;
	}

	public double getCoordinataX() {
		return coordinataX;
	}

	public double getCoordinataY() {
		return coordinataY;
	}

	public double getCAPWeeklyDemandMin() {
		return CAPWeeklyDemandMin;
	}
	
	public double getCAPWeeklyDemandMedio() {
		return CAPWeeklyDemandMedio;
	}
	
	public double getCAPWeeklyDemandMax() {
		return CAPWeeklyDemandMax;
	}
	
	public RigaExcelCAP(String nome, double coordinataX, double coordinataY, double CAPWeeklyDemandMin, double CAPWeeklyDemandMedio, double CAPWeeklyDemandMax) {
		super();
		this.nome = nome;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
		this.CAPWeeklyDemandMin = CAPWeeklyDemandMin;
		this.CAPWeeklyDemandMedio = CAPWeeklyDemandMedio;
		this.CAPWeeklyDemandMax = CAPWeeklyDemandMax;
	}

	@Override
	public String toString() {
		return "RigaExcelCAP [nome=" + nome + ", coordinataX=" + coordinataX + ", coordinataY=" + coordinataY
				+ ", CAPWeeklyDemandMin=" + CAPWeeklyDemandMin + ", CAPWeeklyDemandMedio=" + CAPWeeklyDemandMedio
				+ ", CAPWeeklyDemandMax=" + CAPWeeklyDemandMax + "]";
	}


}
