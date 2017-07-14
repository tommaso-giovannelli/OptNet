package optNet.prova;

public class RigaExcelPlant {

	private String nome;
	
	private double coordinataX;
	
	private double coordinataY;
	
	private double percentDFT;
	
	private double kmCost;

	public String getNome() {
		return nome;
	}

	public double getCoordinataX() {
		return coordinataX;
	}

	public double getCoordinataY() {
		return coordinataY;
	}

	public double getPercentDFT() {
		return percentDFT;
	}

	public double getKmCost() {
		return kmCost;
	}

	public RigaExcelPlant(String nome, double coordinataX, double coordinataY, double percentDFT, double kmCost) {
		super();
		this.nome = nome;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
		this.percentDFT = percentDFT;
		this.kmCost = kmCost;
	}

	@Override
	public String toString() {
		return "RigaExcelPlant [nome=" + nome + ", coordinataX=" + coordinataX + ", coordinataY=" + coordinataY + "]";
	}
	
	
}
