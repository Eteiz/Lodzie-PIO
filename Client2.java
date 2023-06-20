import java.io.*;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.Random;
import java.util.Scanner;

public class Client2 {
    private Socket socket;
    int LastChosenX;
    boolean Player1Turn = true;
    boolean Player2Turn = false;
    boolean Player3Turn = false;
    boolean Player4Turn = false;
    int LastChosenY;
    static Random rand = new Random();
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
    private static int GetRandomWithoutOneInRange(int exceptThis,int range)
    {
        int chosen = exceptThis;
        while(chosen == exceptThis)chosen = rand.nextInt(range)+1; // to have not 0 and 1 but 1 and 2
        return chosen;
    }

    public Point CoordinatesBasedOnWhereShoot(int shootedPlayer)
    {
        int chosenX = -1;
        int chosenY = -1;

        if(username.equals("1"))
        {
            if(shootedPlayer == 2)
            {
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
            }
        }
        if(username.equals("2"))
        {
            if(shootedPlayer == 1)
            {
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
            }
        }

        return new Point(chosenX,chosenY);
    }

    public void WriteNextPlayer(String NextPlayer) throws IOException
    {
        bufferedWriter.write("> " + NextPlayer);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void ChangePlayerTurn() throws IOException {

        if(Player1Turn)
        {
            Player1Turn = false;
            Player2Turn = true;
        }
        else if(Player2Turn)
        {
            Player1Turn = true;
            Player2Turn = false;
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
            int range = 2;
            while(socket.isConnected())
            {
                if(username.equals("1") && Player1Turn || username.equals("2") && Player2Turn)
                {
                    System.out.println("This is my turn!");

                    int where_shoot = GetRandomWithoutOneInRange(Integer.parseInt(username), range);
                    System.out.println("I: " + username + " Shoot in: " + where_shoot);
                    String messageToSend2 = scanner.nextLine();
                    if (messageToSend2.equals("approve"))
                    {
                        Point coords = CoordinatesBasedOnWhereShoot(where_shoot);
                        chosenX = coords.x;
                        chosenY = coords.y;
                        if (chosenX != -1 && chosenY != -1) {
                            String messageToSend = chosenX + " " + chosenY;

                            bufferedWriter.write(username + ": " + where_shoot + " " + messageToSend + " !");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();

                            LastChosenX = chosenX;
                            LastChosenY = chosenY;
                            gui.player1BoardGUI.ResetBoardTiles();

                            ChangePlayerTurn();
                            if(username.equals("1"))WriteNextPlayer("2");
                            if(username.equals("2"))WriteNextPlayer("1");
                        }

                    }
                }
            }
        }
        catch (IOException e)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public Point ParseShootPoint(String[] parts)
    {
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);
        return new Point(x,y);
    }
    public int ShootValue(Point point,int whoWasShooted)
    {
        if(username.equals("1") && whoWasShooted == 1)
        {
            gui.mainLogicBoard.shootBoat(point);
            gui.mainBoardGUI.UpdateBoard(gui.mainLogicBoard);
            return gui.mainLogicBoard.Board[point.x][point.y];
        }
        if(username.equals("2") && whoWasShooted == 2)
        {
            gui.mainLogicBoard.shootBoat(point);
            gui.mainBoardGUI.UpdateBoard(gui.mainLogicBoard);
            return gui.mainLogicBoard.Board[point.x][point.y];
        }
        if(username.equals("3") && whoWasShooted == 3)
        {
            gui.mainLogicBoard.shootBoat(point);
            gui.mainBoardGUI.UpdateBoard(gui.mainLogicBoard);
            return gui.mainLogicBoard.Board[point.x][point.y];
        }
         // "4" && 4
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
                        if(messageFromGroupChat.startsWith(">"))
                        {
                            ChangePlayerTurn();
                        }

                        if(messageFromGroupChat.endsWith("!"))
                        {
                            parts = messageFromGroupChat.split(" ");
                            System.out.println("Parsed string: " + parts[1] + " " + parts[2] + " " + parts[3]);
                        }

                        if(parts != null)
                        {
                            int whoWasShooted = Integer.parseInt(parts[1]);
                            int res = ShootValue(ParseShootPoint(parts),whoWasShooted);
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
