import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Main GUI Panel on which all boards and options are displayed
public class GameGUI extends JFrame implements ActionListener {
    final private String start = "<html>Faza ustawiania statków:<br/><br/>" +
            "Aby umieścić statek na planszy naciśnij statek, " +
            "a nastepnie wybierz pole na którym ma sie znajdować. " +
            "Gdy ustawisz wszystkie statki czerwony pasek zamieni " +
            "się na zielony.<br/>" +
            "Jeżeli się pomylisz naciśnij przycisk \"Usuń statki\"</html>";
    boolean preparationDone = false;
    boolean isDead = false;
    String shootReady = " ";

    // Main Game Panel
    BoatPlacingPanel ShipPanel;
    JButton placementButton;
    JButton shipResetButton;
    JButton readyButton;
    JButton shootButton;
    JLabel info;

    // Player
    // Each player has its Logic and GUI version of board which are dependent on each other and update accordingly
    BoardGUI mainBoardGUI;
    Board mainLogicBoard;

    // Enemy
    // Player 1
    BoardGUI player1BoardGUI;
    Board player1LogicBoard;

    // Player 2
    BoardGUI player2BoardGUI;
    Board player2LogicBoard;

    // Player 3
    BoardGUI player3BoardGUI;
    Board player3LogicBoard;

    // Method to call GUI with its first parameters
    public GameGUI() {
        this.setTitle("Łodzie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1165,800);

        ImageIcon icon = new ImageIcon("src/img/LodzIco.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(0xCCCCCC));
        this.setLayout(null);
        DisplayMainGamePanel();
    }

    // Places this panel in the game window
    void DisplayMainGamePanel() {

        // Placing GUIBoards and initializing LogicBoards for other players
        mainBoardGUI = new BoardGUI(true, null);
        mainBoardGUI.setBounds(384,328, mainBoardGUI.boardWidth + 27,mainBoardGUI.boardHeight + 27);
        mainLogicBoard = new Board();
        mainBoardGUI.activeTiles();
        add(mainBoardGUI);

        // Player 1
        player1BoardGUI = new BoardGUI(false, null);
        player1BoardGUI.setBounds(7+45,5,player1BoardGUI.boardWidth + 27,player1BoardGUI.boardHeight + 27);
        player1LogicBoard = new Board();
        player1BoardGUI.desactiveTiles();
        add(player1BoardGUI);

        // Player 2
        player2BoardGUI = new BoardGUI(false, null);
        player2BoardGUI.setBounds(384+45,5, player2BoardGUI.boardWidth + 27, player2BoardGUI.boardHeight + 27);
        player2LogicBoard = new Board();
        player2BoardGUI.desactiveTiles();
        add(player2BoardGUI);

        // Player 3
        player3BoardGUI = new BoardGUI(false, null);
        player3BoardGUI.setBounds(761+45,5,player3BoardGUI.boardWidth + 27,player3BoardGUI.boardHeight + 27);
        player3LogicBoard = new Board();
        player3BoardGUI.desactiveTiles();
        add(player3BoardGUI);

        // Placing BoatPlacingPanel for ships to be chosen
        ShipPanel = new BoatPlacingPanel();
        ShipPanel.setBounds(18,415,336,250);

        //Placing JButton to confirm placing
        placementButton = new JButton("Ustaw statek");
        placementButton.setBounds(18,350,165,50);
        placementButton.addActionListener(this);

        shipResetButton = new JButton("Usuń statki");
        shipResetButton.setBounds(188, 350, 165, 50);
        shipResetButton.addActionListener(this);

        readyButton = new JButton("Gotowy!");
        readyButton.setBounds(18,670,336,50);
        readyButton.addActionListener(this);
        readyButton.setEnabled(false);

        shootButton = new JButton("Oddaj strzał!");
        shootButton.setBounds(800+45,350,200,50);
        shootButton.addActionListener(this);
        shootButton.setEnabled(false);

        //Jabel info
        info = new JLabel();
        setLabelText(start);
        info.setBounds(800, 420, 325, 300);
        info.setBackground(new Color(0x969eb9));
        info.setOpaque(true);
        info.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setVerticalAlignment(SwingConstants.CENTER);
        info.setBorder(new EmptyBorder( 0,15,0,15));

        add(ShipPanel);
        add(placementButton);
        add(shipResetButton);
        add(readyButton);
        add(shootButton);
        add(info);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // The program registers that button "Place ship" is clicked and checks if any tile (ChosenX, ChosenY) and shipType (chosenShip) is chosen
        if(e.getSource().equals(placementButton)) {

            // For test purposes
            //System.out.println(ShipPanel.chosenShip + " " + mainBoardGUI.ChosenX + " " + mainBoardGUI.ChosenY);

            if(ShipPanel.chosenShip != null && mainBoardGUI.ChosenX != -1 && mainBoardGUI.ChosenY != -1) {
                String shipType = ShipPanel.chosenShip;
                Point placingPoint = new Point(mainBoardGUI.ChosenX,mainBoardGUI.ChosenY);
                Boat newBoat = null;

                if(shipType.equals("2H")) newBoat = new Boat(2,0,placingPoint);
                else if(shipType.equals("3H")) newBoat = new Boat(3,0,placingPoint);
                else if(shipType.equals("4H")) newBoat = new Boat(4,0,placingPoint);
                else if(shipType.equals("2V")) newBoat = new Boat(2,1,placingPoint);
                else if(shipType.equals("3V")) newBoat = new Boat(3,1,placingPoint);
                else if(shipType.equals("4V")) newBoat = new Boat(4,1,placingPoint);
                else newBoat = new Boat(1,0,placingPoint);

                if(mainLogicBoard.validateBoat(newBoat)) mainLogicBoard.setBoats(newBoat);
            }
            mainBoardGUI.UpdateBoard(mainLogicBoard);
            ShipPanel.ResetPlacingPanel();
            mainBoardGUI.ResetBoardTiles();
            ShipPanel.chosenShip = null;

            if(mainLogicBoard.all1Set())
                ShipPanel.Lock1s();

            if(mainLogicBoard.all2Set())
                ShipPanel.Lock2s();

            if(mainLogicBoard.all3Set())
                ShipPanel.Lock3s();

            if(mainLogicBoard.all4Set())
                ShipPanel.Lock4s();

            if(mainLogicBoard.allBoatsSet()){
                ShipPanel.shipLeftToPlace.setBackground(Color.GREEN);
                readyButton.setEnabled(true);
            }


        }
        /* shipResetButton action*/
        if(e.getSource().equals(shipResetButton)){
            readyButton.setEnabled(false);
            ShipPanel.shipLeftToPlace.setBackground(Color.RED);
            ShipPanel.UnlockButtons();

            for(int i = 0; i < mainLogicBoard.getSize(); ++i)
                for(int j = 0; j < mainLogicBoard.getSize(); ++j)
                    mainLogicBoard.Board[i][j] = 0;

            mainLogicBoard.clearBoard();
            mainBoardGUI.UpdateBoard(mainLogicBoard);
            ShipPanel.ResetPlacingPanel();
            mainBoardGUI.ResetBoardTiles();
            ShipPanel.chosenShip = null;
        }

        if(e.getSource().equals(readyButton)) {
            // If every boat is placed
            shootButton.setEnabled(true);
            readyButton.setEnabled(false);


            if(mainLogicBoard.allBoatsSet()) {
                // Locking placing buttons
                ShipPanel.LockButtons();
                placementButton.setEnabled(false);
                shipResetButton.setEnabled(false);

                // TUTAJ MAKSYM MOZESZ DODAC ZACZECIE LACZENIA SIE Z SERWEREM CZY COS
                preparationDone = true;
            }
        }

        if(e.getSource().equals(shootButton) && (
                (player1BoardGUI.ChosenX != -1 && player1BoardGUI.ChosenY != -1) ||
                        (player2BoardGUI.ChosenX != -1 && player2BoardGUI.ChosenY != -1) ||
                        (player3BoardGUI.ChosenX != -1 && player3BoardGUI.ChosenY != -1))) {
            shootReady = "y";

            /*if(player1BoardGUI.ChosenX != -1 && player1BoardGUI.ChosenY != -1) {
                player1LogicBoard.shootBoat(new Point(player1BoardGUI.ChosenX, player1BoardGUI.ChosenY));
                player1BoardGUI.UpdateBoard(player1LogicBoard);
            }
            else if(player2BoardGUI.ChosenX != -1 && player2BoardGUI.ChosenY != -1) {
                player2LogicBoard.shootBoat(new Point(player2BoardGUI.ChosenX, player2BoardGUI.ChosenY));
                player2BoardGUI.UpdateBoard(player2LogicBoard);
            }
            else if(player3BoardGUI.ChosenX != -1 && player3BoardGUI.ChosenY != -1) {
                player3LogicBoard.shootBoat(new Point(player3BoardGUI.ChosenX, player3BoardGUI.ChosenY));
                player3BoardGUI.UpdateBoard(player3LogicBoard);
            }
            player1BoardGUI.ResetBoardTiles();
            player2BoardGUI.ResetBoardTiles();
            player3BoardGUI.ResetBoardTiles();*/
        }
    }

    void setLabelText(String str){
        info.setText(str);
    }
    public static void main(String[] args) throws IOException {
        GameGUI test = new GameGUI();
    }
}
