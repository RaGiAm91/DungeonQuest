package ProjecteDungeonQuest;

public class SalaPont extends Sala {

	public SalaPont() {
		super();
		this.setExplorada(false);
	}

	@Override
	boolean intentarSortir(Personatge p) {
		System.out.println("Intentando salir...");
		int a;
		int b = p.getAgilitat();

		a = dado.nextInt(1, 12);
		System.out.println("Has sacado un " + a);

		if( a <= b) {
			return true;
		} else {
			System.out.println("Te has tropezado cayendote y haciendote daño{-1 de vida}");
			p.setVida(p.getVida()-1);
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
