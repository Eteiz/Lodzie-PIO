import java.util.Arrays;

public class Board {
    private final int size = 10; //size of game board
    public int Board[][] = new int[size][size]; //game board for placing boats
    public int Boats[] = new int[4]; //array to know how many boats are set
    private final int ControlBoats[] = new int[4]; //array to define how many boats of specified length are to be placed

    Board()
    {
        this.ControlBoats[0] = 1; //game has 4 boats with length 1
        this.ControlBoats[1] = 0; //game has 3 boats with length 2
        this.ControlBoats[2] = 0; //game has 2 boats with length 3
        this.ControlBoats[3] = 0; //game has 1 boat with length 4
    }

    //method to place boats
    public boolean setBoats(Boat boat) {
        if(!validateBoat(boat))
            return false;
        if(boat.length == 1)
            this.Board[boat.position.x][boat.position.y] = 1;
        if(boat.direction == 0) {
            for(int i = 0; i < boat.length; i++)
                this.Board[boat.position.x + i][boat.position.y] = 1;
        }
        else {
            for(int i = 0; i < boat.length; i++)
                this.Board[boat.position.x][boat.position.y + i] = 1;
        }
        this.Boats[boat.length - 1] += 1;
        return true;
    }

    //metod to clear board
    public void clearBoard() {
        for(int i = 0 ; i < size; i++)
            for(int j = 0; j < size; j++)
                this.Board[i][j] = 0;
        Arrays.fill(this.Boats, 0);
    }

    //method to check if boat to place is valid
    public boolean validateBoat(Boat boat) {
        if(this.Board[boat.position.x][boat.position.y] == 1)
            return false;
        if(this.Boats[boat.length - 1] == this.ControlBoats[boat.length - 1])
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

    //metod to make shoots on the board
    public void shootBoat(Point shoot) {
        if(this.Board[shoot.x][shoot.y] == 0) {
            this.Board[shoot.x][shoot.y] = 2; //miss
        }
        else if(this.Board[shoot.x][shoot.y] == 1) {
            this.Board[shoot.x][shoot.y] = -1; //good shot
        }
    }

    public int getSize() {
        return size;
    }
    //method to check if all boats are placed
    public boolean allBoatsSet() {
        for(int i = 0; i < this.ControlBoats.length; i++)
        {
            if(this.Boats[i] < this.ControlBoats[i])
                return false;
        }
        return true;
    }

    //method to check how many boats are left to set
    public int numBoatsToSet() {
        if(allBoatsSet())
           return 0;
        int counter = 0;
        for(int i = 0; i < this.ControlBoats.length; i++) {
            if (this.Boats[i] < this.ControlBoats[i])
                counter += this.ControlBoats[i] - this.Boats[i];
        }
        return counter;
    }

    public boolean all1Set()
    {
        return this.Boats[0] >= this.ControlBoats[0];
    }

    public boolean all2Set()
    {
        return this.Boats[1] >= this.ControlBoats[1];
    }

    public boolean all3Set()
    {
        return this.Boats[2] >= this.ControlBoats[2];
    }

    public boolean all4Set()
    {
        return this.Boats[3] >= this.ControlBoats[3];
    }

    //this checks if player has boats left on Board, and if you can continue game
    //returns true if no boat left
      public boolean checkAllBoatsShot() {
        for(int i = 0 ; i < size; i++){
            for(int j = 0; j < size; j++) {
                if(this.Board[i][j] == 1)
                    return false;
            }
        }
        return true;
    }
}