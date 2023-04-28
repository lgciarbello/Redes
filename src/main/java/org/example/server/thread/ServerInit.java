package org.example.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerInit implements Runnable{
    private ServerSocket server;

    public ServerInit(ServerSocket server){
        this.server = server;
    }

    public void intialize(){
        System.out.println("Waiting for a client ...");
        Socket socket = null; // waits for a connection made by a client

        try {
            socket = server.accept();

            List<String> bufferedMemory = new ArrayList<>();
            ServerListener listener = new ServerListener(bufferedMemory, socket);
            ServerWriter writer = new ServerWriter(bufferedMemory, socket);

            Thread threadL =  new Thread(listener);
            Thread threadW = new Thread(writer);

            threadL.start();
            threadW.start();

            System.out.println("Client accepted");
            threadL.join();
            threadW.join();

            System.out.println("Closing connection");
            socket.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        intialize();
    }
}
