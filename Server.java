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
        while (!isEnd)
        {
            for(int i=0;i<numberOfPlayers;i+=1)
            {
                clients.get(i).isMyTurn = true;
                while(clients.get(i).gui.mainBoardGUI.ChosenX == -1 && clients.get(i).gui.mainBoardGUI.ChosenY == -1)
                {
                    // Waiting to choose place to shoot
                    continue;
                }
                int chosenX = clients.get(i).gui.mainBoardGUI.ChosenX;
                int chosenY = clients.get(i).gui.mainBoardGUI.ChosenY;
                Point pointToShoot = new Point(chosenX,chosenY);

                clients.get(i).gui.mainBoardGUI.ResetBoardTiles();
                // Choosing on which board to shoot
                int chosen = GetRandomWithoutOne(i);

                // shooting on that board
                clients.get(chosen).gui.mainLogicBoard.shootBoat(pointToShoot);

                // update board for one that was chosen to be shooted
                Board mainLogicBoard = clients.get(chosen).gui.mainLogicBoard;
                clients.get(chosen).gui.mainBoardGUI.UpdateBoard(mainLogicBoard);

                int idChosen = clients.get(chosen).id;

                // update other players logic boards
                for(int ind=0;ind<numberOfPlayers;ind+=1)
                {
                    if(clients.get(i).id == idChosen)continue;

                    clients.get(ind).gui.player1BoardGUI.UpdateBoard(mainLogicBoard);
                }

            }
            isEnd = true;
        }

    }
}