public class BoatLogic {
    public int length; //length of boat
    public int direction; //direction of boat placement: 0 - horizontal down, 1 - vertical right
    public PointLogic position; //position of Boat
    BoatLogic(int length, int direction, PointLogic position) {
        this.length = length;
        this.direction = direction;
        this.position = position;
    }
}
