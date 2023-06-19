import java.io.*;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class Client2 {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    static GameGUI gui = new GameGUI();

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

            //Scanner scanner = new Scanner(System.in);
            int chosenX = -1;
            int chosenY = -1;
            while(socket.isConnected())
            {
                //String messageToSend2 = scanner.nextLine();
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
                if(chosenX != -1 && chosenY != -1)
                {
                    String messageToSend = chosenX + " " + chosenY;
                    bufferedWriter.write(username + ": " + messageToSend + " !");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    gui.player1BoardGUI.ResetBoardTiles();
                }
//                bufferedWriter.write(username + ": " + messageToSend2 +":" + chosenX + " " + chosenY);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
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
                        if(messageFromGroupChat.startsWith("1:") && messageFromGroupChat.endsWith("!"))
                        {
                            String[] parts = messageFromGroupChat.split(" ");
                            System.out.println(parts[1] + " " + parts[2]);
                        }
                        if(messageFromGroupChat.startsWith("2:") && messageFromGroupChat.endsWith("!"))
                        {
                            String[] parts = messageFromGroupChat.split(" ");
                            System.out.println(parts[1] + " " + parts[2]);
                        }
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
        while(!gui.preparationDone)
        {
            System.out.println("Waiting...");
        }
        System.out.println("Ready!");
        Socket socket = new Socket("localhost",1234);
        Client2 client = new Client2(socket,username);
        client.ListenForMessage();
        client.sendMessage();

    }
}
