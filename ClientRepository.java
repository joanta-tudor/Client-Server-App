package repository;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import model.Client;
import org.apache.logging.log4j.Logger;
import org.sqlite.JDBC;
import utils.AppContext;

public class ClientRepository implements RepositoryDB<Client> {
    Connection connect=null;
    private static Logger numeLogger= LogManager.getLogger(ClientRepository.class);

    public ClientRepository(){ConnectToDB();}

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
    public void adauga(Client c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("insert into Clienti values (?,?,?,?)");
        ps.setInt(1,c.getId());
        ps.setString(2,c.getNume());
        ps.setString(3,c.getTelefon());
        ps.setInt(4,c.getBilete());
        ps.executeUpdate();
        numeLogger.info("Adaugat");
    }

    @Override
    public Iterable<Client> getAll()
    {
        List<Client> l=new ArrayList<>();
        try{
            PreparedStatement ps=connect.prepareStatement("select * from Clienti");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                l.add(new Client(rs.getInt("id"),rs.getString("nume"),rs.getString("telefon"),rs.getInt("bilete")));
        }catch(SQLException e) {
            System.out.println(e);
        }
        numeLogger.info("Selectat");
        return l;
    }

    @Override
    public void sterge(Client c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("delete from Clienti where id=?");
        ps.setInt(1,c.getId());
        ps.executeUpdate();
        numeLogger.info("Sters");
    }

    @Override
    public void update(Client c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("update Clienti set nume=? where id=?");
        ps.setString(1,c.getNume());
        ps.setInt(2,c.getId());
        ps.executeUpdate();
        numeLogger.info("Updatat");
    }
}
