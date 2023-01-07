package SSCampeonato;

import RacingManagerLN.*;
import SSCarro.*;
import SSCircuito.Circuito;

import java.util.List;
import java.util.Set;

public interface ICampeonato {

	/**
	 *
	 * @param id_registo
	 */
	int recolhePontos(int id_registo);

	/**
	 * 
	 *
	 */
	Corrida getProxCorrida();

	/**
	 * 
	 *
	 */
	List<String> simulaProxCorrida();

	String printResultado();

	//void registarPontos();

	List<Campeonato> getCampeonatos();

	/**
	 * 
	 * @param nomeJogador
	 * @param nomeCampeonato
	 * @param carro
	 * @param piloto
	 */
	Registo setJogadorCampeonato(Jogador nomeJogador, String nomeCampeonato, Carro carro, Piloto piloto);

	/**
	 *
	 * @param id_registo
	 * @param modo_motor
	 * @param pneus
	 */
	void setCarroCampeonato(int id_registo, float PAC, String modo_motor, String pneus);

	/**
	 * 
	 * @param id_registo
	 */
	boolean afinacaoValida(int id_registo);

	List<Circuito> getCircuitos();


	void advanceToNextRace();

	boolean maisCorridas();

}