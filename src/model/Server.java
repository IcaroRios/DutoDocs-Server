package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.GerenciadorImple;

public class Server {
    
    private final GerenciadorImple gerenciador;

    public Server() throws RemoteException {
        this.gerenciador = new GerenciadorImple();  
    }
    
    public void iniciar(String ip) throws RemoteException, MalformedURLException{
        Registry r = LocateRegistry.createRegistry(3322);
        Naming.rebind("//"+ ip + ":3322/gerenciador", gerenciador);
        
    }
    
}
