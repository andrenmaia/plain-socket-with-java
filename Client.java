import java.net.*;
import java.io.*;

public class Client {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected. I'm the " + socket.getLocalPort());


            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());
        } catch(Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }

        String line = "";
        
        while(!line.equals("exit")) {
            try {
                line = input.readLine();
                output.writeUTF(line);
            } catch(Exception e) {
                e.printStackTrace(System.err);
            }
        }

        try {
            input.close();
            output.close();
            socket.close();
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting client");
        Client client = new Client("127.0.0.1", 5000);
    }
}