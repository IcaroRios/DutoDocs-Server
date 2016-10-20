package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Gerenciador extends Remote {

    public int logar(String nome, String senha) throws RemoteException;

    public boolean deslogar(String nome, String senha) throws RemoteException;

    public String listarDocumentos() throws RemoteException;

    public boolean criarDocumento(String nome) throws RemoteException;

}
