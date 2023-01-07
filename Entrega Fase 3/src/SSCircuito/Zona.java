package SSCircuito;

public class Zona {
	private int id;
	private int numero;
	private String GDU;
	private String tipo;

	public Zona(int id, int numero, String GDU, String tipo)
	{
		this.id = id;
		this.numero = numero;
		this.GDU = GDU;
		this.tipo = tipo;
	}

	public int getId()
	{
		return this.id;
	}

	public int getNumero()
	{
		return this.numero;
	}

	public String getGDU()
	{
		return this.GDU;
	}

	public String getTipo()
	{
		return this.tipo;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Zona clone()
	{
		return new Zona(this.id, this.numero, this.GDU, this.tipo);
	}


	public float tempoMedioZona(float PAC)
	{
		float t_zona = 0;
		String tipo_zona = this.getTipo();
		if (tipo_zona.equals("Chicane"))
		{
			t_zona = (float) 4.5;
			if (PAC > 0.5)
			{
				t_zona *= (1-PAC/100);
			}
			else if (PAC < 0.5)
			{
				t_zona *= (1+PAC/100);
			}
		}
		else if (tipo_zona.equals("Curva"))
		{
			t_zona = 2;
			if (PAC > 0.5)
			{
				t_zona *= (1-PAC/100);
			}
			else if (PAC < 0.5)
			{
				t_zona *= (1+PAC/100);
			}
		}
		else if (tipo_zona.equals("Reta"))
		{
			t_zona = 4;
			if (PAC < 0.5)
			{
				t_zona *= (1-PAC/100);
			}
			else if (PAC > 0.5)
			{
				t_zona *= (1+PAC/100);
			}
		}
		return t_zona;
	}

}