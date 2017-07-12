package optNet.prova;

public class RigaExcelCAP {

	private String nome;
	
	private int coordinataX;
	
	private int coordinataY;
	
	public double CAPWeeklyDemand;

	public String getNome() {
		return nome;
	}

	public int getCoordinataX() {
		return coordinataX;
	}

	public int getCoordinataY() {
		return coordinataY;
	}

	public double getCAPWeeklyDemand() {
		return CAPWeeklyDemand;
	}
	
	public RigaExcelCAP(String nome, int coordinataX, int coordinataY, double CAPWeeklyDemand) {
		super();
		this.nome = nome;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
		this.CAPWeeklyDemand = CAPWeeklyDemand;
	}

	@Override
	public String toString() {
		return "RigaExcelCAP [nome=" + nome + ", coordinataX=" + coordinataX + ", coordinataY=" + coordinataY
				+ ", CAPWeeklyDemand=" + CAPWeeklyDemand + "]";
	}

}
