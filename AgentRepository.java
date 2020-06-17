package repository;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Agent;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.sqlite.JDBC;
import utils.AppContext;

import static utils.AppContext.getProp;

public class AgentRepository implements RepositoryDB<Agent>{
    Connection connect=null;
    private static Logger numeLogger= LogManager.getLogger(AgentRepository.class);

    public AgentRepository(){ConnectToDB();}

    public void ConnectToDB()
    {
        try{
            DriverManager.registerDriver(new JDBC());
            String s=AppContext.getProp().getProperty("url");
            connect=DriverManager.getConnection(s);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        numeLogger.info("Conectat");
    }

    @Override
    public void adauga(Agent a) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("insert into Agent values (?,?)");
        ps.setInt(1,a.getId());
        ps.setString(2,a.getParola());
        ps.executeUpdate();
        numeLogger.info("Adaugat");
    }

    @Override
    public Iterable<Agent> getAll()
    {
        List<Agent> l=new ArrayList<>();
        try{
            PreparedStatement ps=connect.prepareStatement("select * from Agent");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                l.add(new Agent(rs.getInt("id"),rs.getString("parola")));
        }catch(SQLException e) {
            System.out.println(e);
        }
        numeLogger.info("Selectat");
        return l;
    }

    @Override
    public void sterge(Agent a) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("delete from Agent where id=?");
        ps.setInt(1,a.getId());
        ps.executeUpdate();
        numeLogger.info("Sters");
    }

    @Override
    public void update(Agent c) throws SQLException {
        PreparedStatement ps=connect.prepareStatement("update Agent set parola=? where id=?");
        ps.setString(1,c.getParola());
        ps.setInt(2,c.getId());
        ps.executeUpdate();
        numeLogger.info("Updatat");
    }
}
