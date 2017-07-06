package optNet.connection;

public class RigaExcelCAP {

	private String nome;
	
	private int coordinataX;
	
	private int coordinataY;
	
	public String CAPWeeklyDemand;

	public String getNome() {
		return nome;
	}

	public int getCoordinataX() {
		return coordinataX;
	}

	public int getCoordinataY() {
		return coordinataY;
	}

	public String getCAPWeeklyDemand() {
		return CAPWeeklyDemand;
	}
	
	public RigaExcelCAP(String nome, int coordinataX, int coordinataY, String CAPWeeklyDemand) {
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
