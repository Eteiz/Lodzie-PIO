import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

        // Game-Interface
        MainFrame MyFrame = new MainFrame();
        Board MainBoard = new Board();

        // TBA: Spytac Kuby co robi ta linijka kodu
        //MainBoard.setBounds(1080/2-336/2,380,336,368);

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

        // Game-Logic
        Player player = new Player("player");

        //player.setBoats();
        Boat boat_4 = new Boat(4, 0, new Point(0, 0));
        Boat boat_3_1 = new Boat(3, 1, new Point(0, 4));
        Boat boat_3_2 = new Boat(3, 1, new Point(9, 4));
        Boat boat_2_1 = new Boat(2, 0, new Point(3, 5));
        Boat boat_2_2 = new Boat(2, 1, new Point(7, 0));
        Boat boat_2_3 = new Boat(2, 1, new Point(7, 7));
        Boat boat_1_1 = new Boat(1, 1, new Point(9, 0));
        Boat boat_1_2 = new Boat(1, 1, new Point(5, 3));
        Boat boat_1_3 = new Boat(1, 0, new Point(1, 9));
        Boat boat_1_4 = new Boat(1, 1, new Point(4, 8));

        Boat wrong1 = new Boat(4, 0, new Point(7, 9));
        Boat wrong2 = new Boat(2, 1, new Point(2, 9));
        Boat wrong3 = new Boat(1, 1, new Point(2, 6));
        Boat wrong4 = new Boat(4, 1, new Point(2, 1));
        Boat wrong5 = new Boat(3, 0, new Point(4, 7));
        Boat good1 = new Boat(3, 1, new Point(7, 3));
        Boat good2 = new Boat(2, 0, new Point(2, 2));
        Boat good3 = new Boat(1, 0, new Point(9, 9));

        player.playerBoard.setBoats(boat_4);
        player.playerBoard.setBoats(boat_3_1);
        player.playerBoard.setBoats(boat_3_2);
        player.playerBoard.setBoats(boat_2_1);
        player.playerBoard.setBoats(boat_2_2);
        player.playerBoard.setBoats(boat_2_3);
        player.playerBoard.setBoats(boat_1_1);
        player.playerBoard.setBoats(boat_1_2);
        player.playerBoard.setBoats(boat_1_3);
        player.playerBoard.setBoats(boat_1_4);

        if(player.validateBoat(wrong1, player.playerBoard)) {
            System.out.println("ADD wrong1");
            player.playerBoard.setBoats(wrong1);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(wrong2, player.playerBoard)) {
            System.out.println("ADD wrong2");
            player.playerBoard.setBoats(wrong2);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(wrong3, player.playerBoard)) {
            System.out.println("ADD wrong3");
            player.playerBoard.setBoats(wrong3);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(wrong4, player.playerBoard)) {
            System.out.println("ADD wrong4");
            player.playerBoard.setBoats(wrong4);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(wrong5, player.playerBoard)) {
            System.out.println("ADD wrong5");
            player.playerBoard.setBoats(wrong5);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(good1, player.playerBoard)) {
            System.out.println("ADD good1");
            player.playerBoard.setBoats(good1);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(good2, player.playerBoard)) {
            System.out.println("ADD good2");
            player.playerBoard.setBoats(good2);
            player.playerBoard.printBoard();
        }
        if(player.validateBoat(good3, player.playerBoard)) {
            System.out.println("ADD good3");
            player.playerBoard.setBoats(good3);
            player.playerBoard.printBoard();
        }
        /*player.playerBoard.printBoard();
        System.out.println();
        player.shoot(player.playerBoard);

        player.playerBoard.printBoard();
        System.out.println();
        player.shoot(player.playerBoard);

        player.playerBoard.printBoard();
        System.out.println();
        player.shoot(player.playerBoard);

        player.playerBoard.printBoard();
        System.out.println();
        player.shoot(player.playerBoard);

        player.playerBoard.printBoard();*/
    }
}


