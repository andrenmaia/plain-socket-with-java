import java.net.*;
import java.io.*;

public class Server {

    private ServerSocket server = null;
    private DataInputStream input = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for client ...");

            while(true) {
                Socket socket = null;
                System.out.println("A");


                try {
                    System.out.println("B");
                    socket = server.accept();
                    System.out.println("A new client is connected: " + socket);

                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    Thread t = new ClientHandler(socket, input, output);
                    t.start();

                } catch(Exception e) {
                    socket.close();
                    e.printStackTrace();
                }
                System.out.println("C");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    class ClientHandler extends Thread {

        final Socket socket;
        final DataInputStream input;
        final DataOutputStream output;

        public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output) {
            this.socket = socket;
            this.input = input;
            this.output = output;
        }

        @Override
        public void run() {

            String received;
            String toReturn;

            while(true) {
                try {
                    output.writeUTF("What's next");

                    received = input.readUTF();

                    if (received.equals("exit")) {
                        System.out.println("Client " + this.socket + "sends exit. Closing this connection");
                        this.socket.close();
                        System.out.println("Connection with " + this.socket + " closed.");
                        break;
                    }

                    System.out.println(this.socket + ": " + received);
                    output.writeUTF("Received: " + received);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                input.close();
                output.close();
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting server");
        Server server = new Server(5000);
    }
}