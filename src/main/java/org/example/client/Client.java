package org.example.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{

    private Socket socket;                  // Socket que irá permitir a conexão entre o cliente e o servidor
    private DataInputStream input;          // Input que irá receber as linhas escritas pelo cliente no terminal
    private DataOutputStream output;        // Output que irá ser enviado ao servidor com a linha escrita no terminal
    private final String address;           // IP do servidor
    private final int port;                 // Porta do servidor

    public Client(String address, int port) {   // Construtor do cliente que contém endereço de IP (Address) e a Porta para conexão
        this.address = address;
        this.port = port;

        try {
            socket = new Socket(this.address, this.port);                   // Criação de um novo Socket para comunicação, no endereço e porta especificados
            input = new DataInputStream(System.in);                         // Adição de um novo input ao Socket
            output = new DataOutputStream(socket.getOutputStream());        // Adição de um novo input ao Socket

            System.out.println("Connected");

            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Main, vai executar o construtor do Cliente
    public static void main(String[] args) {
        String luisIp = "192.168.195.203";
        String juniorIp = "192.168.195.189";

        Client client = new Client(luisIp, 5000);
        new ClientListener(client);

    }

    @Override
    public void run() {
        String line = "";                   // Variável que irá receber o String escrito no terminal durante a execução do programa
        int caracter;                       // Variável que irá receber o String escrito no terminal durante a execução do programa

        while (!line.equals("Over")) {      // Mantem o envio até que "Over" seja lido na linha
            try {
                if(input != null){
                    line = input.readLine();
                    System.out.println(line);
                    output.writeUTF(line);
                }

            } catch (IOException e) {           // Caso haja uma IOException a mesm é exibida no terminal
                e.printStackTrace();
                break;
            }
        }

        try {                                   // Tentativa de fechar o Input, Output e o Socket
            if(input != null){
                input.close();
            }
            output.close();
            socket.close();
        } catch (IOException e) {               // Caso a tentativa não seja realizada com êxito, exibe a exceção
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
