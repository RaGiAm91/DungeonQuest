package ProjecteDungeonQuest;

public class SalaTeranyina extends Sala {

	public SalaTeranyina() {
		super();
		this.setExplorada(false);
	}

	@Override
	boolean intentarSortir(Personatge p) {
		System.out.println("Intentando salir...");
		int a;
		int b = p.getForsa();

		a = dado.nextInt(1, 12);
		System.out.println("Has sacado un " + a);

		if( a <= b) {
			return true;
		} else {
			System.out.println("Te has tropezado con telaraña y no puedes salir");
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
