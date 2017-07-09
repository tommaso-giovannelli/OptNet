package optNet.connection;

public class RigaExcelPlant {

	private String nome;
	
	private int coordinataX;
	
	private int coordinataY;

	public String getNome() {
		return nome;
	}

	public int getCoordinataX() {
		return coordinataX;
	}

	public int getCoordinataY() {
		return coordinataY;
	}

	public RigaExcelPlant(String nome, int coordinataX, int coordinataY) {
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
