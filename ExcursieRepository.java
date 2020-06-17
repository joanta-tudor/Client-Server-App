package repository;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import model.Excursie;
import org.apache.logging.log4j.Logger;
import org.sqlite.JDBC;
import utils.AppContext;

public class ExcursieRepository implements RepositoryDB<Excursie>{
    Connection connect=null;
    private static Logger numeLogger= LogManager.getLogger(ExcursieRepository.class);

    public ExcursieRepository(){ConnectToDB();}

    public void ConnectToDB()
    {
        try{
            DriverManager.registerDriver(new JDBC());
            connect=DriverManager.getConnection(AppContext.getProp().getProperty("url"));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        numeLogger.info("Conectat");
    }

    @Override
    public void adauga(Excursie c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("insert into Excursie values (?,?,?,?,?,?)");
        ps.setInt(1,c.getId());
        ps.setString(2,c.getPlecare());
        ps.setDouble(3,c.getPret());
        ps.setInt(4,c.getLocuri_disp());
        ps.setString(5,c.getTransport());
        ps.setString(6,c.getNume());
        ps.executeUpdate();
        numeLogger.info("Adaugat");
    }

    @Override
    public Iterable<Excursie> getAll()
    {
        List<Excursie> l=new ArrayList<>();
        try{
            PreparedStatement ps=connect.prepareStatement("select * from Excursie");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                l.add(new Excursie(rs.getInt("id"),rs.getString("plecare"),rs.getDouble("pret"),rs.getInt("locuri_disp"),rs.getString("agentie_transport"),rs.getString("nume")));
        }catch(SQLException e) {
            System.out.println(e);
        }
        numeLogger.info("Selectat");
        return l;
    }

    @Override
    public void sterge(Excursie c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("delete from Excursie where id=?");
        ps.setInt(1,c.getId());
        ps.executeUpdate();
        numeLogger.info("Sters");
    }

    @Override
    public void update(Excursie c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("update Excursie set locuri_disp=? where id=?");
        ps.setInt(1,c.getLocuri_disp());
        ps.setInt(2,c.getId());
        ps.executeUpdate();
        numeLogger.info("Updatat");
    }
}
