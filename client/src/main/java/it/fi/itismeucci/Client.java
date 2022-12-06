package it.fi.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;



public class Client extends Thread{
    String nomeServer = "localHost";
    int portaServer = 9999;
    Socket ilMioSocket;
    BufferedReader tastiera;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    ArrayList<String> messaggiGruppo = new ArrayList<String>(); 
    ArrayList<String> messaggiSingoli = new ArrayList<String>();
    ObjectMapper objectMapper = new ObjectMapper();

    public Client(Socket ilMioSocket) {
        
        this.ilMioSocket = ilMioSocket;
        
    }

    public Socket connetti(){
        System.out.println("il client è ora in esecuzione");

        try{
            //creo il soket con ip (nel nostro caso 127.0.0.1 o localhost) e porta
            ilMioSocket = new Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(ilMioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(ilMioSocket.getInputStream()));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("errore durante la connessione");
            System.exit(1);
        }
        return ilMioSocket;    
    }

    
}