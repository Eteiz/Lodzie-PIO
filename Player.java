import java.util.Scanner;

public class Player {
    String playerNick;
    Board playerBoard = new Board();
    Player(String playerNick) {
        this.playerNick = playerNick;
    }
    public void setBoats() {
        int num_of_4boats = 0;
        int num_of_3boats = 0;
        int num_of_2boats = 0;
        int num_of_1boats = 0;

        int length = 2, direction = 0, x = 0, y = 1;


        Boat boat = new Boat(length, direction, new Point(x, y));
        if(playerBoard.validateBoat(boat)) {
            if(length == 1 && num_of_1boats < 4) {
                this.playerBoard.setBoats(boat);
                num_of_1boats++;
            }
            if(length == 2 && num_of_2boats < 3) {
                this.playerBoard.setBoats(boat);
                num_of_2boats++;
            }
            if(length == 3 && num_of_3boats < 2) {
                this.playerBoard.setBoats(boat);
                num_of_3boats++;
            }
            if(length == 4 && num_of_4boats < 1) {
                this.playerBoard.setBoats(boat);
                num_of_4boats++;
            }

        }
    }
    public void shoot(Board oponnetBoard) {
        Point shot = new Point(0,0);
        oponnetBoard.shootBoat(shot);
    }
}
