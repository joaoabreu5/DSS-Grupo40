package Datalayer;

import RacingManagerLN.Jogador;
import SSCircuito.Circuito;

import java.sql.*;
import java.util.*;

public class CircuitoDAO implements Map<String, Circuito> {
    private static CircuitoDAO singleton = null;

    private CircuitoDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static CircuitoDAO getInstance()
    {
        if (CircuitoDAO.singleton == null) {
            CircuitoDAO.singleton = new CircuitoDAO();
        }
        return CircuitoDAO.singleton;
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
    public Circuito get(Object key)
    {
        Circuito circ = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Circuito WHERE Nome = '" + key + "'")) {
            if (rs.next()) {
                String nome = (String) key;
                int curvas = rs.getInt("Curvas");
                int chicanes = rs.getInt("Chicanes");
                int retas = rs.getInt("Retas");
                int comprimento = rs.getInt("Comprimento");

                List<Integer> zonas = new ArrayList<>();

                String sql = "SELECT * FROM Zona WHERE Nome_Circuito = '" + key + "'";
                try (ResultSet rsz = stm.executeQuery(sql)) {
                    while (rsz.next()) {
                        zonas.add(rsz.getInt("Id"));
                    }
                }
                circ = new Circuito(nome, curvas, chicanes, retas, comprimento, zonas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return circ;
    }

    @Override
    public Circuito put(String key, Circuito cir)
    {
        Circuito res_cir = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Circuito \n" +
                    "VALUES \n" +
                    "('" + key + "', '" + cir.getComprimento() + "', '" + cir.getRetas() + "', '" + cir.getCurvas() + "', '" + cir.getChicanes() + "');";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_cir;
    }

    @Override
    public Circuito remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Circuito> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Circuito> values() {
        List<Circuito> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Nome FROM Circuito")) {
            while (rs.next()) {
                String id_circuito = rs.getString("Nome");
                Circuito circ = this.get(id_circuito);
                res.add(circ);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        return null;
    }
}