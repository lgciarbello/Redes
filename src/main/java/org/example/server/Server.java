package org.example.server;

// A Java program for a Server

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.server.thread.ServerInit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Getter
@Setter
@NoArgsConstructor
public class Server {
    // initialize socket and input stream
    private Socket socket; // creates a socket (TCP client to communicated between 2 machines)
    private ServerSocket server;

    // constructor with port
    public Server(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port); // creates a server that will listen to a defined port
            System.out.println("Server started");

            while (true) {
                System.out.println("Waiting for a client ...");
                socket = server.accept();

                ServerInit serverInit = new ServerInit(socket);
                new Thread(serverInit).start();
            }

        }
        catch(IOException i) {
            System.out.println(i);
        }
    }


    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}