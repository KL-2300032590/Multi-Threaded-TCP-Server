package webs;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientSimulator {

    public static void main(String[] args) {
        int numberOfClients = 50;
        ExecutorService clientPool = Executors.newFixedThreadPool(20); //poolsize-20

        for (int i = 1; i <= numberOfClients; i++) {
            final int clientId = i;

            clientPool.submit(() -> {
                try {
                    long start = System.currentTimeMillis();

                    Socket socket = new Socket("localhost", 5001);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    System.out.println("[Client-" + clientId + "] Connected to server");

                    //store
                    String storeCommand = "STORE id" + clientId + " value" + clientId;
                    out.writeUTF(storeCommand);
                    System.out.println("[Client-" + clientId + "] Sent: " + storeCommand);

                    String response1 = in.readUTF();
                    System.out.println("[Client-" + clientId + "] Server: " + response1);

                    Thread.sleep(200); 

                    //get
                    String getCommand = "GET id" + clientId;
                    out.writeUTF(getCommand);
                    System.out.println("[Client-" + clientId + "] Sent: " + getCommand);

                    String response2 = in.readUTF();
                    System.out.println("[Client-" + clientId + "] Server: " + response2);

                    Thread.sleep(200); 

                    // exit
                    String exitCommand = "EXIT";
                    out.writeUTF(exitCommand);
                    System.out.println("[Client-" + clientId + "] Sent: " + exitCommand);

                    socket.close();

                    long end = System.currentTimeMillis();
                    System.out.println("[Client-" + clientId + "] Finished in " + (end - start) + " ms");

                } catch (IOException | InterruptedException e) {
                    System.err.println("[Client-" + clientId + "] Error: " + e.getMessage());
                }
            });
        }

        clientPool.shutdown();
    }
}
