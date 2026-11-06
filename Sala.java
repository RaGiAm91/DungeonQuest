package ProjecteDungeonQuest;

import java.util.Random;

public abstract class Sala {

	private Tresor tresor;
	private Monstre monstre;
	private boolean explorada;
	
	public Sala() {
		super();
		this.tresor = tresor;
		this.monstre = monstre;
		this.explorada = false;
	}

	public Tresor getTresor() {
		return tresor;
	}

	public void setTresor(Tresor tresor) {
		this.tresor = tresor;
	}

	public Monstre getMonstre() {
		return monstre;
	}

	public void setMonstre(Monstre monstre) {
		this.monstre = monstre;
	}

	public boolean isExplorada() {
		return explorada;
	}

	public void setExplorada(boolean explorada) {
		this.explorada = explorada;
	}

	@Override
	public String toString() {
		if(tresor != null) {
			String tesoro = "No hay tesoro en la sala";
			return tesoro;
		} else {	
			return tresor.toString();
		}
	}

	Random dado = new Random();

	abstract boolean intentarSortir(Personatge p);
}
