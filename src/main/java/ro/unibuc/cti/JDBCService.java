package ro.unibuc.cti;

import jdk.jshell.execution.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

public class JDBCService implements FoodDeliveryDataService {

    private static JDBCService instance;

    private Connection conn;

    private JDBCService() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FoodDeliveryPlatform",
                    "alexandru", "Alex2000");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static JDBCService getInstance() {
        if (instance == null) {
            instance = new JDBCService();
        }
        return instance;
    }


    @Override
    public List<Cont> readConturi() {
        List<Cont> conturi = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from soferi;");
            while (rs.next()) {
                Sofer s = new Sofer(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("nrTel"),
                        rs.getString("nrInmatriculare"),
                        rs.getString("vehicul"),
                        rs.getString("oras"));

                conturi.add(s);
            }

            rs = stmt.executeQuery("select * from utilizatori;");
            while (rs.next()) {
                Utilizator u = new Utilizator(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("nrTel"),
                        rs.getString("adresa"));

                conturi.add(u);
            }

            rs = stmt.executeQuery("select * from localuri;");
            while (rs.next()) {
                int idLocal = rs.getInt("id");

                SortedMap<String, Integer> meniu = getMeniuForLocalWithId(idLocal);

                Local l = new Local(
                        idLocal,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("nrTel"),
                        rs.getString("adresa"),
                        meniu);

                conturi.add(l);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return conturi;
    }

    private SortedMap<String, Integer> getMeniuForLocalWithId(int id) {
        SortedMap<String, Integer> meniu = new TreeMap<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select numeProdus,pret from meniuri where idLocal=" + id);
            while (rs.next()) {
                meniu.put(rs.getString("numeProdus"), rs.getInt("pret"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meniu;
    }

    @Override
    public List<Comanda> readComenzi() {
        List<Comanda> comenzi = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from comenzi");

            while (rs.next()) {
                int idComanda = rs.getInt("idComanda");

                Map<String, Integer> produse = getProduseForComanda(idComanda);

                Comanda c = new Comanda(
                        idComanda,
                        rs.getInt("idUser"),
                        rs.getInt("idLocal"),
                        produse,
                        null
                );
                comenzi.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return comenzi;
    }

    private Map<String, Integer> getProduseForComanda(int idComanda) {
        Map<String, Integer> produse = new HashMap<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select produs,cantitate from produse_comenzi where idComanda=" + idComanda);
            while (rs.next()) {
                produse.put(rs.getString("produs"), rs.getInt("cantitate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return produse;
    }

    @Override
    public void writeConturi(List<Cont> listaConturi) {
        clearDataBase();
        try {
            Statement stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("insert into soferi values ");

            for (Cont c : listaConturi) {
                if (c instanceof Sofer){
                    Sofer s = (Sofer) c;
                    sql.append("(").append(s.getId()).append(",'").append(s.getUsername()).append("','").append(s.getPassword())
                            .append("','").append(s.getNume()).append("','").append(s.getPrenume()).append("','").append(s.getNrTel())
                            .append("','").append(s.getNrInmatriculare()).append("','").append(s.getVehicul()).append("','").append(s.getOras()).append("'),");
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            stmt.executeUpdate(sql.toString());

            sql = new StringBuilder("insert into localuri values ");
            StringBuilder sqlMeniu = new StringBuilder("insert into meniuri values ");

            for (Cont c : listaConturi) {
                if (c instanceof Local){
                    Local l = (Local) c;
                    sql.append("(").append(l.getId()).append(",'").append(l.getUsername()).append("','").append(l.getPassword())
                            .append("','").append(l.getNume()).append("','").append(l.getPrenume()).append("','").append(l.getNrTel())
                            .append("','").append(l.getAdresa()).append("'),");

                    SortedMap<String, Integer> meniu = l.getMeniu();

                    for (String prod : meniu.keySet()) {
                        sqlMeniu.append("(").append(l.getId()).append(",'").append(prod).append("',").append(meniu.get(prod)).append("),");
                    }
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            sqlMeniu.deleteCharAt(sqlMeniu.length() - 1);
            stmt.executeUpdate(sql.toString());
            stmt.executeUpdate(sqlMeniu.toString());

            sql = new StringBuilder("insert into utilizatori values ");

            for (Cont c : listaConturi) {
                if (c instanceof Utilizator){
                    Utilizator u = (Utilizator) c;
                    sql.append("(").append(u.getId()).append(",'").append(u.getUsername()).append("','").append(u.getPassword())
                            .append("','").append(u.getNume()).append("','").append(u.getPrenume()).append("','").append(u.getNrTel())
                            .append("','").append(u.getAdresa()).append("'),");
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            stmt.executeUpdate(sql.toString());


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearDataBase() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("delete from produse_comenzi");
            stmt.execute("delete from meniuri");
            stmt.execute("delete from comenzi");
            stmt.execute("delete from soferi");
            stmt.execute("delete from utilizatori");
            stmt.execute("delete from localuri");
            stmt.execute("commit");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void writeComenzi(List<Comanda> listaComenzi) {
        try {
            Statement stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("insert into comenzi values ");
            StringBuilder sqlProduse = new StringBuilder("insert into produse_comenzi values ");

            for (Comanda c : listaComenzi) {
                sql.append("(").append(c.getIdComanda()).append(",").append(c.getIdUser()).append(",").append(c.getIdLocal())
                .append(",").append(c.getIdSofer()).append(",'").append(java.sql.Date.valueOf(c.getData())).append("','").append(c.getStatusComanda().toString())
                .append("'),");

                Map<String, Integer> produseComandate = c.getProduseComandate();

                for (String prod : produseComandate.keySet()) {
                    sqlProduse.append("(").append(c.getIdComanda()).append(",'").append(prod)
                            .append("',").append(produseComandate.get(prod)).append("),");
                }
            }

            if (!sql.toString().equals("insert into comenzi values ")){
                sql.deleteCharAt(sql.length() - 1);
//                System.out.println(sql);
//                System.out.println();
//                System.out.println();
                stmt.execute(sql.toString());
            }

            if (!sqlProduse.toString().equals("insert into produse_comenzi values ")){
                sqlProduse.deleteCharAt(sqlProduse.length() - 1);
//                System.out.println(sqlProduse);
//                System.out.println();
                stmt.execute(sqlProduse.toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
