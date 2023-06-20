import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final int PORT = 8098;
    private static final String SERVER_IP = "127.0.0.1";

    private static int numberOfConnetions = 0;
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void startServer()
    {
        try
        {
            while (!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                numberOfConnetions += 1;
                System.out.println("A new client has connected number: "+ numberOfConnetions);

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeServerSocket()
    {
        try {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        if(message.equals("quit"))
        {
            serverSocket.close();
        }
    }
}
