import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Board {

    final int size = 10;
    int Board[][] = new int[size][size];

    public boolean setBoats(Boat boat) {
        if(!validateBoat(boat))
            return false;
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
        return true;
    }
    public boolean validateBoat(Boat boat) {
        if(this.Board[boat.position.x][boat.position.y] == 1)
            return false;
        int start = 0, end = 10;
        if(boat.direction == 0) {
            if (boat.position.x + boat.length > this.size)
                return false;
            if (boat.position.x - 1 >= 0)
                start = boat.position.x - 1;
            if (boat.position.x + boat.length + 1 < 10)
                end = boat.position.x + boat.length + 1;
            for (int i = start; i < end; i++) {
                if (this.Board[i][boat.position.y] == 1)
                    return false;
                if (boat.position.y - 1 >= 0)
                    if (this.Board[i][boat.position.y - 1] == 1)
                        return false;
                if (boat.position.y + 1 < 10)
                    if (this.Board[i][boat.position.y + 1] == 1)
                        return false;
            }
        }
        else {
            if (boat.position.y + boat.length > this.size)
                return false;
            if (boat.position.y - 1 >= 0)
                start = boat.position.y - 1;
            if (boat.position.y + boat.length + 1 < 10)
                end = boat.position.y + boat.length + 1;
            for (int i = start; i < end; i++) {
                if (this.Board[boat.position.x][i] == 1)
                    return false;
                if (boat.position.x - 1 >= 0)
                    if (this.Board[boat.position.x - 1][i] == 1)
                        return false;
                if (boat.position.x + 1 < 10)
                    if (this.Board[boat.position.x + 1][i] == 1)
                        return false;
            }
        }
        return true;
    }
    public void shootBoat(Point shoot) {
        if(this.Board[shoot.x][shoot.y] == 0) {
            this.Board[shoot.x][shoot.y] = 2;
        }
        else if(this.Board[shoot.x][shoot.y] == 1) {
            this.Board[shoot.x][shoot.y] = -1;
        }
    }
}