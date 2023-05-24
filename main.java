import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        MainFrame MyFrame = new MainFrame();
        Board MainBoard = new Board();
        MainBoard.setBounds(1080/2-336/2,380,336,368);

        Board Player1Board = new Board();
        Player1Board.setBounds(18,10,336,368);


        Board Player2Board = new Board();
        Player2Board.setBounds(336+18*2,10,336,368);


        Board Player3Board = new Board();
        Player3Board.setBounds(336*2+18*3,10,336,368);

        MyFrame.add(MainBoard);
        MyFrame.add(Player1Board);
        MyFrame.add(Player2Board);
        MyFrame.add(Player3Board);

        MyFrame.setVisible(true);
    }
}
