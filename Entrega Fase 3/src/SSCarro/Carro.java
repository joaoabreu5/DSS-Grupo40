package SSCarro;

import RacingManagerLN.Piloto;

public abstract class Carro {
	private int id;
	private String marca;
	private String modelo;
	private int cilindrada;
	private int potenciaICE;
	private float fiabiliade;
	private float PAC;
	private String modo_motor;
	private String pneu;
	private int afinacoes;
	private boolean dnf;

	public Carro(int id, String marca, String modelo, int cilindrada, int potencia, float fiabiliade, float PAC, String modo_motor, String pneu, int afinacoes, boolean dnf)
	{
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindrada = cilindrada;
		this.potenciaICE = potencia;
		this.fiabiliade = fiabiliade;
		this.PAC = PAC;
		this.modo_motor = modo_motor;
		this.pneu = pneu;
		this.afinacoes = afinacoes;
		this.dnf = dnf;
	}

	public abstract String getCategoria();

	public int getId()
	{
		return id;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public int getCilindrada() {
		return cilindrada;
	}

	public int getPotenciaICE() {
		return potenciaICE;
	}

	public abstract int getPotenciaTotal();

	public float getFiabiliade() {
		return fiabiliade;
	}

	public int getAfinacoes() {
		return afinacoes;
	}

	public boolean isDnf() {
		return dnf;
	}

	public float getPAC()
	{
		return this.PAC;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModo_motor()
	{
		return this.modo_motor;
	}

	public String getPneu()
	{
		return this.pneu;
	}

	public void addAfinacao(){this.afinacoes=this.afinacoes+1;}

	public String toString()
	{
		return "Marca: " + this.marca + ", Modelo: " + this.modelo +", Categoria: " + this.getCategoria() + ", Cilindrada: " + this.cilindrada + ", Potência: " + this.getPotenciaTotal();
	}

	public boolean afinacaoValida(int size){
		if(afinacoes<(size*0.66)){
			return true;
		}
		return false;
	}

	public boolean verificarPAC(float pac) {
		if (pac>0 && pac<1) {
			return true;
		}
		return false;
	}

	public void setMotor(String modo_motor) {
		this.modo_motor = modo_motor;
	}

	public void setPneus(String pneu) {
		this.pneu = pneu;
	}

	/**
	 *
	 * @param volta
	 * @param voltas
	 * @param piloto
	 */
	public abstract boolean DNF(int volta, int voltas, Piloto piloto, int num_zonas);
}