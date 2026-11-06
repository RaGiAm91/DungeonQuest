package ProjecteDungeonQuest;

import java.util.Random;

public class Monstre implements Combatent {
	
	Random dado = new Random();

	private String nom;
	private int vida;
	private int penalitzacio;
	private int valorExperiencia;
	
	public String getNom() {
		return nom;
	}
	public int getVida() {
		return vida;
	}
	public int getPenalitzacio() {
		return penalitzacio;
	}
	public int getValorExperiencia() {
		return valorExperiencia;
	}
	
	public int calcularAtac() {
		if (vida <= 1) return 1;
		return dado.nextInt(1, vida +1);
	}
	
	public void rebreDany(int quantitat) {
		vida = vida-quantitat;
	}
	
	public boolean estaViu() {
		if(vida > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Monstre(String nom, int penalitzacio) {
		super();
		this.nom = nom;
		this.vida = dado.nextInt(4, 7);
		this.penalitzacio = penalitzacio;
		this.valorExperiencia = this.vida+2;
	}

	@Override
	public String toString() {
	    return "Monstre: " + nom + ", vida: " + vida;
	}
}
	
