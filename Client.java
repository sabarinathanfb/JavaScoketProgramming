import java.io.*;
import java.net.*;

public class Client{

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",1234);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output,true);

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer.println("Hello Server I am sabarinathan!");

            String response = reader.readLine();

            System.out.println("Server Response: " + response);
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}