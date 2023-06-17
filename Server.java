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
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
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
            for(int i=0;i<numberOfPlayers;i+=1)
            {
                if(clients.get(i).gui.preparationDone)
                {
                    numOfReady += 1;
                }
            }
            if(numOfReady == numberOfPlayers)break;
        }
        System.out.println("Everybody is ready");

        int idPlayer1 = clients.get(0).id;
        int idPlayer2 = clients.get(1).id;
        int idPlayer3 = clients.get(2).id;
        int idPlayer4 = clients.get(3).id;

        for(int i=0;i<numberOfPlayers;i+=1)
        {
            clients.get(i).idPlayer1 = idPlayer1;
            clients.get(i).idPlayer2 = idPlayer2;
            clients.get(i).idPlayer3 = idPlayer3;
            clients.get(i).idPlayer4 = idPlayer4;
        }

        // GAME LOOP
        boolean isEnd = false;
        int numberOfLosers = 0;
        while (!isEnd)
        {
            for(int i=0;i<numberOfPlayers;i+=1)
            {
                // Choosing on which board to shoot
                int chosen = GetRandomWithoutOne(i);

                System.out.println("Player: "+ i + " shoot player: " + chosen);

                clients.get(i).isMyTurn = true;
                if(i == 0)
                {
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player1BoardGUI.ChosenX == -1 && clients.get(i).gui.player1BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
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
                        clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
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
                        clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 3)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
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
                        clients.get(i).gui.player1BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 1)
                    {
                        while(clients.get(i).gui.player2BoardGUI.ChosenX == -1 && clients.get(i).gui.player2BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player2BoardGUI.ResetBoardTiles();
                    }
                    if(chosen == 2)
                    {
                        while(clients.get(i).gui.player3BoardGUI.ChosenX == -1 && clients.get(i).gui.player3BoardGUI.ChosenY == -1)
                        {
                            // Waiting to choose place to shoot
                            continue;
                        }
                        clients.get(i).gui.player3BoardGUI.ResetBoardTiles();
                    }
                }

                int chosenX = clients.get(i).gui.mainBoardGUI.ChosenX;
                int chosenY = clients.get(i).gui.mainBoardGUI.ChosenY;
                Point pointToShoot = new Point(chosenX,chosenY);

                // shooting on that board
                clients.get(chosen).gui.mainLogicBoard.shootBoat(pointToShoot);

                // update board for one that was chosen to be shooted
                Board mainLogicBoard = clients.get(chosen).gui.mainLogicBoard;
                int resultValue = mainLogicBoard.Board[chosenX][chosenY];
                clients.get(chosen).gui.mainBoardGUI.UpdateBoard(mainLogicBoard);


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
                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 2){
                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 3){
                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }
                }
                if(i == 1) // player 2 shooted
                {
                    if(chosen == 0){
                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 2){
                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 3){
                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                }
                if(i == 2) // player 3 shooted
                {
                    if(chosen == 0){
                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 1){
                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 3){
                        clients.get(2).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                }
                if(i == 3)
                {
                    if(chosen == 0){
                        clients.get(3).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player1LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 1){
                        clients.get(3).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(2).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }

                    if(chosen == 2){
                        clients.get(3).gui.player3LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(0).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                        clients.get(1).gui.player2LogicBoard.Board[chosenX][chosenY] = resultValue;
                    }
                }

            }
            isEnd = true;
        }

    }
}