package ProjecteDungeonQuest;

import java.util.Arrays;
import java.util.Random;

public class Personatge implements Combatent {

	Random dado = new Random();

	private String nom;
	private int vida;
	private int atac;
	private int experiencia = 0;
	private int agilitat;
	private int forsa;
	private int [] posicio = {0,0}; // posició a la masmorra (valor de filera i columna en què es troba el personatge)
	private Tresor [] equipament = new Tresor[this.forsa]; //serà un array de tresors de mida igual a la força del personatge
	private int nivell = 1;

	public Personatge(String nom) {
		this.nom = nom;
		this.vida = dado.nextInt(5, 20);
		this.atac = dado.nextInt(1, 4);
		this.experiencia = 0;
		this.agilitat = dado.nextInt(4, 11);
		this.forsa = dado.nextInt(4, 11);
		this.equipament = new Tresor[this.forsa];
	} 

	public String getNom() {
		return nom;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public int getVida() {
		return vida;
	}
	public int getAtac() {
		return atac;
	}
	public int getExperiencia() {
		return experiencia;
	}
	public int getAgilitat() {
		return agilitat;
	}
	public int getForsa() {
		return forsa;
	}
	public int getNivell() {
		return nivell;
	}
	public int[] getPosicio() {
		return posicio;
	}
	
	public void setPosicio(int[] posicio, int a, int b) {
		this.posicio = posicio;
	}
	public Tresor[] getEquipament() {
		return equipament;
	}
	public void setEquipament(Tresor[] equipament) {
		this.equipament = equipament;
	}

	public void atacar(Monstre m) {
		int danyo = this.calcularAtac();
		m.rebreDany(danyo);
		System.out.println("Has echo " + danyo + " de daño a " + m.getNom());
		if ( m.estaViu()) {
			int monstreDanyo = m.calcularAtac();
			System.out.println(m.getNom() + " te ha inflingido " + monstreDanyo + " de daño");
			rebreDany(monstreDanyo);	
		} else {
			experiencia += m.getValorExperiencia();
	        System.out.println("Has derrotado a " + m.getNom() + "! +" + m.getValorExperiencia() + " exp");	
	        pujarNivell();
		}
	}

	public void explorar(Sala altre) {
	    System.out.println("El personaje explora si hay algun tesoro");
	    altre.setExplorada(true);
	    if (altre.getTresor() != null) {
	        System.out.println("Se ha encontrado un " + altre.getTresor());
	        boolean afegit = false;
	        for (int i = 0; i < equipament.length; i++) {
	            if (equipament[i] == null && !afegit) {
	                equipament[i] = altre.getTresor();
	                afegit = true;
	            }
	        }
	        if (!afegit) {
	            System.out.println("No tens espai a l'equipament!");
	        }
	    } else {
	        System.out.println("No hay tesoro");
	    }
	}

	public void moure(char direccio) {
	    int[] p = getPosicio();
	    int ultimaFila = Masmorra.FILES - 1;
	    int ultimaCol = Masmorra.COLUMNES - 1;

	    switch (Character.toUpperCase(direccio)) {
	        case 'N':
	            // Cantonada superior esquerra (0,0) o superior dreta (0, ultimaCol)
	            if (p[0] == 0 && (p[1] == 0 || p[1] == ultimaCol)) {
	                posicio[0] = p[0] - 1;
	                System.out.println("Has salido de la sala");
	            } else if (p[0] > 0) {
	                posicio[0] = p[0] - 1;
	                System.out.println("Has salido de la sala");
	            } else {
	                System.out.println("No pots anar en aquesta direcció.");
	            }
	            break;
	        case 'S':
	            // Cantonada inferior esquerra (ultimaFila,0) o inferior dreta (ultimaFila, ultimaCol)
	            if (p[0] == ultimaFila && (p[1] == 0 || p[1] == ultimaCol)) {
	                posicio[0] = p[0] + 1;
	                System.out.println("Has salido de la sala");
	            } else if (p[0] < ultimaFila) {
	                posicio[0] = p[0] + 1;
	                System.out.println("Has salido de la sala");
	            } else {
	                System.out.println("No pots anar en aquesta direcció.");
	            }
	            break;
	        case 'E':
	            // Cantonada superior dreta (0, ultimaCol) o inferior dreta (ultimaFila, ultimaCol)
	            if (p[1] == ultimaCol && (p[0] == 0 || p[0] == ultimaFila)) {
	                posicio[1] = p[1] + 1;
	                System.out.println("Has salido de la sala");
	            } else if (p[1] < ultimaCol) {
	                posicio[1] = p[1] + 1;
	                System.out.println("Has salido de la sala");
	            } else {
	                System.out.println("No pots anar en aquesta direcció.");
	            }
	            break;
	        case 'O':
	            // Cantonada superior esquerra (0,0) o inferior esquerra (ultimaFila, 0)
	            if (p[1] == 0 && (p[0] == 0 || p[0] == ultimaFila)) {
	                posicio[1] = p[1] - 1;
	                System.out.println("Has salido de la sala");
	            } else if (p[1] > 0) {
	                posicio[1] = p[1] - 1;
	                System.out.println("Has salido de la sala");
	            } else {
	                System.out.println("No pots anar en aquesta direcció.");
	            }
	            break;
	        default:
	            System.out.println("Direcció no vàlida.");
	    }
	}

	@Override
	public int calcularAtac() {
		if (atac <= 1) return 1;
		return dado.nextInt(1, atac + 1);
	}

	@Override
	public void rebreDany(int quantitat) {
		vida = vida-quantitat;
	}

	@Override
	public boolean estaViu() {
		if(vida > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void pujarNivell() {
	    if (nivell >= 3) return;

	    int expNecessaria;
	    if (nivell == 1) {
	        expNecessaria = 10;
	    } else {
	        expNecessaria = 20;
	    }

	    if (experiencia >= expNecessaria) {
	        nivell++;
	        vida += 5;
	        atac += 1;
	        agilitat += 1;
	        forsa += 1;
	        int vidaMaxima = vida;
	        vida = vidaMaxima; // recuperar la vida al maximo.
	        System.out.println("=== LEVEL UP! Ara ets nivell " + nivell + " ===");
	        System.out.println("vida +5 | atac +1 | agilitat +1 | força +1");
	        System.out.println("Vida restaurada al màxim: " + vidaMaxima);
	    }
	}

	@Override
	public String toString() {
		return "Nom: " + nom + ", nivell: " + nivell + ", vida: " + vida + ", agilitat: " + agilitat + ", forsa: " + forsa + ", posicio: " + Arrays.toString(posicio)
		+ ", equipament: " + Arrays.toString(equipament);
		// Quan mostrem un personatge per consola, apareixerà el seu nom, vida, agilitat, força, posició dintre de la masmorra i tresors que té actualment al seu equipament.	
	}


}
