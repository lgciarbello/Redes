package org.example.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerInit implements Runnable{
    private final Socket socket;

    public ServerInit(Socket socket){
        this.socket = socket;
    }

    public void intialize(){

        try {
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
