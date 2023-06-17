import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientHandler implements Runnable {

    GameGUI gui = new GameGUI();
    private Socket client;
    static Random rand = new Random();
    public final int id = rand.nextInt(100000);

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) throws IOException
    {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
    }
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                String request = in.readLine();
                if(request.contains("name"))
                {
                    out.println("name ha name");
                }
                else
                {
                    out.println("Print somthing with 'name' to get default response");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
