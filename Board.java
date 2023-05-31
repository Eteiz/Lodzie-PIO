import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Board {
    final int size = 10;
    int Board[][] = new int[size][size];
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
                for(int i = 0; i < 2; i++)
                    this.Board[boat.position.x + i][boat.position.y] = 1;
            }
            else {
                for(int i = 0; i < 2; i++)
                    this.Board[boat.position.x][boat.position.y + i] = 1;
            }
        }
        if(boat.length == 3) {
            if(boat.direction == 0) {
                for(int i = 0; i < 3; i++)
                    this.Board[boat.position.x + i][boat.position.y] = 1;
            }
            else {
                for(int i = 0; i < 3; i++)
                    this.Board[boat.position.x][boat.position.y + i] = 1;
            }
        }
        if(boat.length == 4) {
            if(boat.direction == 0) {
                for(int i = 0; i < 4; i++)
                    this.Board[boat.position.x + i][boat.position.y] = 1;
            }
            else {
                for(int i = 0; i < 4; i++)
                    this.Board[boat.position.x][boat.position.y + i] = 1;
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