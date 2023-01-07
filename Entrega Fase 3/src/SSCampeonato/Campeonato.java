package SSCampeonato;

import java.util.*;
import Datalayer.*;
import RacingManagerLN.Jogador;
import RacingManagerLN.Piloto;
import SSCarro.Carro;
import SSCircuito.Circuito;

public class Campeonato {
	private String nome;
	private String versao;
	private int corrida;
	private List<Integer> corridas;
	private Set<Integer> registos;
	private List<Integer> rankC1;
	private List<Integer> rankC2;
	private List<Integer> rankGT;
	private List<Integer> rankSC;
	private List<Integer> rankHibrido;
	private CorridaDAO corridaDAO;
	private RegistoDAO registoDAO;

	public Campeonato(String nome, String versao, int corrida, List<Integer> corridas, Set<Integer> registos,
					  List<Integer> rankC1, List<Integer> rankC2, List<Integer> rankGT, List<Integer> rankSC, List<Integer> rankHibrido)
	{
		this.nome = nome;
		this.versao = versao;
		this.corrida = corrida;
		this.registos = new HashSet<>(registos);
		this.corridas = new ArrayList<>(corridas);
		this.rankC1 = new ArrayList<>(rankC1);
		this.rankC2 = new ArrayList<>(rankC2);
		this.rankGT = new ArrayList<>(rankGT);
		this.rankSC = new ArrayList<>(rankSC);
		this.rankHibrido = new ArrayList<>(rankHibrido);
		this.corridaDAO = CorridaDAO.getInstance();
		this.registoDAO = RegistoDAO.getInstance();
	}

	public String getNome()
	{
		return this.nome;
	}

	public String getVersao()
	{
		return this.versao;
	}

	public int getCorrida()
	{
		return this.corrida;
	}

	public void advanceToNextRace()
	{
		this.corrida++;
	}


	@Override
	public String toString()
	{
		return "Nome: " + this.nome;
	}


	public Corrida getProxCorrida() {
		Corrida c = corridaDAO.get(this.corridas.get(this.corrida));
		return c;
	}

	public boolean afinacaoValida(int id_registo) {
		int size = this.corridas.size();
		Registo registo = registoDAO.get(id_registo);
		return registo.afinacaoValida(size);
	}

	public void setCarroCampeonato(int id_registo, float PAC, String modo_motor, String pneus) {
		if (PAC == -1) {
			Registo r = registoDAO.get(id_registo);
			PAC = r.getCarro().getPAC();
		}
		corridaDAO.setAfincacCarroCorrida(id_registo, this.corridas.get(this.corrida), PAC, modo_motor, pneus);
	}

	public Registo addRegisto(Jogador jogador, Carro carro, Piloto piloto){
		Registo registo = new Registo(-1,0,0,0, carro.getId(), jogador.getNome(), piloto.getNome());
		registoDAO.putNew(registo,this.nome);
		return registo;
	}

	/**
	 *
	 * @param id_registo
	 */
	public int recolhePontos(int id_registo) {
		return registoDAO.get(id_registo).getPontuacao();
	}

	public String printResultado(){
		return corridaDAO.get(this.corridas.get(this.corrida)).printResultado();
	}

	public List<String> simulaProxCorrida()
	{
		List res = new ArrayList<>();
		Corrida corrida = corridaDAO.get(this.corridas.get(this.corrida));
		corrida.simulaCorrida(this.versao);
		res=corrida.getAcontecimentos();
		res.add(corrida.printResultado());
		Map<Integer,Integer> rh = corrida.getRankHibrido();
		this.registaPontos(rh);
		Map<Integer,Integer> r1 = corrida.getRankC1();
		this.registaPontos(r1);
		Map<Integer,Integer> r2 = corrida.getRankC2();
		this.registaPontos(r2);
		Map<Integer,Integer> rsc = corrida.getRankSC();
		this.registaPontos(rsc);
		Map<Integer,Integer> rgt = corrida.getRankGT();
		this.registaPontos(rgt);
		res.add(this.printClassificaçãoCampeonato());
		return res;
	}

	public boolean maisCorridas() {
		if (this.corrida < this.corridas.size()) return true;
		return false;
	}

	public void registaPontos(Map<Integer,Integer> rank){
		for(Integer id_registo : rank.keySet()) {
			Registo registo = registoDAO.get(id_registo);
			int pontuacao = registo.getPontuacao() + rank.get(id_registo);
			registoDAO.setPontuacaoRegisto(id_registo, pontuacao);
		}
	}

	public List<Circuito> getCircuitos(){
		List<Circuito> circuitos = new ArrayList<>();
		for(Integer id_corrida : corridas){
			circuitos.add(corridaDAO.get(id_corrida).getCircuitoTotal());
		}
		return circuitos;
	}

	public String printClassificaçãoCampeonato() {
		int i;
		Registo registo;
		StringBuilder sb = new StringBuilder();
		Comparator<Registo> comparador_Pontuacao_Registo = (p1, p2) -> p2.getPontuacao() - p1.getPontuacao();

		TreeSet<Registo> ranks;

		sb.append("\n\n|||||  Classificação do campeonato |||||");

		if(rankHibrido.size() > 0)
		{
			ranks = new TreeSet<>(comparador_Pontuacao_Registo);
			for (Integer id_registo : rankHibrido) {
				registo = registoDAO.get(id_registo);
				ranks.add(registo);
			}

			i = 1;
			sb.append("\n\n||||| Classificação dos carros Híbridos |||||");
			for (Registo r : ranks) {
				sb.append("\n");
				sb.append(i);
				sb.append("º: ");
				sb.append(r.getNome_jogador());
				sb.append("\t Pontos: ");
				sb.append(r.getPontuacao());
				sb.append(" ");
				i++;
			}
		}

		if (rankC1.size() > 0)
		{
			ranks = new TreeSet<>(comparador_Pontuacao_Registo);
			for (Integer id_registo : rankC1) {
				registo = registoDAO.get(id_registo);
				ranks.add(registo);
			}

			i = 1;
			sb.append("\n\n||||| Classificações da categoria C1 |||||");
			for (Registo r : ranks) {
				sb.append("\n");
				sb.append(i);
				sb.append("º: ");
				sb.append(r.getNome_jogador());
				sb.append("\t Pontos: ");
				sb.append(r.getPontuacao());
				sb.append(" ");
				i++;
			}
		}

		if (rankC2.size() > 0)
		{
			ranks = new TreeSet<>(comparador_Pontuacao_Registo);
			for (Integer id_registo : rankC2) {
				registo = registoDAO.get(id_registo);
				ranks.add(registo);
			}

			i = 1;
			sb.append("\n\n||||| Classificações da categoria C2 |||||");
			for (Registo r : ranks) {
				sb.append("\n");
				sb.append(i);
				sb.append("º: ");
				sb.append(r.getNome_jogador());
				sb.append("\t Pontos: ");
				sb.append(r.getPontuacao());
				sb.append(" ");
				i++;
			}
		}

		if (rankGT.size() > 0) {
			ranks = new TreeSet<>(comparador_Pontuacao_Registo);
			for (Integer id_registo : rankGT) {
				registo = registoDAO.get(id_registo);
				ranks.add(registo);
			}

			i = 1;
			sb.append("\n\n|||||  Classificações da categoria GT |||||");
			for (Registo r : ranks) {
				sb.append("\n");
				sb.append(i);
				sb.append("º: ");
				sb.append(r.getNome_jogador());
				sb.append("\t Pontos: ");
				sb.append(r.getPontuacao());
				sb.append(" ");
				i++;
			}
		}

		if (rankSC.size() > 0) {
			ranks = new TreeSet<>(comparador_Pontuacao_Registo);
			for (Integer id_registo : rankSC) {
				registo = registoDAO.get(id_registo);
				ranks.add(registo);
			}

			i = 1;
			sb.append("\n\n|||||  Classificações da categoria SC |||||");
			for (Registo r : ranks) {
				sb.append("\n");
				sb.append(i);
				sb.append("º: ");
				sb.append(r.getNome_jogador());
				sb.append("\t Pontos: ");
				sb.append(r.getPontuacao());
				sb.append(" ");
				i++;
			}
		}

		return sb.toString();
	}

}