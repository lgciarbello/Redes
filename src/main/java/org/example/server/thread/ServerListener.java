package org.example.server.thread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;


public class ServerListener implements Runnable {

    private List<String> bufferedMemory;
    private Socket socket;
    private DataInputStream in;

    public ServerListener(List<String> bufferedMemory, Socket socket){
        super();
        this.bufferedMemory = bufferedMemory;
        this.socket = socket;
    }

    public void listen() throws IOException {
        // takes input from the client socket
        in = new DataInputStream(socket.getInputStream()); // gets client's data stream
        String line = "";

        // reads message from client until "Over is sent
        while (!line.equals("Over")) {
            try {
                line = in.readUTF(); // reads and stores data received by stream into "line"
                System.out.println(line);
                bufferedMemory.add(line);

            }
            catch(IOException i) {
                System.out.println(i);
                break;
            }
        }

        in.close();
    }

    @Override
    public void run() {
        try {
            this.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
