package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AgentieService;
import services.RezervareObserver;

import java.io.IOException;

public class LoginController{
    AgentieService service;
    private MainController ctrlMain;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField passwordFieldPassword;
    @FXML
    Button buttonLogin;

    public void setService(AgentieService service)
    {
        this.service = service;
    }

    @FXML
    public void initialize() {
        buttonLogin.setOnAction(e -> {
            login();
        });
    }

    private void login()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Main");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setWidth(850);
            dialogStage.setHeight(550);
            dialogStage.setScene(scene);

            Button buttonLogout = new Button();
            buttonLogout.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                    textFieldUsername.textProperty().setValue("");
                    passwordFieldPassword.textProperty().setValue("");
                }
            });

            ctrlMain = loader.getController();
            String h =  passwordFieldPassword.getText();
            service.login(Integer.parseInt(textFieldUsername.getText()),h,  ctrlMain);
            ctrlMain.setService(service, buttonLogout);
            dialogStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }
    /*
    public ServiceBilet getServiceBilet()
    {
        ApplicationContext factory = new ClassPathXmlApplicationContext("Meci.xml");
        ServiceBilet serviceBilet = factory.getBean(ServiceBilet.class);
        return serviceBilet;
    }

    public ServiceMeci getServiceMeci()
    {
        ApplicationContext factory = new ClassPathXmlApplicationContext("Meci.xml");
        ServiceMeci serviceMeci = factory.getBean(ServiceMeci.class);
        return serviceMeci;
    }
    */
}