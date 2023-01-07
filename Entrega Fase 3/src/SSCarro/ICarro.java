package SSCarro;

import java.util.List;

public interface ICarro {

	/**
	 * 
	 * @param id_carro
	 * @param pac
	 */
	boolean verificaPAC(int id_carro, float pac);

	List<Carro> getCarros();



	/**
	 * 
	 * @param id_carro
	 *
	 */
	boolean categoriaC1ouC2(int id_carro);

	Carro getCarro(int id_carro);

}