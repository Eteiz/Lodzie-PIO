import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        MainFrame MyFrame = new MainFrame();

        Board_GUI mainBoardGUI = new Board_GUI(1165/2-360/2,380, 10, false);

        Board_GUI player1BoardGUI = new Board_GUI(18,10, 10, false);

        Board_GUI player2BoardGUI = new Board_GUI(360+18*2,10, 10, false);

        Board_GUI player3BoardGUI = new Board_GUI(360*2+18*3,10, 10, false);

        MyFrame.add(mainBoardGUI.Board);
        MyFrame.add(player1BoardGUI.Board);
        MyFrame.add(player2BoardGUI.Board);
        MyFrame.add(player3BoardGUI.Board);

        BoatPanel ShipPanel = new BoatPanel();
        ShipPanel.setBounds(18,415,336,328);
        MyFrame.add(ShipPanel);

        MyFrame.setVisible(true);
    }
}
