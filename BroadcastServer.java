import java.io.*;
import java.net.*;
import java.util.*;

public class BroadcastServer {
    private static final Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        try {
            // Step 1: Create a server socket listening on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening on port 1234...");

            // Step 2: Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Step 3: Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This is the handler that runs in a separate thread for each client
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Step 4: Create input and output streams for communication with the client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Prompt for username
                out.println("Enter your username:");
                clientName = in.readLine();

                // Add the client's output stream to the set of writers
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Step 5: Read messages from the client and broadcast them to all clients
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(clientName + ": " + message);
                    broadcastMessage(clientName + ": " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Remove the client from the set when they disconnect
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Broadcast message to all connected clients
        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}

