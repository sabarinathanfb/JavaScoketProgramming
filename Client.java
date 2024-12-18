import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Step 1: Connect to the server on localhost, port 1234
            Socket socket = new Socket("95.111.201.30", 1234);

            // Step 2: Create input and output streams for communication with the server
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Step 3: Set up input to read from the console
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String message;
            System.out.println("Type 'exit' to quit.");
            do {
                // Step 4: Read a message from the console and send it to the server
                System.out.print("Enter message: ");
                message = consoleReader.readLine();
                writer.println(message);

                // Step 5: Read the response from the server
                String response = reader.readLine();
                System.out.println("Server response: " + response);

            } while (!message.equalsIgnoreCase("exit"));

            // Step 6: Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
