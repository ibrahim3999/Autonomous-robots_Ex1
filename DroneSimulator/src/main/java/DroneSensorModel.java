public class DroneSensorModel implements DroneSensorDistance{
    private Drone drone;

    public DroneSensorModel(Drone drone){
        this.drone = drone;
    }

    @Override
    public DistanceResult calculateDistances(Point Location, double rotation) {
        double distanceForward = drone.lidars.get(0).current_distance;
        double distanceBackward = drone.lidars.get(3).current_distance;
        double distanceRight = drone.lidars.get(1).current_distance;
        double distanceLeft = drone.lidars.get(2).current_distance;
        return new DistanceResult(distanceForward, distanceBackward, distanceRight, distanceLeft);
    }
    private double getDistanceAtAngle(double degrees) {
        Lidar lidar = new Lidar(drone, degrees);
        return lidar.getSimulationDistance(0);
    }
}
