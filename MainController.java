package gui;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Agent;
import model.Excursie;
import model.Rezervare;
import services.AgentieService;
import services.RezervareObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController extends UnicastRemoteObject implements RezervareObserver, Serializable {
    ObservableList<Excursie> modelgrade= FXCollections.observableArrayList();
    AgentieService as;

    @FXML
    TableView<Excursie> tableViewExcursie;
    @FXML
    TableView<Excursie> tableViewCauta;
    @FXML
    TableColumn<Excursie,Integer> tableColumnId1;
    @FXML
    TableColumn<Excursie,String> tableColumnPlecare1;
    @FXML
    TableColumn<Excursie,Double> tableColumnPret1;
    @FXML
    TableColumn<Excursie,Integer> tableColumnLocuri1;
    @FXML
    TableColumn<Excursie,String> tableColumnTransport1;
    @FXML
    TableColumn<Excursie,String> tableColumnNume1;
    @FXML
    TableColumn<Excursie,Integer> tableColumnId;
    @FXML
    TableColumn<Excursie,String> tableColumnPlecare;
    @FXML
    TableColumn<Excursie,Double> tableColumnPret;
    @FXML
    TableColumn<Excursie,Integer> tableColumnLocuri;
    @FXML
    TableColumn<Excursie,String> tableColumnTransport;
    @FXML
    TableColumn<Excursie,String> tableColumnNume;
    @FXML
    TextField textFieldId;
    @FXML
    TextField textFieldInainte;
    @FXML
    TextField textFieldDupa;
    @FXML
    Button buttonClose;
    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldTelefon;
    @FXML
    TextField textFieldBilete;

    public MainController() throws RemoteException {
    }


    @FXML
    public void initialize()
    {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("id"));
        tableColumnPlecare.setCellValueFactory(new PropertyValueFactory<Excursie,String>("plecare"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));
        tableColumnLocuri.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuri_disp"));
        tableColumnTransport.setCellValueFactory(new PropertyValueFactory<Excursie,String>("transport"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Excursie,String>("nume"));

        tableColumnId1.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("id"));
        tableColumnPlecare1.setCellValueFactory(new PropertyValueFactory<Excursie,String>("plecare"));
        tableColumnPret1.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));
        tableColumnLocuri1.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuri_disp"));
        tableColumnTransport1.setCellValueFactory(new PropertyValueFactory<Excursie,String>("transport"));
        tableColumnNume1.setCellValueFactory(new PropertyValueFactory<Excursie,String>("nume"));

        tableViewExcursie.setItems(modelgrade);

       buttonClose.setOnAction(e -> {
            buttonClose.fire();
        });

    }

    private void initData() {
        try {
            modelgrade.setAll((Collection<? extends Excursie>) as.getAllE());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setService(AgentieService service, Button buttonLogout)
    {
        this.as= service;
        initData();
        buttonClose = buttonLogout;
    }

    public void cauta() throws Exception {
        try {
            ObservableList<Excursie> l = FXCollections.observableArrayList();
            l.addAll(as.cauta(textFieldId.getText(), Integer.parseInt(textFieldDupa.getText()), Integer.parseInt(textFieldInainte.getText())));

            tableViewCauta.setItems(l);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void adauga() throws Exception
    {
        try{
            Iterable<Rezervare> l=as.getAll();
            Integer i=0;
            for (Rezervare x:l)
                i++;
            as.adauga(i+1,textFieldNume.getText(),textFieldTelefon.getText(),Integer.parseInt(textFieldBilete.getText()),tableViewExcursie.getSelectionModel().getSelectedItem(),new Agent(1,"pass"));

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void rezervareUpdate(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception {
        try{
            ObservableList<Excursie> obs = FXCollections.observableArrayList();
            for(Excursie e : modelgrade){
                if(e.getId().equals(ex.getId()))
                    obs.add(ex);
                else obs.add(e);
            }
            modelgrade.clear();
            modelgrade.addAll(obs);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
