package SSCampeonato;

import java.util.*;
import Datalayer.*;
import RacingManagerLN.Jogador;
import RacingManagerLN.Piloto;
import SSCarro.Carro;
import SSCarro.Hibrido;
import SSCircuito.*;

public class Corrida {
	private int id;
	private int voltas;
	private boolean clima;
	private String circuito;
	private Set<Integer> registos;
	private List<Integer> resultado;
	private Map<Integer, Integer> rankHibrido;
	private Map<Integer, Integer> rankC1;
	private Map<Integer, Integer> rankC2;
	private Map<Integer, Integer> rankGT;
	private Map<Integer, Integer> rankSC;
	private List<Acontecimento> acontecimentos;
	private RegistoDAO registoDAO;
	private CircuitoDAO circuitoDAO;

	public Corrida(int id, int voltas, boolean clima, String circuito, Set<Integer> registos, List<Integer> resultado, Map<Integer,Integer> rankC1,
				   Map<Integer,Integer> rankC2, Map<Integer,Integer> rankGT, Map<Integer,Integer> rankSC, Map<Integer,Integer> rankHibrido)
	{
		this.voltas = voltas;
		this.id = id;
		this.clima = clima;
		this.circuito = circuito;
		this.registos = new HashSet<>(registos);
		this.resultado = new ArrayList<>(resultado);
		this.rankC1 = new LinkedHashMap<>(rankC1);
		this.rankC2 = new LinkedHashMap<>(rankC2);
		this.rankGT = new LinkedHashMap<>(rankGT);
		this.rankSC = new LinkedHashMap<>(rankSC);
		this.rankHibrido = new LinkedHashMap<>(rankHibrido);
		this.acontecimentos = new ArrayList<>();
		this.registoDAO = RegistoDAO.getInstance();
		this.circuitoDAO = CircuitoDAO.getInstance();
	}

	public int getId() {
		return id;
	}

	public int getVoltas() {
		return voltas;
	}

	public boolean getClima() {
		return clima;
	}

	public String getCircuito() {
		return circuito;
	}

	public Circuito getCircuitoTotal(){return circuitoDAO.get(circuito);}

	public Map<Integer,Integer> getRankHibrido(){return this.rankHibrido;}

	public Map<Integer,Integer> getRankC1(){return this.rankC1;}

	public Map<Integer,Integer> getRankC2(){return this.rankC2;}

	public Map<Integer,Integer> getRankSC(){return this.rankSC;}

	public Map<Integer,Integer> getRankGT(){return this.rankGT;}

	public void setId(int id) {
		this.id = id;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

	public void setClima(boolean clima) {
		this.clima = clima;
	}

	/**
	 * 
	 * @param versao
	 */

	public void simulaCorrida(String versao)
	{
		int i;
		float t;
		boolean despiste;
		Registo reg_ant;
		List<Registo> aux = new ArrayList<>();
		Set<Registo> abandonos = new LinkedHashSet<>();

		Circuito c = this.circuitoDAO.get(this.circuito);
		List<Zona> zonas = c.getZonas();

		for(Integer id_registo : this.registos) {
			aux.add(registoDAO.get(id_registo));
		}

		int num_zonas = zonas.size();
		for(i=1; i<voltas+1; i++)
		{
			for(Zona z:zonas)
			{
				String GDU = z.getGDU();
				Set<Integer> abandonos_zona = new HashSet<>();
				for(int j=0; j<aux.size(); j++)
				{
					despiste = false;
					Registo r = aux.get(j);
					Carro carro_r = r.getCarro();
					Piloto piloto_r = r.getPiloto();
					if(!abandonos.contains(r))
					{
						if(carro_r.DNF(i, this.voltas, piloto_r, num_zonas))
						{
							abandonos.add(r);
							abandonos_zona.add(j);
							Acontecimento a = new Abandono(i, z.clone(), r.clone(), "Avaria Mecânica");
							acontecimentos.add(a);
						}
						else if(piloto_r.acidente(i, this.voltas, this.clima))
						{
							abandonos.add(r);
							abandonos_zona.add(j);
							Acontecimento a = new Abandono(i, z.clone(), r.clone(), "Acidente");
							acontecimentos.add(a);
						}
						else
						{
							despiste = piloto_r.despiste(i, this.voltas, this.clima);
							if (despiste)
							{
								Acontecimento a = new Despiste(i, z.clone(), r.clone());
								acontecimentos.add(a);
							}
							if (j>0) {
								reg_ant = aux.get(j - 1);
							}
							else {
								reg_ant = null;
							}
							if (versao.equals("Base"))
							{
								if (despiste)
								{
									Registo reg_next = aux.get(j+1);
									Corrida.swap_cars(aux, j+1);
									Acontecimento a = new Ultrapassagem(i, z.clone(), reg_next.clone(), r.clone());
								}
								else if (reg_ant != null && r.ultrapassa(reg_ant, GDU, this.clima))
								{
									Corrida.swap_cars(aux, j);
									Acontecimento a = new Ultrapassagem(i, z.clone(), r.clone(), reg_ant.clone());
									acontecimentos.add(a);
								}
							}
							else
							{
								if (despiste) {
									t = 2 * r.tempoProximaZona(reg_ant, z, i, this.clima, despiste);
								}
								else {
									t = r.tempoProximaZona(reg_ant, z, i, this.clima, despiste);
								}
								r.setTempo_antes(r.getTempo());
								r.setTempo(t);
								if (reg_ant != null && r.getTempo() < reg_ant.getTempo())
								{
									Acontecimento a = new Ultrapassagem( i, z.clone(), r.clone(), reg_ant.clone());
									acontecimentos.add(a);
								}
							}
						}
					}
				}
				for (int ind : abandonos_zona) {
					aux.remove(ind);
				}
				if(versao.equals("Premium")) {
					Collections.sort(aux);
				}
			}

			if (aux.size() > 0)
				System.out.println("Tempo 1º = " + aux.get(0).getTempo());
			if (aux.size() > 1)
				System.out.println("Tempo 2º = " + aux.get(1).getTempo());

			List<Registo> aux_clone = new ArrayList<>();
			for (Registo reg : aux)
			{
				aux_clone.add(reg.clone());
			}
			Acontecimento a = new ClassificacaoVolta(i,aux_clone);
			acontecimentos.add(a);

		}

		int pontos;
		int pos = 1, posC1 = 1, posC2 = 1, posGT = 1, posSC = 1, posHibrido = 1;
		for(Registo r : aux)
		{
			if(!abandonos.contains(r))
			{
				Carro carro = r.getCarro();
				if (carro instanceof Hibrido) {
					pontos = pontosClasse("hibrido");
					rankHibrido.put(r.getId(),pontos);
					registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), pos, posHibrido, pontos);
					posHibrido++;
				}
				else {
					String classe = carro.getCategoria();
					pontos = pontosClasse(classe);
					if(classe.equals("C1")) {
						rankC1.put(r.getId(), pontos);
						registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), pos, posC1, pontos);
						posC1++;
					}
					else if (classe.equals("C2")) {
						rankC2.put(r.getId(), pontos);
						registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), pos, posC2, pontos);
						posC2++;
					}
					else if (classe.equals("GT")) {
						rankGT.put(r.getId(), pontos);
						registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), pos, posGT, pontos);
						posGT++;
					}
					else if (classe.equals("SC")) {
						rankSC.put(r.getId(), pontos);
						registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), pos, posSC, pontos);
						posSC++;
					}
				}
				resultado.add(r.getId());
				pos++;
			}
		}

		List<Registo> aux_abandonos = abandonos.stream().toList();
		for (i=aux_abandonos.size()-1; i>=0; i--)
		{
			Registo r = aux_abandonos.get(i);
			Carro carro = r.getCarro();

			if (carro instanceof Hibrido) {
				rankHibrido.put(r.getId(), 0);
				registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), 0, 0, 0);
				posHibrido++;
			}
			else {
				String classe = carro.getCategoria();
				if(classe.equals("C1")) {
					rankC1.put(r.getId(), 0);
					registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), 0, 0, 0);
					posC1++;
				}
				else if (classe.equals("C2")) {
					rankC2.put(r.getId(), 0);
					registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), 0, 0, 0);
					posC2++;
				}
				else if (classe.equals("GT")) {
					rankGT.put(r.getId(), 0);
					registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), 0, 0, 0);
					posGT++;
				}
				else if (classe.equals("SC")) {
					rankSC.put(r.getId(),0);
					registoDAO.setTempoPosicaoPontuacaoCarroCorrida(r.getId(), this.id, r.getTempo(), 0, 0, 0);
					posSC++;
				}
			}
			resultado.add(r.getId());
			pos++;
		}
	}

	public int pontosClasse(String classe) {
		int pontos=0;
		int carros_classificados=0;
		if (classe.equals("hibrido")){
			carros_classificados = rankHibrido.size();
		}
		else if(classe.equals("C1"))
			carros_classificados = rankC1.size();
		else if (classe.equals("C2"))
			carros_classificados = rankC2.size();
		else if (classe.equals("GT"))
			carros_classificados = rankGT.size();
		else if (classe.equals("SC")) {
			carros_classificados = rankSC.size();
		}
		switch (carros_classificados) {
			case 0:
				pontos = 12;
				break;
			case 1:
				pontos = 10;
				break;
			case 2:
				pontos = 8;
				break;
			case 3:
				pontos = 7;
				break;
			case 4:
				pontos = 6;
				break;
			case 5:
				pontos = 5;
				break;
			case 6:
				pontos = 4;
				break;
			case 7:
				pontos = 3;
				break;
			case 8:
				pontos = 2;
				break;
			case 9:
				pontos = 1;
				break;
			default:
				pontos = 0;
				break;
		}
		return pontos;
	}

	public static void swap_cars(List<Registo> registos, int i)
	{
		if (i > 0) {
			Registo reg_atual = registos.get(i);
			Registo reg_ant = registos.get(i-1);

			registos.add(i, reg_ant);
			registos.add(i-1, reg_atual);
		}
	}

	public List<String> getAcontecimentos()
	{
		List<String> res = new ArrayList<>();
		for (Acontecimento a : this.acontecimentos)
		{
			res.add(a.toString());
		}
		return res;
	}

	public String printResultado() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		sb.append("\n||||| ");sb.append(circuitoDAO.get(this.circuito).getNome());sb.append(" |||||");
		sb.append("\n||||| ");sb.append("Voltas: ");sb.append(this.voltas);sb.append(" |||||");
		sb.append("\n||||| ");sb.append("Distancia: ");sb.append(circuitoDAO.get(this.circuito).getComprimento());sb.append(" m | ");
		sb.append("Condição meteorológica: ");
		if(this.clima)
		{
			sb.append("Sol");
		}
		else
		{
			sb.append("Chuva");;
		}
		i=1;
		if(rankHibrido.size()!=0) {
			sb.append("\n\n||||| Classificações da categoria Hibridos |||||");
		}
		for(Integer id_registo : rankHibrido.keySet())
		{
			Registo registo = registoDAO.get(id_registo);
			sb.append("\n");
			sb.append(i);sb.append("º: ");
			sb.append(registo.getNome_jogador());sb.append("\t");
			sb.append("Tempo: ");
			sb.append(registo.getTempo());
			Carro carro = registo.getCarro();
			sb.append("\t Carro: "); sb.append(carro.getMarca()); sb.append(" ");
			sb.append(carro.getModelo());
			sb.append("\t Pontos: ");sb.append(rankHibrido.get(id_registo));sb.append(" ");
			i++;
		}
		i=1;
		if(rankC1.size()!=0) {
			sb.append("\n\n||||| Classificações da categoria C1 |||||");
		}
		for(Integer id_registo : rankC1.keySet())
		{
			Registo registo = registoDAO.get(id_registo);
			sb.append("\n");
			sb.append(i);sb.append("º: ");
			sb.append(registo.getNome_jogador());sb.append("\t");
			sb.append("Tempo: ");
			sb.append(registo.getTempo());
			Carro carro = registo.getCarro();
			sb.append("\t Carro: "); sb.append(carro.getMarca()); sb.append(" ");
			sb.append(carro.getModelo());
			sb.append("\t Pontos: ");sb.append(rankC1.get(id_registo));sb.append(" ");
			i++;
		}
		i=1;
		if(rankC2.size()!=0) {
			sb.append("\n\n||||| Classificações da categoria C2 |||||");
		}
		for(Integer id_registo : rankC2.keySet())
		{
			Registo registo = registoDAO.get(id_registo);
			sb.append("\n");
			sb.append(i);sb.append("º: ");
			sb.append(registo.getNome_jogador());sb.append("\t");
			sb.append("Tempo: ");
			sb.append(registo.getTempo());
			Carro carro = registo.getCarro();
			sb.append("\t Carro: "); sb.append(carro.getMarca()); sb.append(" ");
			sb.append(carro.getModelo());
			sb.append("\t Pontos: ");sb.append(rankC2.get(id_registo));sb.append(" ");
			i++;
		}
		i=1;
		if(rankSC.size()!=0) {
			sb.append("\n\n||||| Classificações da categoria SC |||||");
		}
		for(Integer id_registo : rankSC.keySet())
		{
			Registo registo = registoDAO.get(id_registo);
			sb.append("\n");
			sb.append(i);sb.append("º: ");
			sb.append(registo.getNome_jogador());sb.append("\t");
			sb.append("Tempo: ");
			sb.append(registo.getTempo());
			Carro carro = registo.getCarro();
			sb.append("\t Carro: "); sb.append(carro.getMarca()); sb.append(" ");
			sb.append(carro.getModelo());
			sb.append("\t Pontos: ");sb.append(rankSC.get(id_registo));sb.append(" ");
		}
		i=1;
		if(rankGT.size()!=0) {
			sb.append("\n\n||||| Classificações da categoria GT |||||");
		}
		for(Integer id_registo : rankGT.keySet())
		{
			Registo registo = registoDAO.get(id_registo);
			sb.append("\n");
			sb.append(i);sb.append("º: ");
			sb.append(registo.getNome_jogador());sb.append("\t");
			sb.append("Tempo: ");
			sb.append(registo.getTempo());
			Carro carro = registo.getCarro();
			sb.append("\t Carro: "); sb.append(carro.getMarca()); sb.append(" ");
			sb.append(carro.getModelo());
			sb.append("\t Pontos: ");sb.append(rankGT.get(id_registo));sb.append(" ");
		}

		return sb.toString();
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nA Corrida vai se realizar no ");
		sb.append("Circuito "); sb.append(circuito);
		sb.append('\n');
		sb.append("Condição meteorológica: ");
		if(this.clima)
		{
			sb.append("Sol");
		}
		else
		{
			sb.append("Chuva");;
		}
		System.out.println("\n");
		return sb.toString();
	}
}