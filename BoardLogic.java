import java.util.Arrays;

public class BoardLogic {
    private final int size = 10; //size of game board
    public int[][] logicBoard = new int[size][size]; //game board for placing boats
    public int[] boats = new int[4]; //array to know how many boats are set
    private final int[] controlBoats = new int[4]; //array to define how many boats of specified length are to be placed

    BoardLogic() {
        this.controlBoats[0] = 0; //game has 4 boats with length 1
        this.controlBoats[1] = 0; //game has 3 boats with length 2
        this.controlBoats[2] = 1; //game has 2 boats with length 3
        this.controlBoats[3] = 0; //game has 1 boat with length 4
    }

    //method to place boats
    public boolean SetBoats(BoatLogic boat) {
        if(!ValidateBoat(boat))
            return false;
        if(boat.length == 1)
            this.logicBoard[boat.position.x][boat.position.y] = 1;
        if(boat.direction == 0) {
            for(int i = 0; i < boat.length; i++)
                this.logicBoard[boat.position.x + i][boat.position.y] = 1;
        }
        else {
            for(int i = 0; i < boat.length; i++)
                this.logicBoard[boat.position.x][boat.position.y + i] = 1;
        }
        this.boats[boat.length - 1] += 1;
        return true;
    }

    //metod to clear board
    public void ClearLogicBoard() {
        for(int i = 0 ; i < size; i++)
            for(int j = 0; j < size; j++)
                this.logicBoard[i][j] = 0;
        Arrays.fill(this.boats, 0);
    }

    //method to check if boat to place is valid
    public boolean ValidateBoat(BoatLogic boat) {
        if(this.logicBoard[boat.position.x][boat.position.y] == 1)
            return false;
        if(this.boats[boat.length - 1] == this.controlBoats[boat.length - 1])
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
                if (this.logicBoard[i][boat.position.y] == 1)
                    return false;
                if (boat.position.y - 1 >= 0)
                    if (this.logicBoard[i][boat.position.y - 1] == 1)
                        return false;
                if (boat.position.y + 1 < 10)
                    if (this.logicBoard[i][boat.position.y + 1] == 1)
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
                if (this.logicBoard[boat.position.x][i] == 1)
                    return false;
                if (boat.position.x - 1 >= 0)
                    if (this.logicBoard[boat.position.x - 1][i] == 1)
                        return false;
                if (boat.position.x + 1 < 10)
                    if (this.logicBoard[boat.position.x + 1][i] == 1)
                        return false;
            }
        }
        return true;
    }

    //metod to make shoots on the board
    public void ShootBoat(PointLogic shoot) {
        if(this.logicBoard[shoot.x][shoot.y] == 0) {
            this.logicBoard[shoot.x][shoot.y] = 2; //miss
        }
        else if(this.logicBoard[shoot.x][shoot.y] == 1) {
            this.logicBoard[shoot.x][shoot.y] = -1; //good shot
        }
    }

    public int GetBoardSize() {
        return size;
    }
    //method to check if all boats are placed
    public boolean CheckAllBoatsSet() {
        for(int i = 0; i < this.controlBoats.length; i++)
        {
            if(this.boats[i] < this.controlBoats[i])
                return false;
        }
        return true;
    }

    //method to check how many boats are left to set
    public int NumBoatsToSet() {
        if(CheckAllBoatsSet())
           return 0;
        int counter = 0;
        for(int i = 0; i < this.controlBoats.length; i++) {
            if (this.boats[i] < this.controlBoats[i])
                counter += this.controlBoats[i] - this.boats[i];
        }
        return counter;
    }

    public boolean CheckAllBoat1Set()
    {
        return this.boats[0] >= this.controlBoats[0];
    }
    public boolean CheckAllBoat2Set()
    {
        return this.boats[1] >= this.controlBoats[1];
    }
    public boolean CheckAllBoat3Set()
    {
        return this.boats[2] >= this.controlBoats[2];
    }
    public boolean CheckAllBoat4Set()
    {
        return this.boats[3] >= this.controlBoats[3];
    }

    //this checks if player has boats left on Board, and if you can continue game
    //returns true if no boat left
      public boolean CheckAllBoatsShot() {
        for(int i = 0 ; i < size; i++){
            for(int j = 0; j < size; j++) {
                if(this.logicBoard[i][j] == 1)
                    return false;
            }
        }
        return true;
    }
}