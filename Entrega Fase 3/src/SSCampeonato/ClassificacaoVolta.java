package SSCampeonato;

import java.util.ArrayList;
import java.util.List;

import Datalayer.*;

public class ClassificacaoVolta extends Acontecimento {
	private List<Registo> classificacao;

	public ClassificacaoVolta(int volta, List<Registo> classificacao)
	{
		super(volta);
		this.classificacao = new ArrayList<>(classificacao);
	}

	public List<Registo> getClassificacao()
	{
		List<Registo> aux = new ArrayList<>(this.classificacao);
		return aux;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		float tempoDaFrente = 0;
		int i = 1;
		sb.append(super.toString());
		sb.append("\n");
		for (Registo registo : classificacao)
		{
			if (registo.getCarro().isDnf()) {
				sb.append(registo.getNome_jogador());
				sb.append(" abandonou a corrida \n");
			}
			else {
				sb.append(i);
				sb.append("º - ");
				sb.append(registo.getNome_jogador());
				sb.append(" Tempo: ");
				if(i==1) {
					tempoDaFrente = registo.getTempo();
					sb.append(tempoDaFrente);
				}
				else {
					sb.append("+");
					sb.append(registo.getTempo()-tempoDaFrente);
				}
				sb.append("\n");
				i++;
			}
		}
		return sb.toString();
	}
}