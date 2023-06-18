import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {
    private static final String SERVER_IP = "127.0.0.1";
    public GameGUI gui;
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

    public void main() throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        gui = new GameGUI();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            System.out.println("Write: ");
            String command = keyboard.readLine();

            if (command.equals("quit")) break;

            out.println(command);

            String serverResponse = input.readLine();
            System.out.println("Server says: " + serverResponse);
        }

        socket.close();
        System.exit(0);
    }
    @Override
    public void run()
    {
        try {
            main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}