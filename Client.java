import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {
    private static final String SERVER_IP = "192.168.0.240";
    public static GameGUI gui = new GameGUI();
    private Socket client;
    static Random rand = new Random();
    public final int id = rand.nextInt(100000);
    private static final int SERVER_PORT = 8098;

    public Client(Socket clientSocket) throws IOException
    {
        this.client = clientSocket;
        /*in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);*/
    }

    public static void main(String args[]) throws IOException {
        System.out.println("hey");
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("Write: ");
            String command = null;
            try {
                command = keyboard.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (command.equals("quit")) break;

            out.println(command);

            String serverResponse = null;
            try {
                serverResponse = input.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server says: " + serverResponse);
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
    @Override
    public void run()
    {
        /*Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("Write: ");
            String command = null;
            try {
                command = keyboard.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (command.equals("quit")) break;

            out.println(command);

            String serverResponse = null;
            try {
                serverResponse = input.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server says: " + serverResponse);
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);*/
    }
}