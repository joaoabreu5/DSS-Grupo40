package SSCarro;

import RacingManagerLN.Piloto;

import java.util.Random;

public class SC extends Carro {

    public SC(int id, String marca, String modelo, int cilindrada, int potencia, float fiabiliade, float PAC, String modo_motor, String pneu, int afinacoes, boolean dnf) {
        super(id, marca, modelo, cilindrada, potencia, fiabiliade, PAC, modo_motor, pneu, afinacoes, dnf);
    }

    public int getPotenciaTotal(){
        return this.getPotenciaICE();
    }

    public String getCategoria()
    {
        return this.getClass().getSimpleName();
    }

    public boolean DNF(int volta, int voltas, Piloto piloto, int num_zonas)
    {
        float fiab_cilindrada = 0.25F;
        float fiab_piloto = this.getFiabiliade() * 0.75F;
        fiab_piloto *= (1-piloto.getSVA()/10);

        float fiabilidade = fiab_cilindrada + fiab_piloto;

        double prob = (1-fiabilidade)/num_zonas;
        Random rand = new Random();
        double randomValue = rand.nextDouble();
        return randomValue < prob;
    }
}