package optNet.connection;

public class RigaExcelDFT {

	private String nome;
	
	private double coordinataX;
	
	private double coordinataY;
	
	private double S;
	
	private double s_;
	
	private double EOQ;
	
	private double initialStock;

	public String getNome() {
		return nome;
	}

	public double getCoordinataX() {
		return coordinataX;
	}

	public double getCoordinataY() {
		return coordinataY;
	}

	public double getS() {
		return S;
	}

	public double getS_() {
		return s_;
	}

	public double getEOQ() {
		return EOQ;
	}

	public double getInitialStock() {
		return initialStock;
	}

	public RigaExcelDFT(String nome, double coordinataX, double coordinataY, double s, double s_, double eOQ,	double initialStock) {
		super();
		this.nome = nome;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
		S = s;
		this.s_ = s_;
		EOQ = eOQ;
		this.initialStock = initialStock;
	}

	@Override
	public String toString() {
		return "RigaExcelDFT [nome=" + nome + ", coordinataX=" + coordinataX + ", coordinataY=" + coordinataY + ", S="
				+ S + ", s_=" + s_ + ", EOQ=" + EOQ + ", initialStock=" + initialStock + "]";
	}
	
	
}
