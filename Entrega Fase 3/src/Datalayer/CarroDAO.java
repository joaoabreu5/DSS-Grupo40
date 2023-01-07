package Datalayer;

import RacingManagerLN.Administrador;
import SSCarro.*;

import java.sql.*;
import java.util.*;

public class CarroDAO implements Map<Integer, Carro>
{
    private static CarroDAO singleton = null;

    private CarroDAO()
    {
        DAOconfig.DAO_CreateTables();
    }

    public static CarroDAO getInstance()
    {
        if (CarroDAO.singleton == null) {
            CarroDAO.singleton = new CarroDAO();
        }
        return CarroDAO.singleton;
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
    public Carro get(Object key) {
        int potencia_eletrica = 0;
        Carro carro = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Carro WHERE Id = '" + key + "'")) {
            if (rs.next()) {
                int id = (int) key;
                String categoria = rs.getString("Categoria");
                String marca = rs.getString("Marca");
                String modelo = rs.getString("Modelo");
                int cilindrada = rs.getInt("Cilindrada");
                int potenciaICE = rs.getInt("PotenciaICE");

                if (categoria.endsWith("H"))
                {
                    String sql = "SELECT * FROM Hibrido WHERE Id_Carro = '" + key + "'";
                    try (ResultSet rsh = stm.executeQuery(sql))
                    {
                        if (rsh.next()) {
                            potencia_eletrica = rsh.getInt("PotenciaEletrica");
                        }
                    }
                }

                float fiablidade;
                switch(categoria)
                {
                    case "C1":
                        carro = new C1(id, marca, modelo, cilindrada, potenciaICE, 0.95F, 0.5F, "Normal", "Macio", 0, false);
                        break;
                    case "C2":
                        fiablidade = (float) (0.8*(1+cilindrada/100000));
                        carro = new C2(id, marca, modelo, cilindrada, potenciaICE, fiablidade, 0.5F, "Normal", "Macio", 0, false);
                        break;
                    case "GT":
                        carro = new GT(id, marca, modelo, cilindrada, potenciaICE, 1, 0.5F, "Normal", "Macio", 0, false);
                        break;
                    case "SC":
                        carro = new SC(id, marca, modelo, cilindrada, potenciaICE, 1, 0.5F, "Normal", "Macio", 0, false);
                        break;
                    case "C1H":
                        fiablidade = 0.9F;
                        carro = new C1H(id, marca, modelo, cilindrada, potenciaICE, fiablidade, 0.5F, "Normal", "Macio", 0, false, potencia_eletrica);
                        break;
                    case "C2H":
                        fiablidade = (float) (0.75*(1+cilindrada/100000));
                        carro = new C2H(id, marca, modelo, cilindrada, potenciaICE, fiablidade, 0.5F, "Normal", "Macio", 0, false, potencia_eletrica);
                        break;
                    case "GTH":
                        fiablidade = 0.90F;
                        carro = new GTH(id, marca, modelo, cilindrada, potenciaICE, fiablidade, 0.5F, "Normal", "Macio", 0, false, potencia_eletrica);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return carro;
    }

    @Override
    public Carro put(Integer key, Carro value) {
        return null;
    }

    public Carro putNew(Carro c)
    {
        Carro res_carro = null;

        int id_carro = -1;
        String categoria = c.getCategoria();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "INSERT INTO Carro \n" +
                    "(Marca, Modelo, Categoria, Cilindrada, PotenciaICE) \n" +
                    "VALUES \n" +
                    "('" + c.getMarca() + "', '" + c.getModelo() + "', '" + categoria + "', '" + c.getCilindrada() + "', '" + c.getPotenciaICE() + "');";
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id_carro = rs.getInt(1);
            }
            c.setId(id_carro);

            if (c instanceof Hibrido)
            {
                sql = "INSERT INTO Hibrido \n" +
                        "VALUES \n" +
                        "(" + id_carro + ", '" + ((Hibrido) c).getPotenciaMotorEletrico() + "');";
                stm.executeUpdate(sql);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res_carro;
    }


    @Override
    public Carro remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Carro> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Carro> values() {
        Collection<Carro> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL_DB, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM Carro")) {
            while (rs.next()) {
                int id_carro = rs.getInt("Id");
                Carro carro = this.get(id_carro);
                res.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<Integer, Carro>> entrySet() {
        return null;
    }
}