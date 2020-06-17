package rpcProtocol;

import dto.*;
import model.Excursie;
import model.Rezervare;
import services.AgentieService;
import services.RezervareObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class AgentieClientRpcReflectionWorker implements Runnable, RezervareObserver {
    private AgentieService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public  AgentieClientRpcReflectionWorker(AgentieService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        UserDTO udto = (UserDTO)request.data();
        try {
            server.login(Integer.parseInt(udto.getId()), udto.getPasswd(), this);
            return okResponse;
        } catch (Exception e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_Excursie(Request request){
        System.out.println("Login request ..."+request.type());

        try {
            ExcursieDTO meciuriDTO = new ExcursieDTO((List<Excursie>) server.getAllE());
            return new Response.Builder().type(ResponseType.GET_Rezervare).data(meciuriDTO).build();
        } catch (Exception e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_REZERVARE(Request request)
    {
        System.out.println("Login request ..."+request.type());

        try {
            RezervDTO meciuriDTO = new RezervDTO((List<Rezervare>) server.getAll());
            return new Response.Builder().type(ResponseType.GET_REZERVARE).data(meciuriDTO).build();
        } catch (Exception e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADAUGA(Request request)
    {
        System.out.println("Login request ..."+request.type());
        RezervareDTO r= (RezervareDTO) request.data();
        try{
            server.adauga(r.getId(),r.getNume(),r.getTelefon(),r.getBilet(),r.getEx(),r.getAg());
            //rezervareUpdate(r.getId(),r.getNume(),r.getTelefon(),r.getBilet(),r.getEx(),r.getAg());
            return new Response.Builder().type(ResponseType.ADAUG).data(r).build();
        }catch(Exception e) {connected=false; return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();}
    }

    private Response handleSELL_BILETE(Request request){
        System.out.println("Login request ..."+request.type());
        IntervalDTO vanzareDTO = (IntervalDTO) request.data();
        try {
           List<Excursie> l= server.cauta(vanzareDTO.getNume(),vanzareDTO.getInterval1(),vanzareDTO.getInterval2());
            Response r=new Response.Builder().type(ResponseType.SOLD_BILETE).data(new CautaDTO(l)).build();
            return r;
        } catch (Exception e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }


    @Override
    public void rezervareUpdate(Integer id, String nume, String telefon, Integer bilet, model.Excursie ex, model.Agent ag) throws Exception {
        Response resp=new Response.Builder().type(ResponseType.UPDATE).data(new RezervareDTO(id,nume,telefon,bilet,ex,ag)).build();
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new Exception("Sending error: "+e);
        }
    }
}
