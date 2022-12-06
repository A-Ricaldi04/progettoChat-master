package it.fi.itismeucci;

import java.net.Socket;

public class Client {
    Socket socket;
    String nome;
    public Client(Socket socket, String nome) {
        this.socket = socket;
        this.nome = nome;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
