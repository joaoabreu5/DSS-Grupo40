package Datalayer;

import RacingManagerLN.Piloto;

import java.sql.*;
import java.util.*;

public class PilotoDAO implements Map<String, Piloto>
{
    private static PilotoDAO singleton = null;

    private PilotoDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static PilotoDAO getInstance()
    {
        if (PilotoDAO.singleton == null) {
            PilotoDAO.singleton = new PilotoDAO();
        }
        return PilotoDAO.singleton;
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
    public Piloto get(Object key) {
        Piloto p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Piloto WHERE Nome = '" + key + "'")) {
            if (rs.next()) {
                p = new Piloto((String) key, rs.getFloat("CTS"), rs.getFloat("SVA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Piloto put(String key, Piloto p) {
        Piloto res_piloto = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Piloto \n" +
                    "VALUES \n" +
                    "('" + key + "', '" + p.getCTS() + "', '" + p.getSVA() + "');";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_piloto;
    }

    @Override
    public Piloto remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Piloto> values() {
        List<Piloto> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Nome FROM Piloto")) {
            while (rs.next()) {
                String id_piloto = rs.getString("Nome");
                Piloto p = this.get(id_piloto);
                res.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Piloto>> entrySet() {
        return null;
    }
}