import java.io.*;
import java.net.*;

public class Server{
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server is Running on Port 1234...");
            Socket socket = serverSocket.accept();
            System.out.println("ClientConnected");

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);
            String message = reader.readLine();
            System.out.println("Received From Client: " + message);

            writer.println("Hello Client, Your Message was: "+ message);

            socket.close();
            serverSocket.close();

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}