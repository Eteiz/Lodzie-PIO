import java.io.*;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class Client2 {
    private Socket socket;
    int LastChosenX;
    int LastChosenY;
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

            Scanner scanner = new Scanner(System.in);
            int chosenX = -1;
            int chosenY = -1;
            while(socket.isConnected())
            {
                String messageToSend2 = scanner.nextLine();
                if(messageToSend2.equals("approve"))
                {
                    chosenX = gui.player1BoardGUI.ChosenX;
                    chosenY = gui.player1BoardGUI.ChosenY;
                    if(chosenX != -1 && chosenY != -1)
                    {
                        String messageToSend = chosenX + " " + chosenY;
                        int where_shoot = 2;
                        bufferedWriter.write(username + ": " + where_shoot + " " + messageToSend + " !");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        LastChosenX = chosenX;
                        LastChosenY = chosenY;
                        gui.player1BoardGUI.ResetBoardTiles();
                    }
                }
            }
        }
        catch (IOException e)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public Point ParseShoot(String[] parts)
    {
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);
        return new Point(x,y);
    }
    public int ShootValue(Point point)
    {
        gui.mainLogicBoard.shootBoat(point);
        gui.mainBoardGUI.UpdateBoard(gui.mainLogicBoard);
        return gui.mainLogicBoard.Board[point.x][point.y];
    }

    public void UpdateBoardBasedOnShoot(Point point, int res, int player)
    {
        if(player == 2)
        {
            System.out.println("w 2 strzelalem");
            if(this.username.equals("1"))
            {
                System.out.println("ja gracz 1");
                gui.player1LogicBoard.Board[point.x][point.y] = res;
                gui.player1BoardGUI.UpdateBoard(gui.player1LogicBoard);
            }
        }
        if(player == 1)
        {
            System.out.println("w 1 strzelalem");
            if(this.username.equals("2"))
            {
                System.out.println("ja gracz 2");
                gui.player1LogicBoard.Board[point.x][point.y] = res;
                gui.player1BoardGUI.UpdateBoard(gui.player1LogicBoard);
            }
        }
    }

    public int GetLastCharResult(String string)
    {
        String res = string.substring(string.length() - 1);
        return Integer.parseInt(res);
    }

    public int GetFirstCharPlayer(String string)
    {
        String res = string.substring(0,1);
        return Integer.parseInt(res);
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
                        String[] parts = null;
                        if(messageFromGroupChat.endsWith("!"))
                        {
                            parts = messageFromGroupChat.split(" ");
                            System.out.println(parts[2] + " " + parts[3]);
                        }

                        if(parts != null)
                        {
                            int res = ShootValue(ParseShoot(parts));
                            bufferedWriter.write(username + " Shoot res: " + res);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                        if(messageFromGroupChat.contains("Shoot res"))
                        {
                            int shoot_res = GetLastCharResult(messageFromGroupChat);
                            int player = GetFirstCharPlayer(messageFromGroupChat);

                            UpdateBoardBasedOnShoot(new Point(LastChosenX,LastChosenY),shoot_res,player);

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
