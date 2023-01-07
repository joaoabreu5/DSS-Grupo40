package SSCarro;

import RacingManagerLN.Piloto;

import java.util.Random;

public class GTH extends GT implements Hibrido {
	private int motor_eletrico;

	public GTH(int id, String marca, String modelo, int cilindrada, int potencia, float fiabiliade, float PAC, String modo_motor, String pneu, int afinacoes, boolean dnf, int motor_eletrico) {
		super(id, marca, modelo, cilindrada, potencia, fiabiliade, PAC, modo_motor, pneu, afinacoes, dnf);
		this.motor_eletrico = motor_eletrico;
	}

	public int getPotenciaTotal(){
		return this.getPotenciaICE()+this.motor_eletrico;
	}

	public String getCategoria()
	{
		return this.getClass().getSimpleName();
	}

	public boolean DNF(int volta, int voltas, Piloto piloto, int num_zonas)
	{
		float fiabilidade = this.getFiabiliade() * (1-(float) this.getPotenciaTotal()/50000) * (1-((float) volta/voltas)/3);
		fiabilidade *= (1-(float) this.getCilindrada()/100000);

		double prob = (1-fiabilidade)/num_zonas;
		Random rand = new Random();
		double randomValue = rand.nextDouble();
		return randomValue < prob;
	}

	public int getPotenciaMotorEletrico()
	{
		return this.motor_eletrico;
	}

	public void setPotenciaMotorEletrico(int potencia_eletrica)
	{
		this.motor_eletrico = potencia_eletrica;
	}

}