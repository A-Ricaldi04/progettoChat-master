package it.fi.itismeucci;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server extends Thread{
   ServerSocket server;
   Socket Socket_client;
   BufferedReader inDalClient;
   DataOutputStream outVersoClient;
   ObjectMapper invia = new ObjectMapper();
   ObjectMapper riceve = new ObjectMapper();
   String stringaRicevuta;
   String stringaDaInviare;
   String nomeClient;


   public Server(Socket socket, String nomeClient) throws IOException{
    this.Socket_client = socket;
    this.nomeClient=nomeClient;
    inDalClient = new BufferedReader(new InputStreamReader(Socket_client.getInputStream()));
    outVersoClient =new DataOutputStream(Socket_client.getOutputStream());
    }

    public void run(){
        try{
            comunica();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws JsonMappingException, JsonProcessingException{

        for(;;){
            stringaRicevuta=inDalClient.readLine();
                pojoDatiClient objClient = riceve.readValue(stringaRicevuta, pojoDatiClient.class);
                Client ilClient = new Client(Socket_client, objClient.nomeClient);
                switch(objClient.getCodiceOp()){
                    case 0:
                           //invio messaggio singolo
                        /*Requisiti:
                         * chi invia il messaggio
                         * controllo del messaggio
                         * settare il destinatario
                         */
                        pojoDatiServer messaggiodainviare=new pojoDatiServer();
                        messaggiodainviare.setMessaggioSingolo(objClient.getCorpoMessaggio());
                        messaggiodainviare.setDestinatario(objClient.getDestinatario());

                        String msg = invia.writeValueAsString(messaggiodainviare);
                        outVersoClient.writeBytes(msg);
                        break;
                    case 1:
                        //invio messaggio gruppo
                        /*Requisiti:
                         * chi invia il messaggio
                         * controllare il messaggio
                         * 
                         */
                        pojoDatiServer messaggiodainviare1=new pojoDatiServer();
                        for(int i=0;i<Multiserver.listaClientOn.size();i++)
                        {
                            if(objClient.getNomeClient()!=Multiserver.listaClientOn.get(i).getNome())
                            {
                            messaggiodainviare1.setMessaggioGruppo(objClient.getCorpoMessaggio());
                            messaggiodainviare1.setDestinatario(Multiserver.listaClientOn.get(i).getNome());
                            String msg1 = invia.writeValueAsString(messaggiodainviare1);
                            }
                        }
                        break;
                    case 2:
                        //Chiude la comunicazione ad un client
                        
                        for(int i=0;i<Multiserver.listaClientOn.size();i++)
                        {
                        if(Multiserver.listaClientOn.get(i).getNome()==objClient.getNomeClient())
                        {
                            Multiserver.listaClientOn.remove(i);
                        }
                        Socket_client.close();
                        
                        

                    break;
                }
                    
                

        }
    }
   
    }}
