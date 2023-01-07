package Datalayer;

import SSCampeonato.Registo;
import SSCircuito.Zona;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ZonaDAO implements Map<Integer, Zona>
{
    private static ZonaDAO singleton = null;

    private ZonaDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static ZonaDAO getInstance()
    {
        if (ZonaDAO.singleton == null) {
            ZonaDAO.singleton = new ZonaDAO();
        }
        return ZonaDAO.singleton;
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
    public Zona get(Object key)
    {
        Zona z = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Zona WHERE Id = '" + key + "'")) {
            if (rs.next()) {
                z = new Zona((int) key, rs.getInt("Numero"), rs.getString("GDU"), rs.getString("Tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return z;
    }

    @Override
    public Zona put(Integer key, Zona value) {
        return null;
    }

    public Zona putNew(Zona z, int nome_circuito)
    {
        Zona res_zona = null;
        int id_registo = -1;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Zona \n" +
                    "(Numero, Tipo, GDU, Nome_Circuito) \n" +
                    "VALUES \n" +
                    "('" + z.getNumero() + "', '" + z.getTipo() + "', '" + z.getGDU() + "', '" + nome_circuito + "');";
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id_registo = rs.getInt(1);
            }
            z.setId(id_registo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_zona;
    }

    @Override
    public Zona remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Zona> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Zona> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, Zona>> entrySet() {
        return null;
    }
}