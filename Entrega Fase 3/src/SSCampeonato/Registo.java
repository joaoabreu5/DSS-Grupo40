package SSCampeonato;

import Datalayer.*;
import RacingManagerLN.Jogador;
import RacingManagerLN.Piloto;
import SSCarro.Carro;
import SSCarro.Hibrido;
import SSCircuito.Zona;

public class Registo implements Comparable<Registo>
{
	private PilotoDAO pilotoDAO;
	private JogadorDAO jogadorDAO;
	private CarroDAO carroDAO;
	private int id;
	private float tempo;
	private float tempo_antes;
	private int pontuacao; // pontuação no campeonato
	private int id_carro;
	private String nome_jogador; // id
	private String nome_piloto; // id


	public Registo(int id, float tempo, float tempo_antes, int pontuacao, int id_carro, String nome_jogador, String nome_piloto)
	{
		this.id = id;
		this.tempo = tempo;
		this.tempo_antes = tempo_antes;
		this.pontuacao = pontuacao;
		this.id_carro = id_carro;
		this.nome_jogador = nome_jogador;
		this.nome_piloto = nome_piloto;
		this.pilotoDAO = PilotoDAO.getInstance();
		this.jogadorDAO = JogadorDAO.getInstance();
		this.carroDAO = CarroDAO.getInstance();
	}

	public Registo clone()
	{
		return new Registo(this.id, this.tempo, this.tempo_antes, this.pontuacao, this.id_carro, this.nome_jogador, this.nome_piloto);
	}

	public int compareTo(Registo registo)
	{
		return Float.compare(this.tempo, registo.tempo);
	}

	public Carro getCarro()
	{
		return this.carroDAO.get(this.id_carro);
	}

	public Piloto getPiloto()
	{
		return this.pilotoDAO.get(this.nome_piloto);
	}

	public String getNome_jogador() {
		return nome_jogador;
	}

	public int getId() {
		return id;
	}

	public float getTempo_antes() {
		return tempo_antes;
	}

	public float getTempo()
	{
		return this.tempo;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public int getId_carro() {
		return id_carro;
	}

	public String getNome_piloto() {
		return nome_piloto;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTempo(float tempo)
	{
		this.tempo = tempo;
	}

	public void setTempo_antes(float tempo_antes)
	{
		this.tempo_antes = tempo_antes;
	}

	public boolean categoriaInferior(Registo reg_ant) // verifica se uma categoria é inferior a outra (C1H e C1 são do mesmo nível neste método)
	{
		boolean r = false;
		String cat_reg = this.getCarro().getClass().getName();
		String cat_reg_ant = reg_ant.getCarro().getClass().getName();
		if (!cat_reg.equals(cat_reg_ant))
		{
			if (cat_reg.endsWith("H"))
				cat_reg = cat_reg.substring(0, cat_reg.length()-2);
			if (cat_reg_ant.endsWith("H"))
				cat_reg_ant = cat_reg_ant.substring(0, cat_reg_ant.length()-2);
			if (!cat_reg.equals(cat_reg_ant))
			{
				if (cat_reg.equals("C1") && (cat_reg_ant.equals("C2") || cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("C2") && (cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("GT") && cat_reg_ant.equals("SC"))
				{
					r = true;
				}
			}
		}
		return r;
	}

	public boolean categoriaInferiorOuHibrido(Registo reg_ant) // verifica se uma categoria é inferior a outra (C1 é inferior a C1 neste método)
	{
		boolean r = false;
		boolean reg_hibrido = false;
		boolean reg_ant_hibrido = false;
		String cat_reg = this.getCarro().getClass().getName();
		String cat_reg_ant = reg_ant.getCarro().getClass().getName();
		if (!cat_reg.equals(cat_reg_ant))
		{
			if (cat_reg.endsWith("H")) {
				cat_reg = cat_reg.substring(0, cat_reg.length() - 2);
				reg_hibrido = true;
			}
			if (cat_reg_ant.endsWith("H")) {
				cat_reg_ant = cat_reg_ant.substring(0, cat_reg_ant.length() - 2);
				reg_ant_hibrido = true;
			}
			if (!cat_reg.equals(cat_reg_ant))
			{
				if (cat_reg.equals("C1") && (cat_reg_ant.equals("C2") || cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("C2") && (cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("GT") && cat_reg_ant.equals("SC"))
				{
					r = true;
				}
			}
			else if (reg_hibrido && !reg_ant_hibrido)
			{
				r = true;
			}
		}
		return r;
	}

	public boolean mesmaCategoriaOuInferior(Registo reg_ant)  // verifica se uma categoria é do mesmo nível ou inferior a outra (C1H e C1 são do mesmo nível neste método)
	{
		boolean r = false;
		String cat_reg = this.getCarro().getClass().getName();
		String cat_reg_ant = reg_ant.getCarro().getClass().getName();
		if (!cat_reg.equals(cat_reg_ant))
		{
			if (cat_reg.endsWith("H"))
				cat_reg = cat_reg.substring(0, cat_reg.length()-2);
			if (cat_reg_ant.endsWith("H"))
				cat_reg_ant = cat_reg_ant.substring(0, cat_reg_ant.length()-2);
			if (!cat_reg.equals(cat_reg_ant))
			{
				if (cat_reg.equals("C1") && (cat_reg_ant.equals("C2") || cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("C2") && (cat_reg_ant.equals("GT") || cat_reg_ant.equals("SC")))
				{
					r = true;
				}
				else if (cat_reg.equals("GT") && cat_reg_ant.equals("SC"))
				{
					r = true;
				}
			}
			else
			{
				r = true;
			}
		}
		else
		{
			r = true;
		}
		return r;
	}

	public boolean mesmaCategoria(Registo reg_ant) // C1 e C1H são categorias do mesmo nível neste método
	{
		boolean r = false;
		String cat_reg = this.getCarro().getClass().getName();
		String cat_reg_ant = reg_ant.getCarro().getClass().getName();
		if (!cat_reg.equals(cat_reg_ant))
		{
			if (cat_reg.endsWith("H"))
				cat_reg = cat_reg.substring(0, cat_reg.length()-2);
			if (cat_reg_ant.endsWith("H"))
				cat_reg_ant = cat_reg_ant.substring(0, cat_reg_ant.length()-2);
			if (cat_reg.equals(cat_reg_ant))
			{
				r = true;
			}
		}
		else
		{
			r = true;
		}
		return r;
	}


	public boolean ultrapassa(Registo reg_ant, String GDU, boolean clima)
	{
		boolean ultrapassa = false;
		switch (GDU) {
			case "Impossível":
				if (this.categoriaInferior(reg_ant) && this.getPiloto().getSVA() - reg_ant.getPiloto().getSVA() >= 0.7)
				{
					ultrapassa = true;
				}
				break;
			case "Difícil":
				if (this.categoriaInferiorOuHibrido(reg_ant) && this.getPiloto().getSVA() - reg_ant.getPiloto().getSVA() >= 0.3)
				{
					ultrapassa = true;
				}
				break;
			case "Possível":
				if (this.mesmaCategoriaOuInferior(reg_ant) && this.getPiloto().getSVA() - reg_ant.getPiloto().getSVA() >= 0.1)
				{
					ultrapassa = true;
				}
				break;
		}
		return ultrapassa;
	}

	/**
	 * 
	 * @param size
	 */
	public boolean afinacaoValida(int size) {
		Carro carro = carroDAO.get(this.id_carro);
		return carro.afinacaoValida(size);
	}

	/**
	 * 
	 * @param modomotor
	 * @param pneus
	 */
	public void setCarroCampeonato(String modomotor, String pneus) {
		Carro carro = carroDAO.get(id_carro);
		carro.addAfinacao();
		carro.setMotor(modomotor);
		carro.setPneus(pneus);
	}

	/**
	 * @param reg_carro_frente
	 * @param zona
	 * @param volta
	 * @param clima
	 * @param despiste
	 */
	public float tempoProximaZona(Registo reg_carro_frente, Zona zona, int volta, boolean clima, boolean despiste)
	{
		float t_ant_reg_frente, t_inicio_zona, t_zona;
		t_inicio_zona = this.tempo; // tempo feito até ao momento pelo carro

		t_zona = this.tempoZona(zona, clima, volta);

		float t_fim_zona = t_inicio_zona + t_zona;

		if (reg_carro_frente != null)
		{
			boolean mesmaCategoria = this.mesmaCategoria(reg_carro_frente);
			boolean categoriaInferior = this.categoriaInferior(reg_carro_frente);
			if (mesmaCategoria && t_inicio_zona - reg_carro_frente.tempo_antes >= 1 && t_fim_zona < reg_carro_frente.tempo)
			{
				t_fim_zona = (float) (reg_carro_frente.tempo + 0.1);
			}
			else if (categoriaInferior && t_inicio_zona - reg_carro_frente.tempo_antes >= 2 && t_fim_zona < reg_carro_frente.tempo)
			{
				t_fim_zona = (float) (reg_carro_frente.tempo + 0.1);
			}
		}
		return t_fim_zona;
	}

	public float tempoZona(Zona z, boolean clima, int volta)
	{
		Piloto p = this.getPiloto();
		Carro c = this.getCarro();
		float tempo_medio_zona =  z.tempoMedioZona(c.getPAC());
		float tempo = tempo_medio_zona;

		String pneus = c.getPneu();
		if (!clima)
		{
			float CTS = p.getCTS();
			tempo *= 1.05; // volta à chuva mais lenta e tendo em conta o CTS
			if (CTS < 0.5) {
				tempo *= 1 + CTS/150;
			}
			else if (CTS > 0.5) {
				tempo *= 1 - CTS/150;
			}
			tempo = tempo + tempo*volta/2600; // desgaste dos pneus -> volta mais lenta
		}
		else
		{
			tempo += tempo*(1.5-p.getCTS())/100; // tempo da volta varia conforme CTS
			if (pneus.equals("Macio"))
			{
				tempo = (float) (tempo - tempo*0.007);
				tempo = tempo + tempo*volta/1200; // desgaste dos pneus -> volta mais lenta
			}
			else if (pneus.equals("Duro"))
			{
				tempo = tempo + tempo*volta/1800; // desgaste dos pneus -> volta mais lenta
			}
		}

		float SVA = p.getSVA();
		if (SVA < 0.5)
		{
			tempo *= 1 + SVA/100;
		}
		else if (SVA > 0.5)
		{
			tempo *= 1 - SVA/100;
		}

		tempo *= 1-(c.getPotenciaICE()/tempo_medio_zona)/2200;
		if(c instanceof Hibrido) // tempo por volta diminui se o carro é híbrido
		{
			Hibrido h = (Hibrido)c;
			int potencia_eletrica = h.getPotenciaMotorEletrico();
			tempo *= 1-(potencia_eletrica/tempo_medio_zona)/2250;
		}

		String modo_motor = c.getModo_motor();

		if (modo_motor.equals("Agressivo"))
		{
			tempo *= 0.98;
		}
		else if (modo_motor.equals("Conservador"))
		{
			tempo *= 1.02;
		}

		return tempo;
	}

}