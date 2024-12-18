import java.io.*;
import java.net.*;

public class MultiThreadedServer {
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
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Step 4: Create input and output streams for communication with the client
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            // Step 5: Read the message from the client
            String message = reader.readLine();
            System.out.println("Received from client: " + message);

            // Step 6: Send a response to the client
            writer.println("Hello Client, your message was: " + message);

            // Step 7: Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
