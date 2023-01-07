package Datalayer;

import SSCampeonato.Corrida;
import SSCarro.Carro;
import SSCarro.Hibrido;

import java.sql.*;
import java.util.*;

public class CorridaDAO implements Map<Integer, Corrida> {
    private static CorridaDAO singleton = null;

    private CorridaDAO() {
        DAOconfig.DAO_CreateTables();
    }

    public static CorridaDAO getInstance() {
        if (CorridaDAO.singleton == null) {
            CorridaDAO.singleton = new CorridaDAO();
        }
        return CorridaDAO.singleton;
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
    public Corrida get(Object key) {
        Corrida corr = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Corrida WHERE Id = '" + key + "'")) {
            if (rs.next()) {
                int id_corrida = (int) key;
                int voltas =  rs.getInt("Voltas");
                boolean clima_seco = rs.getBoolean("Clima_Seco");
                String nome_circuito = rs.getString("Nome_Circuito");

                Set<Integer> registos = getRegistosCorrida((Integer) key, stm);
                List<Integer> resultado = new ArrayList<>();
                Map<Integer, Integer> rankC1 = new LinkedHashMap<>();
                Map<Integer, Integer> rankC2 = new LinkedHashMap<>();
                Map<Integer, Integer> rankGT = new LinkedHashMap<>();
                Map<Integer, Integer>rankSC = new LinkedHashMap<>();
                Map<Integer, Integer> rankHibrido = new LinkedHashMap<>();

                String sql = "SELECT * FROM Registo_Corrida WHERE Id_Corrida = '" + id_corrida + "' ORDER BY Posicao ASC";
                try(ResultSet rs_rc = stm.executeQuery(sql))
                {
                    while(rs_rc.next()) {
                        int id_registo = rs_rc.getInt("Id_Registo");
                        int pontuacao = rs_rc.getInt("Pontuacao");
                        String categoria_carro = getCategoriaCarroRegisto(id_registo);

                        if ("C1".equals(categoria_carro)) {
                            rankC1.put(id_registo, pontuacao);
                        }
                        else if ("C2".equals(categoria_carro)) {
                            rankC2.put(id_registo, pontuacao);
                        }
                        else if ("GT".equals(categoria_carro)) {
                            rankGT.put(id_registo, pontuacao);
                        }
                        else if ("SC".equals(categoria_carro)) {
                            rankSC.put(id_registo, pontuacao);
                        }
                        else if ("C1H".equals(categoria_carro) || "C2H".equals(categoria_carro) || "GTH".equals(categoria_carro)) {
                            rankHibrido.put(id_registo, pontuacao);
                        }
                    }
                }
                corr = new Corrida(id_corrida, voltas, clima_seco, nome_circuito, registos, resultado, rankC1, rankC2, rankGT, rankSC, rankHibrido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return corr;
    }

    @Override
    public Corrida put(Integer key, Corrida value) {
        return null;
    }

    public Corrida putNew(Corrida corrida, int id_campeonato) {
        Corrida res_corrida = null;
        int id_corrida = -1;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String clima = "TRUE";
            if (!corrida.getClima())
                clima = "FALSE";

            String sql = "INSERT INTO Corrida \n" +
                    "(Voltas, Clima_Seco, Nome_Circuito, Nome_Campeonato) \n" +
                    "VALUES \n" +
                    "('" + corrida.getVoltas() + "', " + clima + ", '" + corrida.getCircuito() + "', " + id_campeonato + ");";
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id_corrida = rs.getInt(1);
            }
            corrida.setId(id_corrida);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_corrida;
    }

    @Override
    public Corrida remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Corrida> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Corrida> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, Corrida>> entrySet() {
        return null;
    }

    public Set<Integer> getRegistosCorrida(Integer id_corrida, Statement stm) throws SQLException {
        String nome_campeonato = "";
        Set<Integer> registos = new HashSet<>();
        try (ResultSet rs = stm.executeQuery("SELECT Nome_Campeonato FROM Corrida WHERE Id = '" + id_corrida + "'")) {
            if (rs.next()) {
                nome_campeonato = rs.getString("Nome_Campeonato");
            }

            try (ResultSet rsa = stm.executeQuery("SELECT Id FROM Registo WHERE Nome_Campeonato = '" + nome_campeonato + "'")) {
                while (rsa.next()) {
                    registos.add(rsa.getInt("Id"));
                }
            }
        }
        return registos;
    }

    public String getCategoriaCarroRegisto(int id_registo)
    {
        String categoria = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id_Carro FROM Registo WHERE Id = '" + id_registo + "'")) {
            if (rs.next()) {
                int id_carro = rs.getInt("Id_Carro");
                try (ResultSet rsc = stm.executeQuery("SELECT Categoria FROM Carro WHERE Id = '" + id_carro + "'")) {
                    if (rsc.next()) {
                        categoria = rsc.getString("Categoria");
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoria;
    }

    public void setAfincacCarroCorrida(int id_registo, int id_corrida, float PAC, String modo_motor, String pneus)
    {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Registo_Corrida \n" +
                    "(Id_Registo, Id_Corrida, PAC, ModoMotor, Pneus, Tempo, Posicao, PosCategoria, Pontuacao) \n" +
                    "VALUES \n" +
                    "('" + id_registo + "', '" + id_corrida + "', '" + PAC + "', '" + modo_motor + "', '" + pneus + "', '" + 0 + "', '" +
                    0 + "', '" + 0 + "', '" + 0 + "');";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}