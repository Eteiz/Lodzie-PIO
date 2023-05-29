import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        MainFrame MyFrame = new MainFrame();

        Board MainBoard = new Board(1165/2-360/2,380, 10, false);

        Board Player1Board = new Board(18,10, 10, false);

        Board Player2Board = new Board(360+18*2,10, 10, false);

        Board Player3Board = new Board(360*2+18*3,10, 10, false);

        MyFrame.add(MainBoard.Board);
        MyFrame.add(Player1Board.Board);
        MyFrame.add(Player2Board.Board);
        MyFrame.add(Player3Board.Board);

        BoardPanel ShipPanel = new BoardPanel();
        ShipPanel.setBounds(18,415,336,328);
        MyFrame.add(ShipPanel);

        MyFrame.setVisible(true);
    }
}
