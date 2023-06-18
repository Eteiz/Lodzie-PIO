import java.io.*;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class Client2 {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client2(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.username = username;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessage()
    {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected())
            {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void ListenForMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;

                while(socket.isConnected())
                {
                    try {
                        messageFromGroupChat = bufferedReader.readLine();
                        System.out.println(messageFromGroupChat);
                    }
                    catch (IOException e)
                    {
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            if(socket != null)
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for group chat");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost",1234);
        Client2 client = new Client2(socket,username);
        client.ListenForMessage();
        client.sendMessage();

    }
}
