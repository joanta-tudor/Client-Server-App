import gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpcProtocol.AgentieServiceRpcProxy;
import services.AgentieService;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/agentieclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("meciuri.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("meciuri.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

       // AgentieService server = new AgentieServiceRpcProxy(serverIP, serverPort);

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client.xml");
        AgentieService server=(AgentieService) factory.getBean("chatService");
        System.out.println("Obtained a reference to remote chat server");

        primaryStage.setTitle("Login");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        Pane myPane = (Pane) loader.load();
        LoginController ctrl = loader.getController();

        ctrl.setService(server);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(550);
        primaryStage.show();
    }
}
