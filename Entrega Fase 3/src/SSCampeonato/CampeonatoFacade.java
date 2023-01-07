package SSCampeonato;

import Datalayer.*;
import RacingManagerLN.*;
import SSCarro.*;
import SSCircuito.Circuito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CampeonatoFacade implements ICampeonato {

	private CampeonatoDAO campeonatoDAO;
	private String campeonato;
	private Set<String> campeonatos;

	public CampeonatoFacade()
	{
		this.campeonatoDAO = CampeonatoDAO.getInstance();
		this.campeonato = "";
		this.campeonatos = new HashSet<>();
	}

	public void setCampeonato(String campeonato)
	{
		this.campeonato = campeonato;
	}


	/**
	 * 
	 * @param id_registo
	 */
	public int recolhePontos(int id_registo) {
		return campeonatoDAO.get(this.campeonato).recolhePontos(id_registo);
	}

	/**
	 * 
	 *
	 */
	public Corrida getProxCorrida() {
		Campeonato c = campeonatoDAO.get(this.campeonato);
		return c.getProxCorrida();
	}

	public void advanceToNextRace()
	{
		Campeonato c = this.campeonatoDAO.get(this.campeonato);
		c.advanceToNextRace();
		this.campeonatoDAO.updateCorrida(this.campeonato,c.getCorrida());
	}

	public List<Circuito> getCircuitos(){
		Campeonato c = campeonatoDAO.get(this.campeonato);
		return c.getCircuitos();
	}

	/**
	 *
	 *
	 */
	public List<String> simulaProxCorrida() {
		return campeonatoDAO.get(this.campeonato).simulaProxCorrida();
	}

	public String printResultado() {
		Campeonato camp = campeonatoDAO.get(this.campeonato);
		return camp.printResultado();
	}


	public List<Campeonato> getCampeonatos() {
		List<Campeonato> res = (List<Campeonato>) campeonatoDAO.getCampeonatosPorSimular();
		return res;
	}

	/**
	 * 
	 * @param Jogador
	 * @param nomeCampeonato
	 * @param carro
	 * @param piloto
	 */
	public Registo setJogadorCampeonato(Jogador Jogador, String nomeCampeonato, Carro carro, Piloto piloto) {
		Campeonato camp = campeonatoDAO.get(nomeCampeonato);
		return camp.addRegisto(Jogador,carro,piloto);
	}

	/**
	 *
	 * @param id_registo
	 * @param modo_motor
	 * @param pneus
	 */
	public void setCarroCampeonato(int id_registo, float PAC, String modo_motor, String pneus) {
		Campeonato c = campeonatoDAO.get(this.campeonato);
		c.setCarroCampeonato(id_registo, PAC, modo_motor, pneus);
	}

	/**
	 * 
	 * @param id_registo
	 */
	public boolean afinacaoValida(int id_registo) {
		Campeonato c = campeonatoDAO.get(this.campeonato);
		return c.afinacaoValida(id_registo);
	}

	public boolean maisCorridas(){
		return this.campeonatoDAO.get(campeonato).maisCorridas();
	}
}