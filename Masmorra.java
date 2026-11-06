package ProjecteDungeonQuest;
import java.util.Random;
import java.util.Scanner;

public class Masmorra {

	/**
	 *  Arrays de tresors i monstres disponibles al joc
	 */
	private static final Tresor[] TRESORS = {
			new Tresor("Espasa d'or", 50, 3.5),
			new Tresor("Escut de plata", 30, 5.0),
			new Tresor("Poció màgica", 20, 0.5),
			new Tresor("Collaret de gemmes", 80, 0.2),
			new Tresor("Botes màgiques", 15, 1.0),
			new Tresor("Anell de poder", 100, 0.1),
			new Tresor("Mapa del tresor", 10, 0.3),
			new Tresor("Monedes d'or", 25, 1.5)
	};

	private static final Monstre[] MONSTRES_BASE = {
			new Monstre("Orco", 2),
			new Monstre("Esqueleto", 1),
			new Monstre("Goblin", 0),
			new Monstre("Troll", 3),
			new Monstre("Fantasma", 1)
	};

	protected static int FILES = 0;
	protected static int COLUMNES = 0;
	static int probabilitatMonstre;
	private static Sala[][] masmorra;
	private static Personatge personatge;
	private static Random dado = new Random();
	private static Scanner scanner = new Scanner(System.in);
	private static String causaMort = "";
	private static String dificultat = "";

	public static void iniciarJoc() {
		System.out.println("=== BENVINGUT A DUNGEONQUEST ===");
		System.out.print("Introdueix el nom del teu personatge: ");
		String nom = scanner.nextLine();
		System.out.print("Introdueix la dificultat (normal/dificil/" + nom + " must die): ");
		dificultat = scanner.nextLine();

		if (dificultat.equalsIgnoreCase("normal")) {
			FILES = 5;
			COLUMNES = 5;
			probabilitatMonstre = 30;
		} else if (dificultat.equalsIgnoreCase("dificil")) {
			FILES = 7;
			COLUMNES = 7;
			probabilitatMonstre = 45;
		} else if (dificultat.equalsIgnoreCase(nom + " must die")) {
			FILES = 10;
			COLUMNES = 10;
			probabilitatMonstre = 60;
		}
		else {
			System.out.println("Dificultat no reconeguda, s'utilitzarà normal per defecte.");
			dificultat = "normal";
			FILES = 5;
			COLUMNES = 5;
			probabilitatMonstre = 30;
		}		

		personatge = new Personatge(nom);
		personatge.getPosicio()[0] = 0;
		personatge.getPosicio()[1] = 0;

		generarMasmorra();

		System.out.println("Personatge creat!");
		System.out.println(personatge);
		System.out.println("Comença l'aventura!");

		bucleJoc();
	}

	private static void generarMasmorra() {
		masmorra = new Sala[FILES][COLUMNES];

		for (int i = 0; i < FILES; i++) {
			for (int j = 0; j < COLUMNES; j++) {
				masmorra[i][j] = crearSalaAleatoria();
				assignarContingutAleatori(masmorra[i][j]);
			}
		}

		/**
		 *  La sala inicial no té monstre (per donar temps al jugador)
		 */
		masmorra[0][0].setMonstre(null);
	}

	private static Sala crearSalaAleatoria() {
		/**
		 *  60% SalaComuna, 20% SalaPont, 20% SalaTeranyina
		 */
		int tirada = dado.nextInt(100);
		if (tirada < 60) {
			return new SalaComuna();
		} else if (tirada < 80) {
			return new SalaPont();
		} else {
			return new SalaTeranyina();
		}
	}

	private static void assignarContingutAleatori(Sala sala) {
		/**
		 *  40% de probabilitat de tenir tresor
		 */
		int probabilitat = dado.nextInt(100);
		if (probabilitat <= 40) {
			int indexTresor = dado.nextInt(TRESORS.length);
			Tresor tresorAleatori = TRESORS[indexTresor];
			sala.setTresor(tresorAleatori);
		}

		/**
		 *  30% o 45% o 60% (normal o dificil o nom de personate must die) de probabilitat de tenir monstre
		 */		
		probabilitat = dado.nextInt(100);
		if (probabilitat < probabilitatMonstre) {
			int indexMonstre = dado.nextInt(MONSTRES_BASE.length);
			Monstre base = MONSTRES_BASE[indexMonstre];
			Monstre nouMonstre = new Monstre(base.getNom(), base.getPenalitzacio());
			sala.setMonstre(nouMonstre);
		}
	}

	private static void bucleJoc() {
		while (personatge.estaViu() && !haAcabatElJoc()) {
			imprimirMasmorra();
			mostrarInfoSala();
			mostrarOpcions();
			processarOpcio();
		}
		imprimirMasmorra();
		mostrarResultat();
	}

	private static void imprimirMasmorra() {
		System.out.println("--- MASMORRA ---");
		int[] pos = personatge.getPosicio();

		for (int i = 0; i < FILES; i++) {
			for (int j = 0; j < COLUMNES; j++) {
				if (i == pos[0] && j == pos[1]) {
					System.out.print("[&]");
				} else if (masmorra[i][j].isExplorada()) {
					System.out.print("[*]");
				} else {
					System.out.print("[-]");
				}
			}
			System.out.println();
		}
		System.out.println("& = tu | * = explorada | - = no explorada");
		System.out.println("Nivell: " + personatge.getNivell() + " | Vida: " + personatge.getVida() + " | Experiència: " + personatge.getExperiencia()
		+ " | Dificultad: " + dificultat);
	}

	private static void mostrarInfoSala() {
		int[] pos = personatge.getPosicio();
		Sala salaActual = masmorra[pos[0]][pos[1]];

		System.out.println("--- SALA ACTUAL ---");
		if (salaActual instanceof SalaComuna) {
			System.out.println("Tipus: Sala Comuna");
		}
		else if (salaActual instanceof SalaPont) {
			System.out.println("Tipus: Sala Pont (necessites agilitat per sortir)");
		}
		else if (salaActual instanceof SalaTeranyina) {
			System.out.println("Tipus: Sala Teranyina (necessites força per sortir)");
		}

		if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
			System.out.println("Hi ha un monstre: " + salaActual.getMonstre());
		}
	}

	private static void mostrarOpcions() {
		int[] pos = personatge.getPosicio();
		Sala salaActual = masmorra[pos[0]][pos[1]];

		System.out.println("--- OPCIONS ---");

		if (!salaActual.isExplorada()) {
			System.out.println("1. Explorar sala");
		}

		System.out.println("2. Moure (N/S/E/O)");

		if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
			System.out.println("3. Atacar monstre");
		}

		System.out.print("Escull una opció: ");
	}

	private static void processarOpcio() {
		int[] pos = personatge.getPosicio();
		Sala salaActual = masmorra[pos[0]][pos[1]];
		String opcio = scanner.nextLine().trim();

		switch (opcio) {
		case "1":
			if (!salaActual.isExplorada()) {
				personatge.explorar(salaActual);
			} else {
				System.out.println("Aquesta sala ja ha estat explorada.");
			}
			break;

		case "2":
			accionMoure(salaActual);
			break;

		case "3":
			if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
				personatge.atacar(salaActual.getMonstre());
				if (!personatge.estaViu()) {
					causaMort = "monstre (" + salaActual.getMonstre().getNom() + ")";
				}
			} else {
				System.out.println("No hi ha cap monstre aquí.");
			}
			break;

		default:
			System.out.println("Opció no vàlida.");
		}
	}

	private static void accionMoure(Sala salaActual) {
		System.out.print("Direcció (N/S/E/O): ");
		String dir = scanner.nextLine().trim().toUpperCase();

		char direccio = dir.charAt(0);

		if (salaActual.intentarSortir(personatge)) {
			Monstre m = salaActual.getMonstre();
			if (m != null && m.estaViu()) {
				personatge.rebreDany(m.getPenalitzacio());
				System.out.println("Fuges! Reps " + m.getPenalitzacio() + " de dany.");
			}
			personatge.moure(direccio);
		} else {
			System.out.println("No has pogut sortir de la sala.");
		}
	}

	private static void aplicarPenalitzacioFugida(Sala sala) {
		Monstre m = sala.getMonstre();
		if (m != null && m.estaViu() && m.getPenalitzacio() > 0) {
			System.out.println("El monstre t'ataca per la fugida! -" + m.getPenalitzacio() + " vida");
			personatge.rebreDany(m.getPenalitzacio());
			if (!personatge.estaViu()) {
				causaMort = "penalització de fugida del monstre " + m.getNom();
			}
		}
	}

	private static int comptarSalesExplorades() {
		int count = 0;
		for (int i = 0; i < FILES; i++) {
			for (int j = 0; j < COLUMNES; j++) {
				if (masmorra[i][j].isExplorada()) {
					count++;
				}
			}
		}
		return count;
	}

	private static int comptarTresors() {
		int count = 0;
		for (Tresor t : personatge.getEquipament()) {
			if (t != null) {
				count++;
			}
		}
		return count;
	}

	private static int calcularOrTotal() {
		int total = 0;
		for (Tresor t : personatge.getEquipament()) {
			if (t != null) {
				total += t.getValor();
			}
		}
		return total;
	}

	private static boolean haAcabatElJoc() {
		int[] pos = personatge.getPosicio();
		/**
		 *  Ha sortit de la masmorra (posició fora dels límits)
		 */
		boolean foraLimits;
		if (pos[0] < 0 || pos[0] >= FILES || pos[1] < 0 || pos[1] >= COLUMNES) {
			foraLimits = true;
		} else {
			foraLimits = false;
		}
		return foraLimits || !personatge.estaViu();
	}

	private static void mostrarResultat() {
		int[] pos = personatge.getPosicio();
		boolean haGuanyat;
		if (pos[0] < 0 || pos[0] >= FILES || pos[1] < 0 || pos[1] >= COLUMNES) {
			haGuanyat = true;
		} else {
			haGuanyat = false;
		}

		int salesExplorades = comptarSalesExplorades();
		int totalSales = FILES * COLUMNES;
		double percentatge = (salesExplorades * 100.0) / totalSales;

		if (haGuanyat) {
			boolean totExplorat;
			if (salesExplorades == totalSales) {
				totExplorat = true;
			} else {
				totExplorat = false;
			}

			if (totExplorat) {
				System.out.println("|******************************|");
				System.out.println("|         TRUE ENDING!         |");
				System.out.println("|______________________________|");
				System.out.println("Has explorat tota la masmorra i has escapat!");
				System.out.println("Ets una llegenda!");
			} else {
				System.out.println("|******************************|");
				System.out.println("|           VICTÒRIA!          |");
				System.out.println("|______________________________|");
				System.out.println("Has escapat de la masmorra!");
			}
		} else {
			System.out.println("|******************************|");
			System.out.println("|          GAME OVER           |");
			System.out.println("|______________________________|");
			System.out.println("Causa de la mort: " + causaMort);
		}

		System.out.println("--- ESTADÍSTIQUES FINALS ---");
		System.out.println("Dificultad: " + dificultat);
		System.out.println("Nivell: " + personatge.getNivell());
		System.out.println("Experiència: " + personatge.getExperiencia());
		System.out.println("Vida restant: " + Math.max(personatge.getVida(), 0));
		System.out.println("Tresors recollits: " + comptarTresors());
		System.out.println("Or total: " + calcularOrTotal() + " monedes");
		System.out.println("Masmorra explorada: " + percentatge + "%");;
	}

	/**
	 *  Punt d'entrada
	 * @param args
	 */
	public static void main(String[] args) {
		iniciarJoc();
	}
}