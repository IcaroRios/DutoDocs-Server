package rmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

public class GerenciadorImple extends UnicastRemoteObject implements Gerenciador {

    private final LinkedList<Usuario> usuarios;
    private final String caminho;

    public GerenciadorImple() throws RemoteException {
        super();
        caminho = "Documentos" + File.separator;
        this.usuarios = new LinkedList<>();
        usuarios.add(new Usuario("icaro", "rios"));
        usuarios.add(new Usuario("thiago", "beiga"));
        usuarios.add(new Usuario("minha", "mae"));

    }

    @Override
    public int logar(String nome, String senha) throws RemoteException {
        /**
         * retorna 0 se o usuario já estiver online. retorna 1 se o usuario foi
         * logado com sucesso. retorna 2 se o usuario não está cadastrado.
         */

        //usuario auxilar para comparação
        Usuario aux = new Usuario(nome, senha);

        //pecorrendo a lista, para verificar se o usuario existe
        for (Usuario u : usuarios) {
            if (u.equals(aux)) {
                //caso o usuario exista, verifica se ele já está online.
                if (u.isIsOnline()) {
                    //usuario online
                    return 0;
                } else {
                    //logando o usuario
                    u.setIsOnline(true);
                    //logado com sucesso
                    return 1;
                }
            }
        }
        //usuario não existente
        return 2;
    }

    @Override
    public boolean deslogar(String nome) throws RemoteException {
        /**
         * retorna verdadeiro quando o char foi deslogado com sucesso. retorna
         * false quando o usuario já estava offline ou não está cadastrado.
         */

        //usuario auxilar para comparação
        //pecorrendo a lista, para verificar se o usuario existe
        for (Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) {
                //caso o usuario exista, verifica se ele já está online.
                if (u.isIsOnline()) {
                    //deslogando o usuario.
                    u.setIsOnline(false);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean criarDocumento(String nome) throws RemoteException {
        try {
            /*
             PrintWriter writer = new PrintWriter(nome+".txt", "UTF-8");            
             writer.close();
             */
            File f = new File(caminho + nome + ".txt");
            if (!f.exists() && !f.isDirectory()) {
                f.createNewFile();
                return true;
            }
            return false;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    public String listarDocumentos() throws RemoteException {
        String informacao = "";
        try {
            File folder = new File(caminho);

            File[] listOfFiles = folder.listFiles();

            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    informacao += listOfFile.getName() + ";";
                }
            }
        } catch (Exception ex) {
            return "";
        }
        return informacao;
    }

    @Override
    public String abrirArquivo(String nome) throws RemoteException {
        BufferedReader br;
        String texto = "";
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("Documentos" + File.separator + nome), "UTF-8"));
           
           String linha = br.readLine();
           while(linha!= null){               
               texto +=linha + '\n';
               linha = br.readLine();
           }           
           br.close();
           return texto;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GerenciadorImple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerenciadorImple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorImple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    
}
