package webs;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RealClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5001);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to Server. Type your message:");

            // Thread to read from server
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String serverMsg = in.readUTF();
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Server disconnected.");
                }
            });
            readThread.start();

         
            while (true) {
                String msg = scanner.nextLine();
                out.writeUTF(msg);
                if (msg.equalsIgnoreCase("EXIT")) break;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
