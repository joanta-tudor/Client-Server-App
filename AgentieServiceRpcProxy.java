package rpcProtocol;

import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Agent;
import model.Excursie;
import model.Rezervare;
import services.AgentieService;
import services.RezervareObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AgentieServiceRpcProxy implements AgentieService {
    private String host;
    private int port;

    private RezervareObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public AgentieServiceRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public Iterable<Rezervare> getAll() throws Exception {
        Request req=new Request.Builder().type(RequestType.GET_REZERVARE).data().build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
       RezervDTO r= (RezervDTO) response.data();
        return r.getR();
    }

    @Override
    public Iterable<Excursie> getAllE() throws Exception {
        Request req=new Request.Builder().type(RequestType.GET_Excursie).data().build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
        ExcursieDTO meciuriDTO = (ExcursieDTO)response.data();
        return meciuriDTO.getExc();
    }

    @Override
    public List<Excursie> cauta(String nume, Integer intre1, Integer intre2) throws Exception {

        Request req=new Request.Builder().type(RequestType.SELL_BILETE).data(new IntervalDTO(nume,intre1,intre2)).build();
        this.sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
        CautaDTO c= (CautaDTO) response.data();
        List<Excursie> l = c.getL();
        return l;
    }

    @Override
    public void adauga(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception {
        Request req=new Request.Builder().type(RequestType.ADAUGA).data(new RezervareDTO(id,nume,telefon,bilet,ex,ag)).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
    }

    @Override
    public boolean login(Integer id, String pass, RezervareObserver obs) throws Exception {
        initializeConnection();
        UserDTO udto= new UserDTO(id.toString(),pass);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=obs;
            return true;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
        return false;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            //client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object "+e);
        }

    }

    private Response readResponse() throws Exception {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws Exception {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.UPDATE;
    }

    private void handleUpdate(Response response){

        RezervareDTO r= (RezervareDTO) response.data();
        try {
            client.rezervareUpdate(r.getId(),r.getNume(),r.getTelefon(),r.getBilet(),r.getEx(),r.getAg());
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){

                        handleUpdate((Response)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
