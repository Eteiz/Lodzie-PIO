public class Boat {
    public int length;
    public int direction; //0 - horizontal down, 1 - vertical right
    public Point position;

    Boat(int length, int direction, Point position) {
        this.length = length;
        this.direction = direction;
        this.position = position;
    }
}
