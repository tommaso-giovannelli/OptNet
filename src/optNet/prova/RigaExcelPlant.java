package optNet.prova;

public class RigaExcelPlant {

	private String nome;
	
	private double coordinataX;
	
	private double coordinataY;

	public String getNome() {
		return nome;
	}

	public double getCoordinataX() {
		return coordinataX;
	}

	public double getCoordinataY() {
		return coordinataY;
	}

	public RigaExcelPlant(String nome, double coordinataX, double coordinataY) {
		super();
		this.nome = nome;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
	}

	@Override
	public String toString() {
		return "RigaExcelPlant [nome=" + nome + ", coordinataX=" + coordinataX + ", coordinataY=" + coordinataY + "]";
	}
	
	
}
