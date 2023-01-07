package RacingManagerLN;

import SSCampeonato.*;
import SSCircuito.*;
import SSCarro.*;

import java.util.List;
import java.util.Set;

public interface IRacingManager {

	List<Piloto> getPilotos();

	List<Carro> getCarros();

	List<Campeonato> getCampeonatos();

	List<Circuito> getCircuitos();

	/**
	 * 
	 * @param nomeJogador
	 * @param nomeCampeonato
	 * @param carro
	 * @param piloto
	 */
	Registo setJogadorCampeonato(String nomeJogador, String nomeCampeonato, int carro, String piloto);

	/**
	 * 
	 * @param id_carro
	 */
	boolean categoriaC1ouC2(int id_carro);

	/**
	 * 
	 * @param idcarro
	 * @param pac
	 */
	boolean verificaPAC(int idcarro, float pac);

	/**
	 * 
	 * @param id_registo
	 * @param modo_motor
	 * @param pneus
	 */
	void setCarroCampeonato(int id_registo, float PAC, String modo_motor, String pneus);

	List<String> simulaProxCorrida();


	/**
	 * 
	 * @param id_registo
	 */
	int recolhePontos(int id_registo);

	/**
	 * 
	 * @param nomeJogador
	 */
	boolean estaAutenticado(String nomeJogador);

	/**
	 * 
	 * @param nomeJogador
	 * @param pontos
	 */
	void adicionarPontos(String nomeJogador, int pontos);

	/**
	 * 
	 * @param id_registo
	 */
	boolean afinacaoValida(int id_registo);

	void campeonatoEscolhido(String campeonato);

	List<Circuito> getCircuitosCampeonato();

	boolean maisCorridas();

	Corrida getProxCorrida();

	void advanceToNextRace();

}