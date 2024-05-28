public class DistanceResult {
    private double distanceForward;
    private double distanceBackward;
    private double distanceRight;
    private double distanceLeft;

    public DistanceResult(double forward, double backward, double right, double left) {
        this.distanceForward = forward;
        this.distanceBackward = backward;
        this.distanceRight = right;
        this.distanceLeft = left;
    }
    public double getDistanceForward() {
        return distanceForward;
    }

    public double getDistanceBackward() {
        return distanceBackward;
    }

    public double getDistanceRight() {
        return distanceRight;
    }

    public double getDistanceLeft() {
        return distanceLeft;
    }
}

