package SSCampeonato;

import SSCircuito.Zona;

public class Ultrapassagem extends Acontecimento {

	private Zona zona;
	private Registo reg_Ultrapassou;
	private Registo reg_Ultrapassado;

	public Ultrapassagem(int volta, Zona zona, Registo reg_Ultrapassou, Registo reg_Ultrapassado)
	{
		super(volta);
		this.zona = zona;
		this.reg_Ultrapassou = reg_Ultrapassou;
		this.reg_Ultrapassado = reg_Ultrapassado;
	}

	public Zona getZona() {
		return zona;
	}

	public Registo getReg_Ultrapassou() {
		return reg_Ultrapassou;
	}

	public Registo getReg_Ultrapassado() {
		return reg_Ultrapassado;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\n");
		sb.append("O(A) Jogador(a) ");
		sb.append(reg_Ultrapassou.getNome_jogador());
		sb.append(" ultrapassou o(a) ");
		sb.append(reg_Ultrapassado.getNome_jogador());
		sb.append(".\n");
		return sb.toString();
	}
}