import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private static final int numberOfPlayers = 4;
    private static final int PORT = 8098;
    static Random rand = new Random();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Socket> client_sockets = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(numberOfPlayers);

    private static int GetRandomWithoutOne(int exceptThis)
    {
        int chosen = exceptThis;
        while(chosen == exceptThis)chosen = rand.nextInt(4);
        return chosen;
    }

    public static void main(String[] args) throws IOException
    {
        ServerSocket listener = new ServerSocket(PORT);

        while(clients.size() < numberOfPlayers)
        {
            System.out.println("[SERVER] Waiting for connection...");
            Socket client = listener.accept();

            System.out.println("[SERVER] Connected to client!");

            Client clientThread = new Client(client);
            clients.add(clientThread);

            System.out.println("[SERVER] Number of clients: " + clients.size() + " Added " + clientThread.id);
            pool.execute(clientThread);
        }
        System.out.println("Waiting for everybody to be ready");
        // Waiting for everybody ready
        while(true)
        {
            int numOfReady = 0;
            for(int i=0;i<numberOfPlayers;i+=1)
            {
                if(clients.get(i).gui.preparationDone)
                {
                    numOfReady += 1;
                    System.out.println("Player: " + i + " is ready");
                }
            }
            if(numOfReady == numberOfPlayers)break;
        }
        System.out.println("Everybody is ready");


        // GAME LOOP
        boolean isEnd = false;
        int numberOfLosers = 0;
        int[] Losers = new int[4];
        Losers[0] = 0;
        Losers[1] = 0;
        Losers[2] = 0;
        Losers[3] = 0;
        while (!isEnd)
        {
            int chosenX = -1;
            int chosenY = -1;
            for(int i=0;i<numberOfPlayers;i+=1)
            {
                // skipping players that already losed
                if(Losers[i] == 1)continue;
                // Choosing on which board to shoot
                int chosen = GetRandomWithoutOne(i);

                while(Losers[chosen] == 1)chosen = GetRandomWithoutOne(i);


                System.out.println("Player: "+ i + " shoot player: " + chosen);

                //clients.get(i).isMyTurn = true;
                if(i == 0)
                {
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player1BoardGUI.ChosenX == -1 && clients.get(i).gui.player1BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            //System.out.println("Player: "+ i + " shoot player: " + chosen);
                            continue;
                        }
                        chosenX = clients.get(i).gui.player1BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player1BoardGUI.ChosenY;
                        //clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            //System.out.println("Player: "+ i + " shoot player: " + chosen);
                            continue;
                        }
                        chosenX = clients.get(i).gui.player2BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player2BoardGUI.ChosenY;
                        //clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            //System.out.println("Player: "+ i + " shoot player: " + chosen);
                            continue;
                        }
                        chosenX = clients.get(i).gui.player3BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player3BoardGUI.ChosenY;
                        //clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
                    }
                }
                if(i == 1)
                {
                    if(chosen == 0)
                    {
                        while(clients.get(i).gui.player1BoardGUI.ChosenX == -1 && clients.get(i).gui.player1BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player1BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player1BoardGUI.ChosenY;
                        //clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player2BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player2BoardGUI.ChosenY;
                        //clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player3BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player3BoardGUI.ChosenY;
                        //clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
                    }
                }
                if(i == 2)
                {
                    if(chosen == 0)
                    {
                        while(clients.get(i).gui.player1BoardGUI.ChosenX == -1 && clients.get(i).gui.player1BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player1BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player1BoardGUI.ChosenY;
                        //clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player2BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player2BoardGUI.ChosenY;
                        //clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player3BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player3BoardGUI.ChosenY;
                        //clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
                    }
                }
                if(i == 3)
                {
                    if(chosen == 0)
                    {
                        while(clients.get(i).gui.player1BoardGUI.ChosenX == -1 && clients.get(i).gui.player1BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player1BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player1BoardGUI.ChosenY;
                        //clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player2BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player2BoardGUI.ChosenY;
                        //clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        chosenX = clients.get(i).gui.player3BoardGUI.ChosenX;
                        chosenY = clients.get(i).gui.player3BoardGUI.ChosenY;
                        //clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
                    }
                }
                System.out.println("Player: "+ i + " shooted in: " + chosenX + " " + chosenY);
                Point pointToShoot = new Point(chosenX,chosenY);

                // shooting on that board
                clients.get(chosen).gui.mainLogicBoard.shootBoat(pointToShoot);

                // update board for one that was chosen to be shooted
                Board mainLogicBoard = clients.get(chosen).gui.mainLogicBoard;
                int resultValue = mainLogicBoard.Board[chosenX][chosenY];
                clients.get(chosen).gui.mainBoardGUI.UpdateBoard(mainLogicBoard);

                // check if player on which we shooted did lose
                if(clients.get(chosen).gui.mainLogicBoard.checkAllBoatsShot())
                {
                    numberOfLosers += 1;
                    Losers[chosen] = 1;
                }

                /*
                player1 :
                logicboard1 - player2
                logicboard2 - player3
                logicboard3 - player4

                player2 :
                logicboard1 - player1
                logicboard2 - player3
                logicboard3 - player4

                player3 :
                logicboard1 - player1
                logicboard2 - player2
                logicboard3 - player4

                player4 :
                logicboard1 - player1
                logicboard2 - player2
                logicboard3 - player3
                */
                // update other players logic boards
                if(i == 0) // player 1 shooted
                {
                    if(chosen == 1){
                        clients.get(0).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player1BoardGUI.UpdateBoard(clients.get(0).gui.player1LogicBoard);

                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player2BoardGUI.UpdateBoard(clients.get(2).gui.player2LogicBoard);

                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player2BoardGUI.UpdateBoard(clients.get(2).gui.player2LogicBoard);
                    }

                    if(chosen == 2){
                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2BoardGUI.UpdateBoard(clients.get(0).gui.player2LogicBoard);

                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player2BoardGUI.UpdateBoard(clients.get(1).gui.player2LogicBoard);

                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player3BoardGUI.UpdateBoard(clients.get(3).gui.player3LogicBoard);
                    }

                    if(chosen == 3){
                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player3BoardGUI.UpdateBoard(clients.get(0).gui.player3LogicBoard);

                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player3BoardGUI.UpdateBoard(clients.get(1).gui.player3LogicBoard);

                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player3BoardGUI.UpdateBoard(clients.get(2).gui.player3LogicBoard);
                    }
                }
                if(i == 1) // player 2 shooted
                {
                    if(chosen == 0){
                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player1BoardGUI.UpdateBoard(clients.get(1).gui.player1LogicBoard);

                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player1BoardGUI.UpdateBoard(clients.get(2).gui.player1LogicBoard);

                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player1BoardGUI.UpdateBoard(clients.get(3).gui.player1LogicBoard);
                    }

                    if(chosen == 2){
                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player2BoardGUI.UpdateBoard(clients.get(1).gui.player2LogicBoard);

                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2BoardGUI.UpdateBoard(clients.get(0).gui.player2LogicBoard);

                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player3BoardGUI.UpdateBoard(clients.get(3).gui.player3LogicBoard);
                    }

                    if(chosen == 3){
                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player3BoardGUI.UpdateBoard(clients.get(1).gui.player3LogicBoard);

                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player3BoardGUI.UpdateBoard(clients.get(0).gui.player3LogicBoard);

                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player3BoardGUI.UpdateBoard(clients.get(2).gui.player3LogicBoard);
                    }

                }
                if(i == 2) // player 3 shooted
                {
                    if(chosen == 0){
                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player1BoardGUI.UpdateBoard(clients.get(2).gui.player1LogicBoard);

                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player1BoardGUI.UpdateBoard(clients.get(1).gui.player1LogicBoard);

                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player1BoardGUI.UpdateBoard(clients.get(3).gui.player1LogicBoard);
                    }

                    if(chosen == 1){
                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player2BoardGUI.UpdateBoard(clients.get(2).gui.player2LogicBoard);

                        clients.get(0).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player1BoardGUI.UpdateBoard(clients.get(0).gui.player1LogicBoard);

                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player2BoardGUI.UpdateBoard(clients.get(3).gui.player2LogicBoard);
                    }

                    if(chosen == 3){
                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player3BoardGUI.UpdateBoard(clients.get(2).gui.player3LogicBoard);

                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player3BoardGUI.UpdateBoard(clients.get(0).gui.player3LogicBoard);

                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player3BoardGUI.UpdateBoard(clients.get(1).gui.player3LogicBoard);
                    }

                }
                if(i == 3)
                {
                    if(chosen == 0){
                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player1BoardGUI.UpdateBoard(clients.get(3).gui.player1LogicBoard);

                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player1BoardGUI.UpdateBoard(clients.get(1).gui.player1LogicBoard);

                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player1BoardGUI.UpdateBoard(clients.get(2).gui.player1LogicBoard);
                    }

                    if(chosen == 1){
                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player2BoardGUI.UpdateBoard(clients.get(3).gui.player2LogicBoard);

                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2BoardGUI.UpdateBoard(clients.get(0).gui.player2LogicBoard);

                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player2BoardGUI.UpdateBoard(clients.get(2).gui.player2LogicBoard);
                    }

                    if(chosen == 2){
                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player3BoardGUI.UpdateBoard(clients.get(3).gui.player3LogicBoard);

                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2BoardGUI.UpdateBoard(clients.get(0).gui.player2LogicBoard);

                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player2BoardGUI.UpdateBoard(clients.get(1).gui.player2LogicBoard);
                    }
                }

            }
            if(numberOfLosers == 3)
            {
                isEnd = true;
            }

        }

    }
}