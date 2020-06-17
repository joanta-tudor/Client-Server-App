package server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Agent;
import model.Excursie;
import model.Rezervare;
import repository.AgentRepository;
import repository.ExcursieRepository;
import repository.RezervareRepository;
import services.AgentieService;
import services.RezervareObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentieServiceImpl implements AgentieService {
    private AgentRepository ar;
    private ExcursieRepository er;
    private RezervareRepository rr;
    private List<RezervareObserver> observers;

    public AgentieServiceImpl(AgentRepository ar,ExcursieRepository er,RezervareRepository rr) {
        this.ar=ar;
        this.er=er;
        this.rr=rr;
        observers=new ArrayList<>();
    }

    @Override
    public Iterable<Rezervare> getAll() throws Exception {
        List<Rezervare> r=new ArrayList<>();
        for(Rezervare re :rr.getAll())
        {
            r.add(re);
        }
        return r;
    }

    @Override
    public Iterable<Excursie> getAllE() throws Exception {
        List<Excursie> l=new ArrayList<>();
        for (Excursie e:er.getAll())
            l.add(e);
        return l;
    }

    @Override
    public List<Excursie> cauta(String nume, Integer intre1, Integer intre2) throws Exception {
        List<Excursie> l= new ArrayList<>();
        for(Excursie i: er.getAll())
        {
            if (i.getNume().equals(nume) && intre1<=Integer.parseInt(i.getPlecare()) && intre2>=Integer.parseInt(i.getPlecare()))
                l.add(i);
        }
        return l;
    }

    @Override
    public void adauga(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception {
        if(bilet>ex.getLocuri_disp())
            throw new Exception("Prea mult");
        rr.adauga(new Rezervare(id,nume,telefon,bilet,ag.getId(),ex.getId()));
        ex.setLocuri_disp(ex.getLocuri_disp()-bilet);
        er.update(ex);
        notify(id,nume,telefon,bilet,ex,ag);
    }

    @Override
    public boolean login(Integer id, String pass, RezervareObserver obs) throws Exception {
        for(Agent a:ar.getAll())
            if(a.getId().equals(id) && a.getParola().equals(pass))
            {
                observers.add(obs);
                return true;
            }
        System.out.println(id);
        System.out.println(pass);

        throw new Exception("Autentificare gresita");
    }

    private final int defaultThreadsNo=5;

    private void notify(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(RezervareObserver o: observers) {
            executor.execute(() -> {
                try {
                    o.rezervareUpdate(id,  nume, telefon,  bilet, ex, ag);
                } catch (Exception e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }
        executor.shutdown();
    }
}
