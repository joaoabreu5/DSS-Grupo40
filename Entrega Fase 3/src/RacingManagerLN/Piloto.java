package RacingManagerLN;

import java.util.Random;

public class Piloto {

	private String nome;
	private int qualidade;
	private float CTS;
	private float SVA;

	public Piloto(String nome, float CTS, float SVA)
	{
		this.nome = nome;
		this.CTS = CTS;
		this.SVA = SVA;
	}

	public String toString()
	{
		return "Nome: " + this.nome + ", CTS: " + this.CTS + ", SVA: " + this.SVA;
	}

	public String getNome()
	{
		return this.nome;
	}

	public float getSVA()
	{
		return this.SVA;
	}

	public float getCTS()
	{
		return this.CTS;
	}

	/**
	 * 
	 * @param volta
	 * @param voltas
	 * @param clima
	 */
	public boolean acidente(int volta, int voltas, boolean clima)
	{
		boolean r = false;
		if (this.getSVA() >= 0.8)
		{
			double prob = this.getSVA()/30 * ((double) volta/voltas);
			if (!clima)
			{
				prob += this.getCTS()/30;
			}
			Random rand = new Random();
			double randomValue = rand.nextDouble();
			if (randomValue < prob)
				r = true;
		}
		return r;
	}

	/**
	 *
	 * @param volta
	 * @param voltas
	 * @param clima
	 */
	public boolean despiste(int volta, int voltas, boolean clima)
	{
		boolean r = false;
		if (this.getSVA() >= 0.35)
		{
			double prob = this.getSVA()/10 * ((double) volta/voltas);
			if (!clima)
			{
				prob += this.getCTS()/10;
			}
			Random rand = new Random();
			double randomValue = rand.nextDouble();
			if (randomValue < prob)
				r = true;
		}
		return r;
	}

}