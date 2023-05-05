package org.example.server.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ServerInit implements Runnable{
    private final Socket socket;
    private final Semaphore semaphore;

    public ServerInit(Socket socket, Semaphore semaphore){
        this.socket = socket;
        this.semaphore = semaphore;
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

            System.out.println("SERVER INIT THREAD: Client accepted");
            threadL.join();
            threadW.join();

            System.out.println("Closing connection");
            socket.close();

            semaphore.release();
            System.out.println("Releasing semaphore...");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        intialize();
    }
}
