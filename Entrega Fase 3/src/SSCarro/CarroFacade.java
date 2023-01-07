package SSCarro;

import Datalayer.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarroFacade implements ICarro {

	private CarroDAO carroDAO;
	private Set<Integer> carros;

	public CarroFacade()
	{
		this.carroDAO = CarroDAO.getInstance();
		this.carros = new HashSet<>();
	}


	/**
	 * 
	 * @param id_carro
	 * @param pac
	 */
	public boolean verificaPAC(int id_carro, float pac) {
		Carro carro = carroDAO.get(id_carro);
		return carro.verificarPAC(pac);
	}

	/**
	 * 
	 * @param id_carro
	 */
	public boolean categoriaC1ouC2(int id_carro) {
		Carro c = carroDAO.get(id_carro);
		String categoria = c.getCategoria();
		if (categoria.equals("C1") || categoria.equals("C2")) {
			return true;
		}
		return false;
	}

	public Carro getCarro(int id_carro){
		return carroDAO.get(id_carro);
	}

	public List<Carro> getCarros()
	{
		List<Carro> res = (List<Carro>) carroDAO.values();
		return res;
	}

}