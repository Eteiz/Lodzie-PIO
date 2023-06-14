import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Main GUI Panel on which all boards and options are displayed
public class GameGUI extends JFrame implements ActionListener {

    // Main Game Panel
    BoatPlacingPanel ShipPanel;
    JButton placementButton;

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
        add(mainBoardGUI);

        // Player 1
        player1BoardGUI = new BoardGUI(false, null);
        player1BoardGUI.setBounds(7,5,player1BoardGUI.boardWidth + 27,player1BoardGUI.boardHeight + 27);
        player1LogicBoard = new Board();
        add(player1BoardGUI);

        // Player 2
        player2BoardGUI = new BoardGUI(false, null);
        player2BoardGUI.setBounds(384,5, player2BoardGUI.boardWidth + 27, player2BoardGUI.boardHeight + 27);
        player2LogicBoard = new Board();
        add(player2BoardGUI);

        // Player 3
        player3BoardGUI = new BoardGUI(false, null);
        player3BoardGUI.setBounds(761,5,player3BoardGUI.boardWidth + 27,player3BoardGUI.boardHeight + 27);
        player3LogicBoard = new Board();
        add(player3BoardGUI);

        // Placing BoatPlacingPanel for ships to be chosen
        ShipPanel = new BoatPlacingPanel();
        ShipPanel.setBounds(18,415,336,328);

        //Placing JButton to confirm placing
        placementButton = new JButton("Ustaw statek");
        placementButton.setBounds(18,350,165,50);
        placementButton.addActionListener(this);

        /** TBA: RESET SHIPS BUTTON
         * setBounds(188, 350, 165, 50);
         * **/

        add(ShipPanel);
        add(placementButton);
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

                if(shipType.equals("1")) mainLogicBoard.setBoats(new Boat(1,0,placingPoint));
                else if(shipType.equals("2H")) mainLogicBoard.setBoats(new Boat(2,0,placingPoint));
                else if(shipType.equals("3H")) mainLogicBoard.setBoats(new Boat(3,0,placingPoint));
                else if(shipType.equals("4H")) mainLogicBoard.setBoats(new Boat(4,0,placingPoint));
                else if(shipType.equals("2V")) mainLogicBoard.setBoats(new Boat(2,1,placingPoint));
                else if(shipType.equals("3V")) mainLogicBoard.setBoats(new Boat(3,1,placingPoint));
                else if(shipType.equals("4V")) mainLogicBoard.setBoats(new Boat(4,1,placingPoint));
            }
            mainBoardGUI.UpdateBoard(mainLogicBoard);
            ShipPanel.ResetPlacingPanel();
            mainBoardGUI.ResetBoardTiles();
        }
    }
    public static void main(String[] args) throws IOException {
        GameGUI test = new GameGUI();
    }
}
