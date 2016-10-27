package rmi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import model.Arquivo;
import model.Linha;
import model.Usuario;

public class GerenciadorImple extends UnicastRemoteObject implements Gerenciador {

    private final LinkedList<Usuario> usuarios;
    private final String caminho;
    private LinkedList<Arquivo> arquivos;
    private LinkedList<Linha> linhas;

    public GerenciadorImple() throws RemoteException {
        super();
        caminho = "Documentos" + File.separator;
        this.usuarios = new LinkedList<>();
        this.arquivos = new LinkedList<>();
        this.linhas = new LinkedList<>();
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

                    Arquivo novo = new Arquivo(listOfFile.getName());
                    boolean existente = false;
                    for (Arquivo atual : arquivos) {
                        if (atual.equals(novo)) {
                            existente = true;
                        }
                    }
                    if (!existente) {
                        arquivos.add(novo);
                    }
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

            while (br.ready()) {
                texto += br.readLine() + "\n";
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

    @Override
    public String escreverArquivo(String arquivo, String texto, int linha, String login) throws RemoteException {
        Usuario editor = buscarUsuario(login);
        Arquivo documento = buscarArquivo(arquivo);
        Linha editada = buscarLinha(editor, documento);
        if (editada.getUsuario().equals(editor) && editada.getPosicao() == linha) {
            System.out.println("inserindo");

            try {
                FileWriter fileW = new FileWriter(new File("Documentos" + File.separator + arquivo));//arquivo para escrita
                BufferedWriter buffW = new BufferedWriter(fileW);
                buffW.write(texto);

                buffW.close();
                fileW.close();
            } catch (IOException ex) {
                System.out.println("erro com o arquivo");
            }

        } else {
            System.out.println("usuario não pode editar a linha");
        }

        return null;
    }

    @Override
    public void pedirLinhaArquivo(int linha, String login, String arquivo) throws RemoteException {
        Usuario editor = buscarUsuario(login);
        Arquivo solocitado = buscarArquivo(arquivo);
        Linha resultado = buscarLinha(editor, solocitado);
        if (resultado == null) {
            resultado = new Linha(editor, solocitado, linha);
            linhas.add(resultado);
            print("linha nova para este usuario e arquivo");
        } else {
            boolean temDono = false;
            for (Linha atual : linhas) {
                if (atual.getPosicao() == linha && !atual.getUsuario().equals(editor)) {
                    temDono = true;
                    print("posicao já tem um dono");
                }
            }
            if (!temDono) {
                resultado.setPosicao(linha);
                print("posicao da linha alterada");
            }
        }

    }

    private Usuario buscarUsuario(String login) {
        for (Usuario atual : usuarios) {
            if (atual.getNome().equalsIgnoreCase(login)) {
                return atual;
            }
        }
        return null;
    }

    private Arquivo buscarArquivo(String arquivo) {
        for (Arquivo atual : arquivos) {
            if (atual.getNome().equalsIgnoreCase(arquivo)) {
                return atual;
            }
        }
        return null;
    }

    private Linha buscarLinha(Usuario usuario, Arquivo arquivo) {
        Linha busca = new Linha(usuario, arquivo);
        for (Linha atual : linhas) {
            if (atual.equals(busca)) {
                return atual;
            }
        }
        return null;
    }

    private void print(String mensagem) {
        System.out.println(mensagem);
    }
}
