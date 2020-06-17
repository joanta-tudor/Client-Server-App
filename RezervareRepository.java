package repository;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import model.Rezervare;
import org.apache.logging.log4j.Logger;
import org.sqlite.JDBC;
import utils.AppContext;

public class RezervareRepository implements RepositoryDB<Rezervare>{
    Connection connect=null;
    private static Logger numeLogger= LogManager.getLogger(RezervareRepository.class);

    public RezervareRepository(){ConnectToDB();}

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
    public void adauga(Rezervare r) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("insert into Rezervare values (?,?,?,?,?,?)");
        ps.setInt(1,r.getId());
        ps.setInt(2,r.getAgent_id());
        ps.setInt(3,r.getExcursie_id());
        ps.setString(4,r.getNumeC());
        ps.setString(5,r.getTelefonC());
        ps.setInt(6,r.getBileteC());

        ps.executeUpdate();
        numeLogger.info("Adaugat");
    }

    @Override
    public Iterable<Rezervare> getAll()
    {
        List<Rezervare> l=new ArrayList<>();
        try{
            PreparedStatement ps=connect.prepareStatement("select * from Rezervare");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                l.add(new Rezervare(rs.getInt("id"),rs.getString("numeC"),rs.getString("telefonC"),rs.getInt("bileteC"),rs.getInt("agent_id"),rs.getInt("excursie_id")));
        }catch(SQLException e) {
            System.out.println(e);
        }
        numeLogger.info("Selectat");
        return l;
    }

    @Override
    public void sterge(Rezervare r) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("delete from Rezervare where id=?");
        ps.setInt(1,r.getId());
        ps.executeUpdate();
        numeLogger.info("Sters");
    }

    @Override
    public void update(Rezervare r) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("update Rezervare set bileteC=? where id=?");
        ps.setInt(1,r.getBileteC());
        ps.setInt(2,r.getId());
        ps.executeUpdate();
        numeLogger.info("Updatat");
    }
}
