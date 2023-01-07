package SSCampeonato;

import SSCircuito.Zona;

public class Abandono extends Acontecimento {

	private Zona zona;
	private Registo registo;
	private String motivo;

	public Abandono(int volta, Zona zona, Registo registo, String motivo)
	{
		super(volta);
		this.zona = zona;
		this.registo = registo;
		this.motivo = motivo;
	}

	public Zona getZona()
	{
		return this.zona;
	}

	public Registo getRegisto()
	{
		return this.registo;
	}

	public String getMotivo()
	{
		return this.motivo;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\n");
		sb.append("O(A) Jogador(a) ");
		sb.append(registo.getNome_jogador());
		sb.append(" abandonou a corrida devido a ");
		sb.append(motivo);
		sb.append(".\n");
		return sb.toString();
	}
}