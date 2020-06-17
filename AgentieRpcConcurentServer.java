package utils;

import rpcProtocol.AgentieClientRpcReflectionWorker;
import services.AgentieService;

import java.net.Socket;

public class AgentieRpcConcurentServer extends AbsConcurentServer{
    private AgentieService chatServer;
    public AgentieRpcConcurentServer(int port, AgentieService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        AgentieClientRpcReflectionWorker worker=new AgentieClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
