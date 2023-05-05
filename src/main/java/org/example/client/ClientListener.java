package org.example.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListener implements Runnable{

    private Client cliente;

    private DataInputStream input;

    // Output que irá ser enviado ao servidor com a linha escrita no terminal
    private DataOutputStream output;

    public ClientListener (Client cliente){
        this.cliente = cliente;
        new Thread(this).start();
    }

    @Override
    public void run() {

        try{
            Socket socket = cliente.getSocket();
            System.out.println("O Cliente está ouvindo");

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";
            while(!line.equals("Over")){
                try{
                    line = input.readUTF();
                    System.out.println(line);
                } catch (IOException e){
                    System.out.println("Servidor fechado");
                    break;
                }
            }
            System.out.println("Fechando conexão via cliente");
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
