package view;

import controller.Controller;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args) {
        try {
            Controller c = new Controller();
            Scanner sc = new Scanner(System.in);

            System.out.println("Digite o ip do servidor:");
            String ip = sc.nextLine();
            c.inciar(ip);
                               
            System.out.println("Servidor inicializado.");
        } catch (RemoteException | MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Registro não foi iniciado, por favor incie o registro");
            System.exit(0);
        }
    }
}
