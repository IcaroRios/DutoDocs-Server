package controller;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import model.Server;

public class Controller {
    
    private final Server servidor;

    public Controller() throws RemoteException {
        this.servidor = new Server();
    }
    
    public void inciar(String ip) throws RemoteException, MalformedURLException{
        servidor.iniciar(ip);
    }
    
}
