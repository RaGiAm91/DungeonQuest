package ProjecteDungeonQuest;

public class Tresor {

	private String nom;
	private int valor;
	private double pes;
	
	public String getNom() {
		return nom;
	}
	public int getValor() {
		return valor;
	}
	public double getPes() {
		return pes;
	}
	
	public Tresor(String nom, int valor, double pes) {
		super();
		this.nom = nom;
		this.valor = valor;
		this.pes = pes;
	}
	
	@Override
	public String toString() {
		return "Tresor: " + this.nom + ", valor: " + this.valor;
	}
	
}
