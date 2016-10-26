package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Gerenciador extends Remote {

    public int logar(String nome, String senha) throws RemoteException;

    public boolean deslogar(String nome) throws RemoteException;

    public String listarDocumentos() throws RemoteException;

    public boolean criarDocumento(String nome) throws RemoteException;
    
    public String abrirArquivo(String nome) throws  RemoteException;

    public String escreverArquivo(String arquivo,String texto) throws RemoteException;
}
