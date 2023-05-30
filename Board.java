import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Board extends JLabel {

    final int size = 10;
    int Board[][] = new int[size][size];

    /*Board(){
        this.setText(PlayerNick);
        this.setIcon(boardImg);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.TOP);

        //this.setBorder(BorderFactory.createLineBorder(Color.CYAN,3));
        this.setIconTextGap(-5);

        this.setFont(new Font("MV Boli", Font.PLAIN, 20));
    };*/

    public void printBoard() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++)
                System.out.print(this.Board[i][j] + " ");
            System.out.println();
        }
    }
    public void setBoats(Boat boat) {
        if(boat.length == 1)
            this.Board[boat.position.x][boat.position.y] = 1;
        if(boat.length == 2) {
            if(boat.direction == 0) {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x + 1][boat.position.y] = 1;
            }
            else {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x][boat.position.y + 1] = 1;
            }
        }
        if(boat.length == 3) {
            if(boat.direction == 0) {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x + 1][boat.position.y] = 1;
                this.Board[boat.position.x + 2][boat.position.y] = 1;
            }
            else {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x][boat.position.y + 1] = 1;
                this.Board[boat.position.x][boat.position.y + 2] = 1;
            }
        }
        if(boat.length == 4) {
            if(boat.direction == 0) {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x + 1][boat.position.y] = 1;
                this.Board[boat.position.x + 2][boat.position.y] = 1;
                this.Board[boat.position.x + 3][boat.position.y] = 1;
            }
            else {
                this.Board[boat.position.x][boat.position.y] = 1;
                this.Board[boat.position.x][boat.position.y + 1] = 1;
                this.Board[boat.position.x][boat.position.y + 2] = 1;
                this.Board[boat.position.x][boat.position.y + 3] = 1;
            }
        }
    }
    public void shootBoat(Point shoot) {
        if(this.Board[shoot.x][shoot.y] == 0) {
            System.out.println("You missed!");
            this.Board[shoot.x][shoot.y] = 2;
        }
        else if(this.Board[shoot.x][shoot.y] == 1) {
            System.out.println("You shot a boat!");
            this.Board[shoot.x][shoot.y] = -1;
        }
        else if(this.Board[shoot.x][shoot.y] == 2 || this.Board[shoot.x][shoot.y] == -1) {
            System.out.println("This shot was already made!");
        }
    }

}
