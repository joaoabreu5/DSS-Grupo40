package RacingManagerLN;

import java.util.*;
import Datalayer.*;
import SSCampeonato.*;
import SSCircuito.*;
import SSCarro.*;

public class RacingManagerFacade implements IRacingManager {

	private CarroFacade carros;
	private CampeonatoFacade campeonatos;
	private CircuitoFacade circuitos;
	private PilotoDAO pilotoDAO;
	private JogadorDAO jogadorDAO;
	private Set<Integer> pilotos;
	private Set<Integer> jogadores;

	public RacingManagerFacade()
	{
		this.pilotoDAO = PilotoDAO.getInstance();
		this.jogadorDAO = JogadorDAO.getInstance();
		this.campeonatos = new CampeonatoFacade();
		this.circuitos = new CircuitoFacade();
		this.carros = new CarroFacade();
	}

	public List<Piloto> getPilotos() {
		List<Piloto> res = (List<Piloto>) pilotoDAO.values();
		return res;
	}

	public List<Carro> getCarros() {
		return carros.getCarros();
	}

	public List<Campeonato> getCampeonatos() {
		return campeonatos.getCampeonatos();
	}

	public List<Circuito> getCircuitos() {
		return circuitos.getCircuitos();
	}

	public List<Circuito> getCircuitosCampeonato(){
		return campeonatos.getCircuitos();
	}

	public void campeonatoEscolhido(String campeonato)
	{
		campeonatos.setCampeonato(campeonato);
	}

	/**
	 * 
	 * @param nomeJogador
	 * @param nomeCampeonato
	 * @param id_carro
	 * @param piloto
	 */
	public Registo setJogadorCampeonato(String nomeJogador, String nomeCampeonato, int id_carro, String piloto) {
		Carro carro = carros.getCarro(id_carro);
		Piloto piloto1 = pilotoDAO.get(piloto);
		Jogador jogador = jogadorDAO.get(nomeJogador);
		if (jogador == null) {
			jogador = new Jogador(nomeJogador, " ", 0, true, "Base");
			jogadorDAO.put(nomeJogador, jogador);
		}
		return campeonatos.setJogadorCampeonato(jogador, nomeCampeonato, carro, piloto1);
	}

	/**
	 * 
	 * @param id_carro
	 */
	public boolean categoriaC1ouC2(int id_carro) {
		return carros.categoriaC1ouC2(id_carro);
	}

	/**
	 * 
	 * @param id_carro
	 * @param pac
	 */
	public boolean verificaPAC(int id_carro, float pac) {
		return carros.verificaPAC(id_carro,pac);
	}

	/**
	 *
	 * @param id_registo
	 * @param modo_motor
	 * @param pneus
	 */
	public void setCarroCampeonato(int id_registo, float PAC, String modo_motor, String pneus) {
		campeonatos.setCarroCampeonato(id_registo, PAC, modo_motor, pneus);
	}

	public void advanceToNextRace()
	{
		this.campeonatos.advanceToNextRace();
	}

	public List<String> simulaProxCorrida() {
		return campeonatos.simulaProxCorrida();
	}

	public String printResultado() {
		return campeonatos.printResultado();
	}


	/**
	 * 
	 * @param id_registo
	 */
	public int recolhePontos(int id_registo) {
		return campeonatos.recolhePontos(id_registo);
	}

	/**
	 * 
	 * @param nomeJogador
	 */
	public boolean estaAutenticado(String nomeJogador) {
		return jogadorDAO.get(nomeJogador).getAutenticado();
	}

	/**
	 * 
	 * @param nomeJogador
	 * @param pontos
	 */
	public void adicionarPontos(String nomeJogador, int pontos) {
		Jogador jogador = jogadorDAO.get(nomeJogador);
		jogador.addPontuacao(pontos);
	}

	/**
	 * 
	 * @param id_registo
	 */
	public boolean afinacaoValida(int id_registo) {
		return campeonatos.afinacaoValida(id_registo);
	}

	public Corrida getProxCorrida(){
		return campeonatos.getProxCorrida();
	}

	public boolean maisCorridas(){
		return this.campeonatos.maisCorridas();
	}
}