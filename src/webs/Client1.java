package webs;

import java.io.*;
import java.net.*;

public class Client1 {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader console;

    public Client1(String addr, int port) {
        try {
            s = new Socket(addr, port);
            System.out.println("Connected");

            console = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        String m = "";
        while (!m.equals("Over")) {
            try {
                m = console.readLine(); 
                out.writeUTF(m);        
            } catch (IOException e) {
                System.out.println("Communication error: " + e.getMessage());
            }
        }

        try {
            console.close();
            out.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Cleanup error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client c = new Client("127.0.0.1", 5001);
    }
}
