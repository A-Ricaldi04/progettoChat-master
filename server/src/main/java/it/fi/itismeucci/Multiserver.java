package it.fi.itismeucci;

import java.net.*;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Multiserver {
    //craere e gestire un arraylist di server 
    static public ArrayList<Server> listaClientOn = new ArrayList<>();
    ObjectMapper riceve =new ObjectMapper();
    String stringaRicevuta;
    public void inizia(){
        try{
            ServerSocket serverSocket = new ServerSocket(9999);
            //ogni volta che che un client mi si connette, un nuovo thread si preoccupa di gestirlo tramite il metodo start
            for(;;){
                //in attesa che un client si connetta...
                Socket socket = serverSocket.accept();
               
                pojoDatiClient objClient = riceve.readValue(stringaRicevuta, pojoDatiClient.class);
                Client nuovo = new Client(socket, objClient.getNomeClient());
                Server serverThread = new Server(socket, "PacMan");
                listaClientOn.add(nuovo);
                serverThread.start();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("errore durante l'instanziamento del server");
            System.exit(1);
        }
    }
    
}
