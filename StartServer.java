import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.AgentRepository;
import repository.ExcursieRepository;
import repository.RezervareRepository;
import server.AgentieServiceImpl;
import services.AgentieService;
import utils.AbstractServer;
import utils.AgentieRpcConcurentServer;
import utils.AppContext;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        /*
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/agentieServer.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }


        AgentieService meciServicesImpl = new AgentieServiceImpl(new AgentRepository(),new ExcursieRepository(),new RezervareRepository());



        int meciuriServerPort=defaultPort;
        try {
            meciuriServerPort = Integer.parseInt(serverProps.getProperty("meciuri.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+meciuriServerPort);
        AbstractServer server = new AgentieRpcConcurentServer(meciuriServerPort, meciServicesImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }*/
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        System.out.println("Waiting for clients");
    }
}
