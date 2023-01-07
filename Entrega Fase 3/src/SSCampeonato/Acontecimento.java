package SSCampeonato;

import Datalayer.RegistoDAO;

public abstract class Acontecimento {

	private int volta;

	public Acontecimento( int volta)
	{
		this.volta = volta;
	}

	public int getVolta() {
		return volta;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Volta ");sb.append(volta);
		return sb.toString();
	}
}