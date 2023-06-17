public class Boat {
    public int length; //length of boat
    public int direction; //direction of boat placement: 0 - horizontal down, 1 - vertical right
    public Point position; //position of Boat
    Boat(int length, int direction, Point position) {
        this.length = length;
        this.direction = direction;
        this.position = position;
    }
}
