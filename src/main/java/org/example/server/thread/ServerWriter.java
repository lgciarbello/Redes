package org.example.server.thread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerWriter implements Runnable {

    private List<String> bufferedMemory;
    private Socket socket;
    private DataOutputStream out;
    private int count = 0;

    public ServerWriter(List<String> bufferedMemory, Socket socket) {
        super();
        this.bufferedMemory = bufferedMemory;
        this.socket = socket;
    }

    public void write() throws IOException {
        out = new DataOutputStream(socket.getOutputStream()); // gets client's data stream
        String line = "";

        while (socket.isConnected()) {
            try{
                if (bufferedMemory.isEmpty()) {
                    out.writeUTF(String.valueOf(count));
                    count++;
                    Thread.sleep(1000);
                } else {
                    out.writeUTF(bufferedMemory.get(0));
                    bufferedMemory.remove(0);
                }
            }catch(Exception e){
                e.printStackTrace();
                break;
            }
        }

    }

    @Override
    public void run() {
        try {
            this.write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}