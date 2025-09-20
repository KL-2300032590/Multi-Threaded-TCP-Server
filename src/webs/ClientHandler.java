package webs;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private String username = null;

    private static ConcurrentHashMap<String, ClientHandler> userMap = new ConcurrentHashMap<>();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF("Welcome! Please login using /login <your_name>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String msg = input.readUTF();
                if (msg.startsWith("/login ")) {
                    String name = msg.substring(7).trim();
                    if (userMap.containsKey(name)) {
                        output.writeUTF("Username already taken. Try another.");
                    } else {
                        this.username = name;
                        userMap.put(username, this);
                        output.writeUTF("Logged in as " + username);
                        broadcast(username + " joined the chat!");
                    }

                } else if (msg.equals("/users")) {
                    output.writeUTF("Online: " + userMap.keySet());

                } else if (msg.startsWith("/msg ")) {
                    if (username == null) {
                        output.writeUTF("Please login first using /login <your_name>");
                        continue;
                    }
                    String[] parts = msg.split(" ", 3);
                    if (parts.length < 3) {
                        output.writeUTF("Invalid format. Use /msg <user> <message>");
                        continue;
                    }
                    String toUser = parts[1];
                    String message = parts[2];
                    ClientHandler receiver = userMap.get(toUser);
                    if (receiver != null) {
                        receiver.output.writeUTF("[DM from " + username + "] " + message);
                    } else {
                        output.writeUTF("User not found: " + toUser);
                    }

                } else if (msg.equalsIgnoreCase("/exit")) {
                    output.writeUTF("Goodbye " + username);
                    break;

                } else {
                    if (username == null) {
                        output.writeUTF("Please login first using /login <your_name>");
                        continue;
                    }
                    broadcast("[" + username + "] " + msg);
                }
            }
        } catch (IOException e) {
            System.out.println("[Server] " + username + " disconnected.");
        } finally {
            try {
                userMap.remove(username);
                broadcast(username + " left the chat.");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String msg) {
        for (ClientHandler client : userMap.values()) {
            try {
                if (client != this) {
                    client.output.writeUTF(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
