package SSCircuito;

import Datalayer.*;

import java.util.ArrayList;
import java.util.List;

public class Circuito {

	private String nome;
	private int curvas;
	private int chicanes;
	private int retas;
	private int comprimento;
	private List<Integer> zonas;
	private ZonaDAO zonaDAO;

	public Circuito(String nome, int curvas, int chicanes, int retas, int comprimento, List<Integer> zonas)
	{
		this.nome = nome;
		this.curvas = curvas;
		this.chicanes = chicanes;
		this.retas = retas;
		this.comprimento = comprimento;
		this.zonas = new ArrayList<>(zonas);
		this.zonaDAO = ZonaDAO.getInstance();
	}


	public String getNome() {
		return nome;
	}

	public int getComprimento() {
		return comprimento;
	}

	public int getCurvas() {
		return curvas;
	}

	public int getChicanes() {
		return chicanes;
	}

	public int getRetas() {
		return retas;
	}

	public List<Zona> getZonas()
	{
		List<Zona> zonas = new ArrayList<>();
		for (Integer id_zona : this.zonas)
		{
			zonas.add(zonaDAO.get(id_zona));
		}
		return zonas;
	}

	@Override
	public String toString() {
		return "Circuito: " + nome  +
				", Comprimento = " + comprimento+
				", Curvas = " + curvas +
				", Chicanes = " + chicanes +
				", Retas = " + retas ;
	}
}