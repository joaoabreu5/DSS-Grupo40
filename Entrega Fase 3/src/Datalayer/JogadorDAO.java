package Datalayer;

import RacingManagerLN.Administrador;
import RacingManagerLN.Jogador;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class JogadorDAO implements Map<String, Jogador>
{
    private static JogadorDAO singleton = null;

    private JogadorDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static JogadorDAO getInstance()
    {
        if (JogadorDAO.singleton == null) {
            JogadorDAO.singleton = new JogadorDAO();
        }
        return JogadorDAO.singleton;
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
    public Jogador get(Object key) {
        Jogador jog = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Jogador WHERE Nome = '" + key + "'")) {
            if (rs.next()) {

                if (rs.getBoolean("Administrador"))
                {
                    jog = new Administrador(rs.getString("Nome"), rs.getString("Password"), rs.getInt("PontuacaoGlobal"),
                            false, rs.getString("VersaoJogo"));
                }
                else
                {
                    jog = new Jogador(rs.getString("Nome"), rs.getString("Password"), rs.getInt("PontuacaoGlobal"),
                            false, rs.getString("VersaoJogo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return jog;
    }

    @Override
    public Jogador put(String key, Jogador j)
    {
        Jogador res_jog = null;

        String administrador = "FALSE";
        if (j.isAdministrador())
            administrador = "TRUE";

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Jogador \n" +
                    "VALUES \n" +
                    "('" + key + "',  '" + j.getPassword() + "', '" + j.getPontuacao() + "', '" + j.getVersao() + "', " + administrador + ");";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_jog;
    }

    @Override
    public Jogador remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Jogador> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Jogador> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Jogador>> entrySet() {
        return null;
    }
}