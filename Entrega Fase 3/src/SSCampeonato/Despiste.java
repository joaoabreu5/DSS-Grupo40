package SSCampeonato;

import SSCircuito.Zona;

public class Despiste extends Acontecimento {
    private Zona zona;
    private Registo registo;

    public Despiste(int volta, Zona zona, Registo r)
    {
        super(volta);
        this.zona = zona;
        this.registo = r;
    }

    public Zona getZona() {
        return zona;
    }

    public Registo getRegisto() {
        return registo;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n");
        sb.append("O(A) Jogador(a) ");
        sb.append(registo.getNome_jogador());
        sb.append(" soferu um despiste, mas permaneceu na corrida.\n");
        return sb.toString();
    }
}