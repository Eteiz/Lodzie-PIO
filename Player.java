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

        Scanner scanner = new Scanner(System.in);
        boolean err;

        while(num_of_4boats < 1 || num_of_3boats < 2 || num_of_2boats < 3 || num_of_1boats < 4) {
            do {
                System.out.println("Choose length of boat");
                String input = scanner.nextLine();
                try
                {
                    length = Integer.parseInt(input);
                    if(length < 0 || length > 4) {
                        System.out.println("Value is incorrect, must be 0 - 4");
                        err = false;
                    }
                    else
                        err = true;
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Value is incorrect, must be 0 - 4");
                    err = false;
                }
            } while(!err);

            do {
                System.out.println("Choose a direction of boat");
                String input = scanner.nextLine();
                try
                {
                    direction = Integer.parseInt(input);
                    if(direction < 0 || direction > 1) {
                        System.out.println("Value is incorrect, must be 0 - 1");
                        err = false;
                    }
                    else
                        err = true;
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Value is incorrect, must be 0 - 1");
                    err = false;
                }
            } while(!err);

            System.out.println("Choose coordinates of boat");

            do {
                System.out.println("Enter x coordinates: ");
                String input = scanner.nextLine();
                try
                {
                    x = Integer.parseInt(input);
                    if(x < 0 || x > 9) {
                        System.out.println("Value is incorrect, must be 0 - 9");
                        err = false;
                    }
                    else
                        err = true;
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Value is incorrect, must be 0 - 9");
                    err = false;
                }
            } while(!err);

            do {
                System.out.println("Enter y coordinates: ");
                String input = scanner.nextLine();
                try
                {
                    y = Integer.parseInt(input);
                    if(y < 0 || y > 9) {
                        System.out.println("Value is incorrect, must be 0 - 9");
                        err = false;
                    }
                    else
                        err = true;
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Value is incorrect, must be 0 - 9");
                    err = false;
                }
            } while(!err);

            Boat boat = new Boat(length, direction, new Point(x, y));
            if(validateBoat(boat, playerBoard)) {
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
            else {
                System.out.println("Incorrect boat!");
            }
        }
    }
    public boolean validateBoat(Boat boat, Board board) {
        if(boat.direction == 0) {
            if(boat.position.x + boat.length > board.size)
                return false;
        }

        if(boat.direction == 1) {
            if(boat.position.y + boat.length > board.size)
                return false;
        }

        if(board.Board[boat.position.x][boat.position.y] == 1)
            return false;

        if(boat.length == 1) {
            if(boat.position.x + 1 < 10 && board.Board[boat.position.x + 1][boat.position.y] == 1)
                return false;
            if(boat.position.x - 1 >= 0 && board.Board[boat.position.x - 1][boat.position.y] == 1)
                return false;
            if(boat.position.y + 1 < 10 && board.Board[boat.position.x][boat.position.y + 1] == 1)
                return false;
            if(boat.position.y - 1 >= 0 && board.Board[boat.position.x][boat.position.y - 1] == 1)
                return false;
            if(boat.position.x - 1 >= 0 && boat.position.y - 1 >= 0 && board.Board[boat.position.x - 1][boat.position.y - 1] == 1)
                return false;
            if(boat.position.x - 1 >= 0 && boat.position.y + 1 < 10 && board.Board[boat.position.x - 1][boat.position.y + 1] == 1)
                return false;
            if(boat.position.x + 1 < 10 && boat.position.y - 1 >= 0 && board.Board[boat.position.x + 1][boat.position.y - 1] == 1)
                return false;
            if(boat.position.x + 1 < 10 && boat.position.y + 1 < 10 && board.Board[boat.position.x + 1][boat.position.y + 1] == 1)
                return false;
        }
        else{
            if(boat.direction == 0) {
                for(int i = 0; i < boat.length; i++) {
                    if(boat.position.x + 1 + i < 10 && board.Board[boat.position.x + i + 1][boat.position.y] == 1)
                        return false;
                    if(boat.position.x - 1 + i >= 0 && board.Board[boat.position.x + i - 1][boat.position.y] == 1)
                        return false;
                    if(boat.position.x + i < 10 && boat.position.y + 1 < 10 && board.Board[boat.position.x + i][boat.position.y + 1] == 1)
                        return false;
                    if(boat.position.x + i < 10 && boat.position.y - 1 >= 0 && board.Board[boat.position.x + i][boat.position.y - 1] == 1)
                        return false;
                    if(boat.position.x - 1 + i >= 0 && boat.position.y - 1 >= 0 && board.Board[boat.position.x + i - 1][boat.position.y - 1] == 1)
                        return false;
                    if(boat.position.x - 1 + i >= 0 && boat.position.y + 1 < 10 && board.Board[boat.position.x + i - 1][boat.position.y + 1] == 1)
                        return false;
                    if(boat.position.x + 1 + i < 10 && boat.position.y - 1 >= 0 && board.Board[boat.position.x + i + 1][boat.position.y - 1] == 1)
                        return false;
                    if(boat.position.x + i < 10 && boat.position.y + 1 < 10 && board.Board[boat.position.x + i + 1][boat.position.y + 1] == 1)
                        return false;
                }
            }
            else {
                for(int i = 0; i < boat.length; i++) {
                    if(boat.position.x + 1 < 10 && boat.position.y + i < 10 && board.Board[boat.position.x + 1][boat.position.y + i] == 1)
                        return false;
                    if(boat.position.x - 1 >= 0 && boat.position.y + i < 10 && board.Board[boat.position.x - 1][boat.position.y + i] == 1)
                        return false;
                    if(boat.position.y + i + 1 < 10 && board.Board[boat.position.x][boat.position.y + i + 1] == 1)
                        return false;
                    if(boat.position.y + i - 1 >= 0 && board.Board[boat.position.x][boat.position.y + i - 1] == 1)
                        return false;
                    if(boat.position.x - 1 >= 0 && boat.position.y + i - 1 >= 0 && board.Board[boat.position.x - 1][boat.position.y + i - 1] == 1)
                        return false;
                    if(boat.position.x - 1 >= 0 && boat.position.y + i + 1 < 10 && board.Board[boat.position.x - 1][boat.position.y + i + 1] == 1)
                        return false;
                    if(boat.position.x + 1 < 10 && boat.position.y + i - 1 >= 0 && board.Board[boat.position.x + 1][boat.position.y + i - 1] == 1)
                        return false;
                    if(boat.position.x + 1 < 10 && boat.position.y + i + 1 < 10 && board.Board[boat.position.x + 1][boat.position.y + i + 1] == 1)
                        return false;
                }
            }
        }
        return true;
    }
    public void shoot(Board oponnetBoard) {

        Scanner scanner = new Scanner(System.in);
        int x = 0, y = 0;
        boolean err;
        System.out.println("Choose coordinates of your shot:");

        do {
            System.out.println("Enter x coordinates: ");
            String input = scanner.nextLine();
            try
            {
                x = Integer.parseInt(input);
                if(x < 0 || x > 9) {
                    System.out.println("Value is incorrect, must be 0 - 9");
                    err = false;
                }
                else
                    err = true;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Value is incorrect, must be 0 - 9");
                err = false;
            }
        } while(!err);

        do {
            System.out.println("Enter y coordinates: ");
            String input = scanner.nextLine();
            try
            {
                y = Integer.parseInt(input);
                if(y < 0 || y > 9) {
                    System.out.println("Value is incorrect, must be 0 - 9");
                    err = false;
                }
                else
                    err = true;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Value is incorrect, must be 0 - 9");
                err = false;
            }
        } while(!err);

        Point shot = new Point(x, y);
        oponnetBoard.shootBoat(shot);
    }
}
