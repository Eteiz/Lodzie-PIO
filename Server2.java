import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    private static final int PORT = 8098;
    private static final String SERVER_IP = "127.0.0.1";
    private ServerSocket serverSocket;

    public Server2(ServerSocket serverSocket)
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
                System.out.println("A new client has connected");

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
        Server2 server = new Server2(serverSocket);
        server.startServer();
    }
}
