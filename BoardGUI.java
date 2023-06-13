import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class BoardGUI extends JPanel implements ActionListener {

    JLabel playerName;
    private TileGUI[][] boardTiles = new TileGUI[10][10];
    public int boardWidth;
    public int boardHeight;

    public int ChosenX = -1;
    public int ChosenY = -1;

    BoardGUI(boolean bigSize, String playerName) {

        if(bigSize) {
            boardWidth = 360;
            boardHeight = 360;
        }
        else {
            boardWidth = 270;
            boardHeight = 270;
        }

        // + 27 for spacing between (9x3)
        setPreferredSize(new Dimension(boardWidth + 27,boardHeight + 27));
        setLayout(null);

        // Placing tiles
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                boardTiles[i][j] = new TileGUI(bigSize);
                boardTiles[i][j].ChangeTile(TileGUI.TileValue.EMPTY);
                boardTiles[i][j].setBounds( (boardWidth/10 + 3)*i, (boardHeight/10 + 3)*j,boardWidth/10, boardHeight/10);

                Border panelBorder = BorderFactory.createLineBorder(Color.red);
                boardTiles[i][j].setBorder(panelBorder);
                boardTiles[i][j].setBorderPainted(false);

                boardTiles[i][j].addActionListener(this);
                add(boardTiles[i][j]);
            }
        }

        setVisible(true);
        setOpaque(true);
        setBackground(Color.black);
    }

    void UpdateBoard(Board referencedBoard) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(referencedBoard.Board[i][j] == 1) boardTiles[i][j].ChangeTile(TileGUI.TileValue.SHIP_PLACED);
                else if(referencedBoard.Board[i][j] == 0) boardTiles[i][j].ChangeTile(TileGUI.TileValue.EMPTY);
                else if(referencedBoard.Board[i][j] == 2) boardTiles[i][j].ChangeTile(TileGUI.TileValue.MISS);
                else if(referencedBoard.Board[i][j] == -1) boardTiles[i][j].ChangeTile(TileGUI.TileValue.HIT);
                boardTiles[i][j].setBorderPainted(false);
            }
        }
    }

    void ResetBoardTiles() {
        for(int k = 0; k < 10; k++) {
            for(int l = 0; l < 10; l++) {
                boardTiles[k][l].setBorderPainted(false);
            }
        }
        ChosenX = -1;
        ChosenY = -1;
    }

    TileGUI GetBoardTile(int x, int y) {
        return boardTiles[x][y];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(e.getSource().equals(boardTiles[i][j])) {
                    for(int k = 0; k < 10; k++) {
                        for(int l = 0; l < 10; l++) {
                            boardTiles[k][l].setBorderPainted(false);
                        }
                    }

                    if(ChosenX == i && ChosenY == j) {
                        ChosenX = -1;
                        ChosenY = -1;
                    }
                    else {
                        boardTiles[i][j].setBorderPainted(true);
                        ChosenX = i;
                        ChosenY = j;
                    }
                }
            }
        }
    }
}
