import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;

public class Client {
    Instant start = Instant.now();
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

    int numberOfLosers = 0;
    boolean Player1Losed = false;
    boolean Player2Losed = false;
    boolean Player3Losed = false;
    boolean Player4Losed = false;
    boolean didILose = false;

    static GameGUI gui = new GameGUI();

    public Client(Socket socket, String username)
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
    public int GetRandomWithoutOneInRange(int exceptThis,int range)
    {
        int chosen = exceptThis;
        while(chosen == exceptThis || (chosen==1 && Player1Losed) || (chosen==2 && Player2Losed) || (chosen==3 && Player3Losed)
         || (chosen==4 && Player4Losed))
        {
            while(chosen == exceptThis)chosen = rand.nextInt(range)+1; // to have not 0 and 1 but 1 and 2
        }
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
            if(shootedPlayer == 3)
            {
                chosenX = gui.player2BoardGUI.ChosenX;
                chosenY = gui.player2BoardGUI.ChosenY;
            }
            if(shootedPlayer == 4)
            {
                chosenX = gui.player3BoardGUI.ChosenX;
                chosenY = gui.player3BoardGUI.ChosenY;
            }
        }
        if(username.equals("2"))
        {
            if(shootedPlayer == 1)
            {
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
            }
            if(shootedPlayer == 3)
            {
                chosenX = gui.player2BoardGUI.ChosenX;
                chosenY = gui.player2BoardGUI.ChosenY;
            }
            if(shootedPlayer == 4)
            {
                chosenX = gui.player3BoardGUI.ChosenX;
                chosenY = gui.player3BoardGUI.ChosenY;
            }
        }
        if(username.equals("3"))
        {
            if(shootedPlayer == 1)
            {
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
            }
            if(shootedPlayer == 2)
            {
                chosenX = gui.player2BoardGUI.ChosenX;
                chosenY = gui.player2BoardGUI.ChosenY;
            }
            if(shootedPlayer == 4)
            {
                chosenX = gui.player3BoardGUI.ChosenX;
                chosenY = gui.player3BoardGUI.ChosenY;
            }
        }
        if(username.equals("4"))
        {
            if(shootedPlayer == 1)
            {
                chosenX = gui.player1BoardGUI.ChosenX;
                chosenY = gui.player1BoardGUI.ChosenY;
            }
            if(shootedPlayer == 2)
            {
                chosenX = gui.player2BoardGUI.ChosenX;
                chosenY = gui.player2BoardGUI.ChosenY;
            }
            if(shootedPlayer == 3)
            {
                chosenX = gui.player3BoardGUI.ChosenX;
                chosenY = gui.player3BoardGUI.ChosenY;
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

    public void OpenTurnAndBlockOthers(int player)
    {
        if(player == 1)
        {
            System.out.println("Now is player 1 turn");
            gui.setLabelText("Tura gracza I");
            Player1Turn = true;
            Player2Turn = false;
            Player3Turn = false;
            Player4Turn = false;
        }
        if(player == 2)
        {
            gui.setLabelText("Tura gracza II");
            Player1Turn = false;
            Player2Turn = true;
            Player3Turn = false;
            Player4Turn = false;
        }
        if(player == 3)
        {
            gui.setLabelText("Tura gracza III");
            Player1Turn = false;
            Player2Turn = false;
            Player3Turn = true;
            Player4Turn = false;
        }
        if(player == 4)
        {
            gui.setLabelText("Tura gracza IV");
            Player1Turn = false;
            Player2Turn = false;
            Player3Turn = false;
            Player4Turn = true;
        }
    }
    public void ChangePlayerTurn() throws IOException {

        if(Player1Turn)
        {
            OpenTurnAndBlockOthers(2);
            if(Player2Losed)OpenTurnAndBlockOthers(3);
            if(Player3Losed)OpenTurnAndBlockOthers(4);
            if(Player4Losed)OpenTurnAndBlockOthers(1);
        }
        else if(Player2Turn)
        {
            OpenTurnAndBlockOthers(3);
            if(Player3Losed)OpenTurnAndBlockOthers(4);
            if(Player4Losed)OpenTurnAndBlockOthers(1);
            if(Player1Losed)OpenTurnAndBlockOthers(2);
        }
        else if(Player3Turn)
        {
            OpenTurnAndBlockOthers(4);
            if(Player4Losed)OpenTurnAndBlockOthers(1);
            if(Player1Losed)OpenTurnAndBlockOthers(2);
            if(Player2Losed)OpenTurnAndBlockOthers(3);
        }
        else if(Player4Turn)
        {
            OpenTurnAndBlockOthers(1);
            if(Player1Losed)OpenTurnAndBlockOthers(2);
            if(Player2Losed)OpenTurnAndBlockOthers(3);
            if(Player3Losed)OpenTurnAndBlockOthers(4);
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
            int range = 4;
            while(socket.isConnected())
            {
                if(!didILose && numberOfLosers == 1)
                {
                    gui.setLabelText("Wygrałeś");
                    closeEverything(socket,this.bufferedReader,this.bufferedWriter);
                }
                if(didILose)
                {
                    gui.setLabelText("Przegrałeś");
                }
                if(!didILose)
                {
                    if ((username.equals("1") && Player1Turn) || (username.equals("2") && Player2Turn) ||
                            (username.equals("3") && Player3Turn) || (username.equals("4") && Player4Turn))
                    {

                        int where_shoot = GetRandomWithoutOneInRange(Integer.parseInt(username), range);
                        gui.setLabelText("<html>Twoja tura<br/><br/>Strzelasz do gracza: "+ where_shoot +"</html>");
                        ChangeViewOfBoard(where_shoot);
                        //System.out.println("I: " + username + " Shoot in: " + where_shoot);
                        String messageToSend2 = scanner.nextLine();

                        if(messageToSend2.equals("y"))
                        {

                            Point coords = CoordinatesBasedOnWhereShoot(where_shoot);
                            chosenX = coords.x;
                            chosenY = coords.y;
                            if (chosenX != -1 && chosenY != -1)
                            {
                                String messageToSend = chosenX + " " + chosenY;

                                bufferedWriter.write(username + ": " + where_shoot + " " + messageToSend + " !");
                                bufferedWriter.newLine();
                                bufferedWriter.flush();

                                LastChosenX = chosenX;
                                LastChosenY = chosenY;
                                gui.player1BoardGUI.ResetBoardTiles();

                                ChangePlayerTurn();
                                if(Player1Turn)WriteNextPlayer("1");
                                else if(Player2Turn)WriteNextPlayer("2");
                                else if(Player3Turn)WriteNextPlayer("3");
                                else if(Player4Turn)WriteNextPlayer("4");

                                gui.player1BoardGUI.desactiveTiles();
                                gui.player2BoardGUI.desactiveTiles();
                                gui.player3BoardGUI.desactiveTiles();
                            }
                            gui.shootReady = " ";
                        }
                    }
                    else
                    {
                        if (Duration.between(start, Instant.now()).toSeconds() >= 1)
                        {
                            //gui.setLabelText("Oczekiwanie na kolejnego gracza");
                            System.out.println("Waiting for changing turn...");
                            start = Instant.now();
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

    public void ActivateBoardAndDisactOthers(int whoToActivate)
    {
        if(whoToActivate == 1)
        {
            gui.player1BoardGUI.activeTiles();
            gui.player2BoardGUI.desactiveTiles();
            gui.player3BoardGUI.desactiveTiles();
        }
        if(whoToActivate == 2)
        {
            gui.player2BoardGUI.activeTiles();
            gui.player1BoardGUI.desactiveTiles();
            gui.player3BoardGUI.desactiveTiles();
        }
        if(whoToActivate == 3)
        {
            gui.player3BoardGUI.activeTiles();
            gui.player2BoardGUI.desactiveTiles();
            gui.player1BoardGUI.desactiveTiles();
        }

    }

    public void ChangeViewOfBoard(int player)
    {
        if(player == 1 && (username.equals("2") || username.equals("3") || username.equals("4")))
        {
            ActivateBoardAndDisactOthers(1);
        }
        if(player == 2 && username.equals("1"))
        {
            ActivateBoardAndDisactOthers(1);
        }
        if(player == 2 && (username.equals("3") || username.equals("4")))
        {
            ActivateBoardAndDisactOthers(2);
        }
        if(player == 3 && (username.equals("1") || username.equals("2")))
        {
            ActivateBoardAndDisactOthers(2);
        }
        if(player == 3 && (username.equals("4")))
        {
            ActivateBoardAndDisactOthers(3);
        }
        if(player == 4 && (username.equals("2") || username.equals("3") || username.equals("1")))
        {
            ActivateBoardAndDisactOthers(3);
        }
    }

    public void UpdateBoardBasedOnShoot(Point point, int res, int player)
    {
        if(player == 1 && (username.equals("2") || username.equals("3") || username.equals("4")))
        {
            System.out.println("w 1 strzelalem");

            gui.player1LogicBoard.Board[point.x][point.y] = res;
            gui.player1BoardGUI.UpdateBoard(gui.player1LogicBoard);
        }
        if(player == 2 && username.equals("1"))
        {
            System.out.println("w 2 strzelalem");

            gui.player1LogicBoard.Board[point.x][point.y] = res;
            gui.player1BoardGUI.UpdateBoard(gui.player1LogicBoard);
        }
        if(player == 2 && (username.equals("3") || username.equals("4")))
        {
            System.out.println("w 2 strzelalem");

            gui.player2LogicBoard.Board[point.x][point.y] = res;
            gui.player2BoardGUI.UpdateBoard(gui.player2LogicBoard);
        }
        if(player == 3 && (username.equals("1") || username.equals("2")))
        {
            System.out.println("w 3 strzelalem");

            gui.player2LogicBoard.Board[point.x][point.y] = res;
            gui.player2BoardGUI.UpdateBoard(gui.player2LogicBoard);
        }
        if(player == 3 && (username.equals("4")))
        {
            System.out.println("w 3 strzelalem");

            gui.player3LogicBoard.Board[point.x][point.y] = res;
            gui.player3BoardGUI.UpdateBoard(gui.player3LogicBoard);
        }
        if(player == 4 && (username.equals("2") || username.equals("3") || username.equals("1")))
        {
            System.out.println("w 4 strzelalem");

            gui.player3LogicBoard.Board[point.x][point.y] = res;
            gui.player3BoardGUI.UpdateBoard(gui.player3LogicBoard);
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

    public void TellOthersShootRes(int res) throws IOException
    {
        bufferedWriter.write(username + " Shoot res: " + res);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        didILose = gui.mainLogicBoard.checkAllBoatsShot();
        if(didILose)
        {
            bufferedWriter.write(username + " I loosed");
            bufferedWriter.newLine();
            bufferedWriter.flush();
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
                        String[] parts = null;
                        if(messageFromGroupChat.startsWith(">"))
                        {
                            ChangePlayerTurn();
                        }

                        if(messageFromGroupChat.endsWith("!"))
                        {
                            parts = messageFromGroupChat.split(" "); // on who x y
                            System.out.println("Parsed string: " + parts[1] + " " + parts[2] + " " + parts[3]);
                            LastChosenX = Integer.parseInt(parts[2]);
                            LastChosenY = Integer.parseInt(parts[3]);
                        }

                        if(parts != null) // shoot in myself and tell everybody result
                        {
                            if(!didILose)
                            {
                                int whoWasShooted = Integer.parseInt(parts[1]);
                                if(username.equals("1") && whoWasShooted == 1)
                                {
                                    int res = ShootValue(ParseShootPoint(parts),whoWasShooted);
                                    TellOthersShootRes(res);
                                }
                                if(username.equals("2") && whoWasShooted == 2)
                                {
                                    int res = ShootValue(ParseShootPoint(parts),whoWasShooted);
                                    TellOthersShootRes(res);
                                }
                                if(username.equals("3") && whoWasShooted == 3)
                                {
                                    int res = ShootValue(ParseShootPoint(parts),whoWasShooted);
                                    TellOthersShootRes(res);
                                }
                                if(username.equals("4") && whoWasShooted == 4)
                                {
                                    int res = ShootValue(ParseShootPoint(parts),whoWasShooted);
                                    TellOthersShootRes(res);
                                }
                            }
                        }
                        if(messageFromGroupChat.contains("Shoot res")) // update board of player that was shooted
                        {
                            int shoot_res = GetLastCharResult(messageFromGroupChat);
                            int player = GetFirstCharPlayer(messageFromGroupChat);

                            UpdateBoardBasedOnShoot(new Point(LastChosenX,LastChosenY),shoot_res,player);

                        }
                        if(messageFromGroupChat.contains("I loosed"))
                        {
                            if(messageFromGroupChat.startsWith("1"))Player1Losed = true;
                            if(messageFromGroupChat.startsWith("2"))Player2Losed = true;
                            if(messageFromGroupChat.startsWith("3"))Player3Losed = true;
                            if(messageFromGroupChat.startsWith("4"))Player4Losed = true;
                            numberOfLosers += 1;
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
        Socket socket = new Socket("192.168.36.76",1234);
        Client client = new Client(socket,username);
        client.ListenForMessage();
        client.sendMessage();

    }
}
