import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private static final int numberOfPlayers = 4;
    private static final int PORT = 8098;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException
    {
        ServerSocket listener = new ServerSocket(PORT);

        while(clients.size() < numberOfPlayers)
        {
            System.out.println("[SERVER] Waiting for connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client!");

            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);
            System.out.println("[SERVER] Number of clients: " + clients.size() + " Added " + clientThread.id);
            pool.execute(clientThread);
        }
        System.out.println("Waiting for everybody to be ready");
        // Waiting for everybody ready
        while(true)
        {
            int numOfReady = 0;
            for(int i=0;i<4;i+=1)
            {
                if(clients.get(i).gui.preparationDone)
                {
                    numOfReady += 1;
                }
            }
            if(numOfReady == 4)break;
        }
        System.out.println("Everybody is ready");

        // GAME LOOP


    }
}