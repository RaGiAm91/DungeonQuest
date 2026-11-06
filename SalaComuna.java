package ProjecteDungeonQuest;

public class SalaComuna extends Sala {

	public SalaComuna() {
		super();
		this.setExplorada(false);
	}

	@Override
	boolean intentarSortir(Personatge p) {
		return true;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
