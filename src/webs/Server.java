package webs;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

//TCP server
public class Server {
    private Socket s = null;
    private DataInputStream in = null;

    public Server(int port) {
        try(ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server Started on port "+port);
         
            
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while(true) {
            	Socket clientSocket = ss.accept();
            	pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server s = new Server(5001);
    }
}
