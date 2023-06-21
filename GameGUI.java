import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Main GUI Panel on which all boards and options are displayed
public class GameGUI extends JFrame implements ActionListener {
    final private String start = "<html>Faza ustawiania statków:<br/><br/>" +
            "Aby umieścić statek na planszy naciśnij ikonę statku, " +
            "a nastepnie wybierz pole na którym chcesz go umieścić. " +
            "Gdy ustawisz wszystkie statki tekst ich ustawienia podświetli " +
            "się na zielono.<br/>" +
            "Jeżeli się pomylisz, naciśnij przycisk \"Usuń statki\"</html>";
    boolean preparationDone = false;
    boolean isDead = false;
    String shootReady = " ";

    // Main elements of Game Window
    BoatPlacingPanelGUI shipPanel;
    JButton placementButton, shipResetButton, readyButton, shootButton;
    JLabel infoLabel;

    // Player and 3 enemy players
    // Each player has its Logic and GUI version of board which are dependent on each other and update accordingly
    BoardGUI mainBoardGUI, player1BoardGUI, player2BoardGUI, player3BoardGUI;
    BoardLogic mainLogicBoard, player1LogicBoard, player2LogicBoard, player3LogicBoard;

    // Method to call GUI with its first parameters
    public GameGUI() {
        this.setTitle("Łodzie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1165,800);

        ImageIcon icon = new ImageIcon("src/img/LodzIco.png");
        this.setIconImage(icon.getImage());
        this.setContentPane(new JLabel(new ImageIcon("src/img/background.jpg"))); // ścieżka do pliku obrazu
        this.setLayout(null);
        DisplayMainGamePanel();
    }

    // Main Game panel on which game operates
    void DisplayMainGamePanel() {

        // Placing GUIBoards and initializing LogicBoards for other players
        mainBoardGUI = new BoardGUI(true);
        mainBoardGUI.setBounds(384,328, mainBoardGUI.boardWidth + 27,mainBoardGUI.boardHeight + 27);
        mainLogicBoard = new BoardLogic();
        mainBoardGUI.ActiveTiles();
        add(mainBoardGUI);

        // Player 1
        player1BoardGUI = new BoardGUI(false);
        player1BoardGUI.setBounds(7+45,5,player1BoardGUI.boardWidth + 27,player1BoardGUI.boardHeight + 27);
        player1LogicBoard = new BoardLogic();
        player1BoardGUI.DeactivateTiles();
        add(player1BoardGUI);

        // Player 2
        player2BoardGUI = new BoardGUI(false);
        player2BoardGUI.setBounds(384+45,5, player2BoardGUI.boardWidth + 27, player2BoardGUI.boardHeight + 27);
        player2LogicBoard = new BoardLogic();
        player2BoardGUI.DeactivateTiles();
        add(player2BoardGUI);

        // Player 3
        player3BoardGUI = new BoardGUI(false);
        player3BoardGUI.setBounds(761+45,5,player3BoardGUI.boardWidth + 27,player3BoardGUI.boardHeight + 27);
        player3LogicBoard = new BoardLogic();
        player3BoardGUI.DeactivateTiles();
        add(player3BoardGUI);

        // Placing BoatPlacingPanel for ships to be chosen
        shipPanel = new BoatPlacingPanelGUI();
        shipPanel.UpdateShipLabel(mainLogicBoard);
        shipPanel.setBounds(18,415,336,250);

        // Button to place ships on boat
        placementButton = new JButton("Ustaw statek");
        // Style of button
        placementButton.setBackground(Color.decode("#25B7D3"));
        placementButton.setBorder(BorderFactory.createLineBorder(Color.decode("#256388"), 2));
        placementButton.setFont(new Font("Arial", Font.BOLD, 20));
        placementButton.setForeground(Color.decode("#FBFBFB"));
        // Other
        placementButton.setBounds(18,350,165,50);
        placementButton.addActionListener(this);

        // Button to reset the placement of ships on board
        shipResetButton = new JButton("Usuń statki");
        // Style of button
        shipResetButton.setBackground(Color.decode("#FF5858"));
        shipResetButton.setBorder(BorderFactory.createLineBorder(Color.decode("#AF3030"), 2));
        shipResetButton.setFont(new Font("Arial", Font.BOLD, 20));
        shipResetButton.setForeground(Color.decode("#FBFBFB"));
        // Other
        shipResetButton.setBounds(188, 350, 165, 50);
        shipResetButton.addActionListener(this);

        // Button to signalize that player is ready
        readyButton = new JButton("Gotowy!");
        // Style of button
        readyButton.setBackground(Color.decode("#6AB148"));
        readyButton.setBorder(BorderFactory.createLineBorder(Color.decode("#3E662A"), 2));
        readyButton.setFont(new Font("Arial", Font.BOLD, 25));
        readyButton.setForeground(Color.decode("#FBFBFB"));
        // Other
        readyButton.setBounds(18,670,336,50);
        readyButton.addActionListener(this);
        readyButton.setEnabled(false);

        // Button to confirm the shot on enemy's board
        shootButton = new JButton("Oddaj strzał!");
        // Style of button
        shootButton.setBackground(Color.decode("#25B7D3"));
        shootButton.setBorder(BorderFactory.createLineBorder(Color.decode("#256388"), 2));
        shootButton.setFont(new Font("Arial", Font.BOLD, 25));
        shootButton.setForeground(Color.decode("#FBFBFB"));
        // Other
        shootButton.setBounds(800,350,325,50);
        shootButton.addActionListener(this);
        shootButton.setEnabled(false);

        // Label informing about the phase of game
        infoLabel = new JLabel(start);
        // Style of label
        infoLabel.setBackground(Color.decode("#D0D0D2"));
        infoLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                new EmptyBorder(0, 15, 0, 0)));
        infoLabel.setOpaque(true);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        // Other
        infoLabel.setBounds(800, 420, 325, 300);

        // Adding elements to JPanel
        add(shipPanel);
        add(placementButton);
        add(shipResetButton);
        add(readyButton);
        add(shootButton);
        add(infoLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // The program registers that button "Place ship" is clicked and checks if any tile (ChosenX, ChosenY) and shipType (chosenShip) is chosen
        if(e.getSource().equals(placementButton)) {

            // For test purposes
            //System.out.println(ShipPanel.chosenShip + " " + mainBoardGUI.ChosenX + " " + mainBoardGUI.ChosenY);

            if(shipPanel.GetChosenShip() != null && mainBoardGUI.GetChosenX() != -1 && mainBoardGUI.SetChosenY() != -1) {
                String shipType = shipPanel.GetChosenShip();
                PointLogic placingPoint = new PointLogic(mainBoardGUI.GetChosenX(), mainBoardGUI.SetChosenY());
                BoatLogic newBoat = null;

                if(shipType.equals("2H")) newBoat = new BoatLogic(2,0,placingPoint);
                else if(shipType.equals("3H")) newBoat = new BoatLogic(3,0,placingPoint);
                else if(shipType.equals("4H")) newBoat = new BoatLogic(4,0,placingPoint);
                else if(shipType.equals("2V")) newBoat = new BoatLogic(2,1,placingPoint);
                else if(shipType.equals("3V")) newBoat = new BoatLogic(3,1,placingPoint);
                else if(shipType.equals("4V")) newBoat = new BoatLogic(4,1,placingPoint);
                else newBoat = new BoatLogic(1,0,placingPoint);

                if(mainLogicBoard.ValidateBoat(newBoat)) mainLogicBoard.SetBoats(newBoat);
            }
            mainBoardGUI.UpdateBoard(mainLogicBoard);
            shipPanel.ResetPlacingPanel();
            mainBoardGUI.ResetBoardTiles();
            shipPanel.SetChosenShip(null);
            shipPanel.UpdateShipLabel(mainLogicBoard);

            if(mainLogicBoard.CheckAllBoat1Set())
                shipPanel.LockShip1Button();

            if(mainLogicBoard.CheckAllBoat2Set())
                shipPanel.LockShip2Button();

            if(mainLogicBoard.CheckAllBoat3Set())
                shipPanel.LockShip3Button();

            if(mainLogicBoard.CheckAllBoat4Set())
                shipPanel.LockShip4Button();

            if(mainLogicBoard.CheckAllBoatsSet()){
                shipPanel.shipLeftToPlace.setForeground(Color.decode("#3E662A"));
                readyButton.setEnabled(true);
            }
        }
        // The program registers that button "Reset Ship" is clicked and resets both GUI and Logic Board
        if(e.getSource().equals(shipResetButton)){
            readyButton.setEnabled(false);
            shipPanel.shipLeftToPlace.setBackground(Color.RED);
            shipPanel.UnlockAllShipButtons();

            for(int i = 0; i < mainLogicBoard.GetBoardSize(); ++i)
                for(int j = 0; j < mainLogicBoard.GetBoardSize(); ++j)
                    mainLogicBoard.logicBoard[i][j] = 0;

            mainLogicBoard.ClearLogicBoard();
            mainBoardGUI.UpdateBoard(mainLogicBoard);
            shipPanel.ResetPlacingPanel();
            mainBoardGUI.ResetBoardTiles();
            shipPanel.SetChosenShip(null);
            shipPanel.shipLeftToPlace.setForeground(Color.BLACK);
            shipPanel.UpdateShipLabel(mainLogicBoard);
        }

        // The program registers that button "Ready" is clicked and depending if player placed all their ship moves to next phase of game
        if(e.getSource().equals(readyButton)) {

            if(mainLogicBoard.CheckAllBoatsSet()) {
                // If every boat is placed
                shootButton.setEnabled(true);
                readyButton.setEnabled(false);
                mainBoardGUI.DeactivateTiles();

                // Locking placing buttons
                shipPanel.LockShipButtons();
                placementButton.setEnabled(false);
                shipResetButton.setEnabled(false);

                shipPanel.shipLeftToPlace.setForeground(Color.GRAY);

                preparationDone = true;
            }
        }

        if(e.getSource().equals(shootButton) && (
                (player1BoardGUI.GetChosenX() != -1 && player1BoardGUI.SetChosenY() != -1) ||
                        (player2BoardGUI.GetChosenX() != -1 && player2BoardGUI.SetChosenY() != -1) ||
                        (player3BoardGUI.GetChosenX() != -1 && player3BoardGUI.SetChosenY() != -1))) {
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
        infoLabel.setText(str);
    }
    public static void main(String[] args) throws IOException {
        GameGUI test = new GameGUI();
    }
}
