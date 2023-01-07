package UI;

import RacingManagerLN.Jogador;
import RacingManagerLN.Piloto;
import RacingManagerLN.RacingManagerFacade;
import SSCampeonato.Campeonato;
import SSCampeonato.Corrida;
import SSCampeonato.Registo;
import SSCarro.Carro;
import SSCircuito.Circuito;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private RacingManagerFacade model;
    private Menu menu;
    private Scanner scin;
    public TextUI() {
        this.menu = new Menu(new String[]{
                "Login",
                "Login Administrador",
                "Registar",
        });
        this.menu.setHandler(1, this::trataLogin);
        this.menu.setHandler(3, this::trataRegistar);
        this.model = new RacingManagerFacade();
        this.scin = new Scanner(System.in);
    }

    public void run() {
        this.menu.run();
        System.out.println("Até breve!...");
    }

    public void trataEntrar() {
        this.menu = new Menu(new String[]{
                "Escolher Campeonato",
        });
        this.menu.setHandler(1, this::trataEscolherCampeonato);

        this.model = new RacingManagerFacade();
        this.scin = new Scanner(System.in);
        this.menu.run();

    }

    public void trataLogin() {
        System.out.println("Nome de utilizador: ");
        Scanner scnnu = new Scanner(System.in);
        scnnu.nextLine();
        System.out.println("Palavra Passe: ");
        Scanner scnnp = new Scanner(System.in);
        scnnp.nextLine();
        trataEntrar();
    }
    public void trataRegistar() {
        System.out.println("Nome de utilizador: ");
        Scanner scnnu = new Scanner(System.in);
        scnnu.nextLine();
        System.out.println("Palavra Passe: ");
        Scanner scnnp = new Scanner(System.in);
        scnnp.nextLine();
        trataEntrar();
        System.out.println("Seja bem vindo");
    }

    public String trataPedirNome(){
        System.out.println("\nNome Jogador: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }

    private void trataEscolherCampeonato() {
        String camp = trataListarCampeonato();
        this.model.campeonatoEscolhido(camp);
        System.out.println("\n------------------Circuitos------------------\n");
        List< Circuito> circuitos = this.model.getCircuitosCampeonato();
        for(Circuito c :circuitos){
            System.out.println(c);
        }
        List<Registo> participantes = new ArrayList<>();
        participantes.add(this.model.setJogadorCampeonato(trataPedirNome(),camp,trataListarCarros(),trataListarPilotos()));
        participantes.addAll(trataNovosParticipantes(camp));
        trataSimularCampeonato(participantes);
    }

    private String trataListarCampeonato()
    {
        List<Campeonato> campeonatos = this.model.getCampeonatos();
        List<Campeonato> opcoes = List.copyOf(campeonatos);
        int opt = 1 ;
        int i ;
        int escolhido=0;
        System.out.println("\n------------------Campeonatos------------------\n");
        while (opt !=0 ){
            i = 1;
            for (Campeonato op : opcoes){
                StringBuilder sb = new StringBuilder();
                sb.append(i);sb.append("- ");sb.append(op.toString());
                System.out.println(sb.toString());
                i++;
            }
            System.out.print("\nOpção: ");
            Scanner scanner = new Scanner(System.in);
            escolhido = scanner.nextInt();
            escolhido--;
            if(escolhido>=0 && escolhido<opcoes.size())
                opt = 0;
        }
        return opcoes.get(escolhido).getNome();
    }

    private int trataListarCarros() {
        List<Carro> carro = this.model.getCarros();
        List<Carro> opcoes = List.copyOf(carro);
        int opt = 1 ;
        int i ;
        int escolhido=0;
        System.out.println("\n------------------Carros------------------\n");
        while (opt !=0 ){
            i = 1;
            for (Carro op : opcoes){
                StringBuilder sb = new StringBuilder();
                sb.append(i);sb.append("- ");sb.append(op.toString());
                System.out.println(sb.toString());
                i++;
            }
            System.out.print("\nOpção: ");
            Scanner scanner = new Scanner(System.in);
            escolhido = scanner.nextInt();
            escolhido--;
            if(escolhido>=0 && escolhido<opcoes.size())opt = 0;
        }
        return opcoes.get(escolhido).getId();
    }

    private String trataListarPilotos() {
        List<Piloto> pilotos = this.model.getPilotos();
        List<Piloto> opcoes = List.copyOf(pilotos);
        int opt = 1 ;
        int i ;
        int escolhido=0;
        System.out.println("\n------------------Piloto------------------\n");
        while (opt !=0 ){
            i = 1;
            for (Piloto op : opcoes){
                StringBuilder sb = new StringBuilder();
                sb.append(i);sb.append("- ");sb.append(op.toString());
                System.out.println(sb.toString());
                i++;
            }
            System.out.print("\nOpção: ");
            Scanner scanner = new Scanner(System.in);
            escolhido = scanner.nextInt();
            escolhido--;
            if(escolhido>=0 && escolhido<opcoes.size())opt = 0;
        }
        return opcoes.get(escolhido).getNome();
    }

    public List<Registo> trataNovosParticipantes(String camp)
    {
        int op = 1;
        int escolha = -1;
        Scanner scanner = new Scanner(System.in);
        List<Registo> res = new ArrayList<>();
        while (op != 0) {
            System.out.println("\nDeseja adicionar mais um jogador:\n1-Sim\n2-Não");
            escolha = scanner.nextInt();
            if (escolha == 1) {
                scanner = new Scanner(System.in);
                System.out.println("\nNome participante:");
                String nome_jogador = scanner.nextLine();
                int id_carro = trataListarCarros();
                String nome_piloto = trataListarPilotos();
                res.add(model.setJogadorCampeonato(nome_jogador, camp, id_carro, nome_piloto));
            }
            else op = 0;
        }
        return res;
    }

    private void trataSimularCampeonato(List<Registo> registos) {
        while (this.model.maisCorridas())
        {
            Corrida corrida = model.getProxCorrida();
            System.out.println(corrida.toString());
            for (Registo r : registos) {
                trataConfCorrida(r);
            }
            trataSimularCorrida();
            trataEnter();
        }
    }

    private void trataEnter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPressione uma tecla para continuar...");
        scanner.nextLine();
    };

    private void trataSimularCorrida()
    {
        List<String> acontecimentos = this.model.simulaProxCorrida();
        for(String s : acontecimentos) {
            System.out.println(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        this.model.advanceToNextRace();
    }

    private void trataConfCorrida(Registo r)
    {
        Scanner scanner = new Scanner(System.in);
        int escolha = -1;
        int opt = 1;
        String motor = "";
        float pac = -1;
        if (model.categoriaC1ouC2(r.getId_carro())) {
            if (model.afinacaoValida(r.getId())) {
                while (opt != 0) {
                    System.out.println("\n\n"+r.getNome_jogador()+" pretende fazer alteracoes?\n1-Sim\n2-Não");
                    escolha = scanner.nextInt();
                    if (escolha > 0 && escolha < 3) opt = 0;
                }
                if (escolha == 1) {
                    opt=1;
                    while (opt != 0) {
                        System.out.println("\nPAC:");
                        pac = scanner.nextFloat();
                        if (model.verificaPAC(r.getId_carro(), pac))
                            opt = 0;
                    }
                }
            }
        }
        else System.out.println(r.getNome_jogador()+" não pode alterar o PAC nem modo de motor.");
        opt=1;
        while (opt != 0) {
            System.out.println("\nMotor:\n1-Agressivo\n2-Normal\n3-Conservador");
            escolha = scanner.nextInt();
            if (escolha > 0 && escolha < 4) opt = 0;
            switch (escolha) {
                case 1:
                    motor = "Agressivo";
                    break;
                case 2:
                    motor = "Normal";
                    break;
                case 3:
                    motor = "Conservador";
                    break;
            }
        }
        opt=1;
        String pneus = "";
        while (opt != 0) {
            System.out.println("\nPneus:\n1-Macio\n2-Duro\n3-Chuva");
            escolha = scanner.nextInt();
            if (escolha > 0 && escolha < 4) opt = 0;
            switch (escolha) {
                case 1:
                    pneus = "Macio";
                    break;
                case 2:
                    pneus = "Duro";
                    break;
                case 3:
                    pneus = "Chuva";
                    break;
            }
        }
        model.setCarroCampeonato(r.getId(), pac, motor, pneus);
    }
}
