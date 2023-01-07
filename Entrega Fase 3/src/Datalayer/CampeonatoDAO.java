package Datalayer;

import RacingManagerLN.Administrador;
import RacingManagerLN.Jogador;
import SSCampeonato.Campeonato;
import SSCampeonato.Registo;

import java.sql.*;
import java.util.*;

public class CampeonatoDAO implements Map<String, Campeonato>
{
    private static CampeonatoDAO singleton = null;

    private CampeonatoDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static CampeonatoDAO getInstance()
    {
        if (CampeonatoDAO.singleton == null) {
            CampeonatoDAO.singleton = new CampeonatoDAO();
        }
        return CampeonatoDAO.singleton;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Campeonato get(Object key) {
        Campeonato camp = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Campeonato WHERE Nome = '" + key + "'")) {
            if (rs.next())
            {
                String nome_camp = (String) key;
                int corridas_simuladas = rs.getInt("CorridasSimuladas");
                String versaoCamp = rs.getString("VersaoCamp");

                Set<Integer> registos = new HashSet<>();
                List<Integer> corridas = getCorridasCampeonato(nome_camp, stm);
                List<Integer> rankC1 = new ArrayList<>();
                List<Integer> rankC2 = new ArrayList<>();
                List<Integer> rankGT = new ArrayList<>();
                List<Integer> rankSC = new ArrayList<>();
                List<Integer> rankHibrido = new ArrayList<>();

                String sql = "SELECT * FROM Registo WHERE Nome_Campeonato = '" + nome_camp + "' ORDER BY PontuacaoCamp DESC" ;
                try (ResultSet rsc = stm.executeQuery(sql))
                {
                    while (rsc.next()) {
                        int id_registo = rsc.getInt("Id");
                        int pontuacao_camp = rsc.getInt("PontuacaoCamp");
                        int id_carro = rsc.getInt("Id_Carro");
                        registos.add(id_registo);
                        String categoria_carro = getCategoriaCarrorRegisto(id_carro);

                        if ("C1".equals(categoria_carro)) {
                            rankC1.add(id_registo);
                        }
                        else if ("C2".equals(categoria_carro)) {
                            rankC2.add(id_registo);
                        }
                        else if ("GT".equals(categoria_carro)) {
                            rankGT.add(id_registo);
                        }
                        else if ("SC".equals(categoria_carro)) {
                            rankSC.add(id_registo);
                        }
                        else if ("C1H".equals(categoria_carro) || "C2H".equals(categoria_carro) || "GTH".equals(categoria_carro)) {
                            rankHibrido.add(id_registo);
                        }
                    }
                }
                camp = new Campeonato(nome_camp, versaoCamp, corridas_simuladas, corridas, registos, rankC1, rankC2, rankGT, rankSC, rankHibrido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return camp;
    }

    @Override
    public Campeonato put(String key, Campeonato camp)
    {
        Campeonato res_camp = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Campeonato \n" +
                    "VALUES \n" +
                    "('" + key + "', '" + camp.getCorrida() + "','" + camp.getVersao() + "');";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_camp;
    }

    public void updateCorrida(String key, int corridas_simuladas)
    {
        Campeonato res_camp = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "UPDATE Campeonato \n" +
                    "SET CorridasSimuladas = " + corridas_simuladas + "\n" +
                    "WHERE Nome = '" + key + "';";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Campeonato remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Campeonato> values() {
        Collection<Campeonato> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Nome FROM Campeonato")) {
            while (rs.next()) {
                String id_camp = rs.getString("Nome");
                Campeonato campeonato = this.get(id_camp);
                res.add(campeonato);
            }
        } catch (Exception e) {
              e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Collection<Campeonato> getCampeonatosPorSimular()
    {
        Collection<Campeonato> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Campeonato")) {
            while (rs.next()) {
                String nome_camp = rs.getString("Nome");
                int num_corridas_simuladas = rs.getInt("CorridasSimuladas");

                String sql = "SELECT COUNT(Id) FROM Corrida WHERE Nome_Campeonato = '" + nome_camp + "';";

                try(Statement stm2 = conn.createStatement();
                    ResultSet rs2 = stm2.executeQuery(sql)) {
                    if (rs2.next()) {
                        int num_corridas_camp = rs2.getInt(1);
                        if (num_corridas_simuladas < num_corridas_camp)
                        {
                            Campeonato campeonato = this.get(nome_camp);
                            res.add(campeonato);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        return null;
    }

    public List<Integer> getCorridasCampeonato(String nome_camp, Statement stm)
    {
        List<Integer> corridas = new ArrayList<>();
        try (ResultSet rsa = stm.executeQuery("SELECT Id FROM Corrida WHERE Nome_Campeonato = '" + nome_camp + "'")) {
            while(rsa.next()) {
                corridas.add(rsa.getInt("Id"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return corridas;
    }

    public String getCategoriaCarrorRegisto(int id_carro)
    {
        String categoria = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Categoria FROM Carro WHERE Id = '" + id_carro + "'")) {
            if (rs.next()) {
                categoria = rs.getString("Categoria");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoria;
    }



}