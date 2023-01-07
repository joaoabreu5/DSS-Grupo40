package SSCircuito;

import Datalayer.*;
import SSCampeonato.Campeonato;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CircuitoFacade implements ICircuito {

	private CircuitoDAO circuitoDAO;
	private Set<Integer> circuitos;

	public CircuitoFacade()
	{
		this.circuitoDAO = CircuitoDAO.getInstance();
		this.circuitos = new HashSet<>();
	}

	public List<Circuito> getCircuitos() {
		List<Circuito> res = (List<Circuito>) circuitoDAO.values();
		return res;
	}


}