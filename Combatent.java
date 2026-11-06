package ProjecteDungeonQuest;

	interface Combatent {

	int calcularAtac();
	//numero = (int) (Math.random() * n) + 1;
	//importar Random
	//Donde n es hasta el número que quieres que llegue, + 1 para que nunca tengas un 0 y el cast a int porque Math#random arroja un valor de tipo double.
	void rebreDany (int quantitat);
	boolean estaViu();
	
}
