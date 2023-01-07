package Datalayer;

import SSCampeonato.Registo;
import SSCarro.Carro;
import SSCarro.Hibrido;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RegistoDAO implements Map<Integer, Registo>
{
    private static RegistoDAO singleton = null;

    private RegistoDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static RegistoDAO getInstance()
    {
        if (RegistoDAO.singleton == null) {
            RegistoDAO.singleton = new RegistoDAO();
        }
        return RegistoDAO.singleton;
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
    public Registo get(Object key) {
        Registo reg = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Registo WHERE Id = '" + key + "'")) {
            if (rs.next())
            {
                int id_registo = (int) key;
                float tempo = 0;
                float tempo_antes = 0;
                int pontuacao_camp = rs.getInt("PontuacaoCamp");
                int id_carro = rs.getInt("Id_Carro");
                String nome_jogador = rs.getString("Nome_Jogador");
                String nome_piloto = rs.getString("Nome_Piloto");

                reg = new Registo(id_registo, tempo, tempo_antes, pontuacao_camp, id_carro, nome_jogador, nome_piloto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return reg;
    }

    @Override
    public Registo put(Integer key, Registo value) {
        return null;
    }

    public Registo putNew(Registo r, String nome_campeonato)
    {
        Registo res_registo = null;
        int id_registo = -1;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Registo \n" +
                    "(Nome_Jogador, Nome_Campeonato, Nome_Piloto, Id_Carro, PontuacaoCamp) \n" +
                    "VALUES \n" +
                    "('" + r.getNome_jogador() + "', '" + nome_campeonato + "', '" + r.getNome_piloto() + "', '" + r.getId_carro() + "', '" + r.getPontuacao() + "');";
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id_registo = rs.getInt(1);
            }
            r.setId(id_registo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_registo;
    }

    public void setTempoPosicaoPontuacaoCarroCorrida(int id_registo, int id_corrida, float tempo, int posicao, int posicaoCategoria, int pontuacaoCorrida)
    {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "UPDATE Registo_Corrida \n" +
                    "SET Tempo = '" + tempo + "', Posicao = '" + posicao + "', PosCategoria = '" + posicaoCategoria + "', Pontuacao = '" + pontuacaoCorrida + "'\n" +
                    "WHERE Id_Registo = '" + id_registo + "' AND Id_Corrida = '" + id_corrida + "';";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPontuacaoRegisto(int id_registo, int pontuacao_camp)
    {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "UPDATE Registo \n" +
                    "SET PontuacaoCamp = '" + pontuacao_camp + "'\n" +
                    "WHERE Id = '" + id_registo + "';";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Registo remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Registo> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Registo> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, Registo>> entrySet() {
        return null;
    }
}