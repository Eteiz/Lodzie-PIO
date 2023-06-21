import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class BoardGUI extends JPanel implements ActionListener {

    // Two-dimensional array for tiles to ease accessing them
    private final TileGUI[][] boardTiles = new TileGUI[10][10];

    // Board Width and Weight used to determine board's size
    // 360x360 for player's board
    // 270x270 for enemy's board
    public int boardWidth, boardHeight;

    // Coordinates of tiles that are chosen by clicking at them
    // Value -1 means no tile was chosen
    private int ChosenX = -1;
    public int GetChosenX() { return ChosenX; }
    public void SetChosenX(int chosenX) { ChosenX = chosenX; }
    private int ChosenY = -1;
    public int SetChosenY() { return ChosenY; }
    public void SetChosenY(int chosenY) { ChosenY = chosenY; }

    BoardGUI(boolean bigSize) {

        // Offset to generate border around board
        int offset = 0;
        // bigSize - variable that determines if board is for player or enemy
        if(bigSize) {
            boardWidth = 360;
            boardHeight = 360;
        }
        else {
            boardWidth = 270 + 8;
            boardHeight = 270 + 8;
            offset = 4;
        }


        // +27 for spacing between tiles (9x3)
        setPreferredSize(new Dimension(boardWidth + 27,boardHeight + 27));
        setLayout(null);

        // Placing tiles
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                boardTiles[i][j] = new TileGUI(bigSize);
                boardTiles[i][j].ChangeTile(TileGUI.TileValue.EMPTY);
                boardTiles[i][j].setBounds( (boardWidth/10 + 3)*i + offset, (boardHeight/10 + 3)*j + offset,boardWidth/10, boardHeight/10);

                // Tile border for when is it chosen
                Border panelBorder = BorderFactory.createLineBorder(Color.red, 2);
                boardTiles[i][j].setBorder(panelBorder);
                boardTiles[i][j].setBorderPainted(false);

                boardTiles[i][j].addActionListener(this);
                add(boardTiles[i][j]);
            }
        }
        setVisible(true);
        setBackground(Color.BLACK);
        setOpaque(true);
    }

    // Method changes look of the tile depending on its state in LogicBoard
    void UpdateBoard(BoardLogic referencedBoard) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(referencedBoard.logicBoard[i][j] == 1) boardTiles[i][j].ChangeTile(TileGUI.TileValue.SHIP_PLACED);
                else if(referencedBoard.logicBoard[i][j] == 0) boardTiles[i][j].ChangeTile(TileGUI.TileValue.EMPTY);
                else if(referencedBoard.logicBoard[i][j] == 2) boardTiles[i][j].ChangeTile(TileGUI.TileValue.MISS);
                else if(referencedBoard.logicBoard[i][j] == -1) boardTiles[i][j].ChangeTile(TileGUI.TileValue.HIT);
                boardTiles[i][j].setBorderPainted(false);
            }
        }
    }

    // Method resets any highlighting of tiles and sets chosen tiles to -1,-1
    void ResetBoardTiles() {
        for(int k = 0; k < 10; k++) {
            for(int l = 0; l < 10; l++) {
                boardTiles[k][l].setBorderPainted(false);
            }
        }
        SetChosenX(-1);
        SetChosenY(-1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                // If any tile is clicked
                if(e.getSource().equals(boardTiles[i][j])) {
                    // Clearing all other highlighted tiles
                    for(int k = 0; k < 10; k++) {
                        for(int l = 0; l < 10; l++) {
                            boardTiles[k][l].setBorderPainted(false);
                        }
                    }
                    // If the tile was clicked before, it stops being highlighted
                    if(GetChosenX() == i && SetChosenY() == j || boardTiles[i][j].tileValue != TileGUI.TileValue.EMPTY) {
                        SetChosenX(-1);
                        SetChosenY(-1);
                    }
                    // If the tile was not chosen before, it starts being highlighted
                    else {
                        boardTiles[i][j].setBorderPainted(true);
                        SetChosenX(i);
                        SetChosenY(j);
                    }
                }
            }
        }
    }

    public void DeactivateTiles(){
        setBackground(Color.BLACK);
    }
    public void ActiveTiles(){
        setBackground(new Color(0x198580));
    }
}
