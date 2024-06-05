import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Drone {
	private double gyroRotation;
	private Point sensorOpticalFlow;

	private Point pointFromStart;
	public Point startPoint;
	public List<Lidar> lidars;
	private String drone_img_path = "Maps\\drone_3_pixels.png";
	public Map realMap;
	private double rotation;
	private double speed;
	private double altitude;
	private Battery battery;
	private CPU cpu;
	private boolean isCharging;

	public Drone(Map realMap) {
		this.realMap = realMap;

		this.startPoint = realMap.drone_start_point;
		pointFromStart = new Point();
		sensorOpticalFlow = new Point();
		lidars = new ArrayList<>();

		speed = 0.2;

		rotation = 0;
		gyroRotation = rotation;

		altitude = 0; // Initial altitude

		battery = new Battery(100, 1, 2); // Initialize the battery with max level, discharge rate, and charge rate

		cpu = new CPU(100, "Drone");
		isCharging = false;
	}

	public void play() {
		cpu.play();
	}

	public void stop() {
		cpu.stop();
	}

	public void addLidar(int degrees) {
		Lidar lidar = new Lidar(this, degrees);
		lidars.add(lidar);
		cpu.addFunction(lidar::getSimulationDistance);
	}

	public Point getPointOnMap() {
		double x = startPoint.x + pointFromStart.x;
		double y = startPoint.y + pointFromStart.y;
		return new Point(x, y);
	}

	public void update(int deltaTime) {
		double distancedMoved = (speed * 100) * ((double) deltaTime / 1000);

		pointFromStart = Tools.getPointByDistance(pointFromStart, rotation, distancedMoved);

		double noiseToDistance = Tools.noiseBetween(WorldParams.min_motion_accuracy, WorldParams.max_motion_accuracy, false);
		sensorOpticalFlow = Tools.getPointByDistance(sensorOpticalFlow, rotation, distancedMoved * noiseToDistance);

		double noiseToRotation = Tools.noiseBetween(WorldParams.min_rotation_accuracy, WorldParams.max_rotation_accuracy, false);
		double milli_per_minute = 60000;
		gyroRotation += (1 - noiseToRotation) * deltaTime / milli_per_minute;
		gyroRotation = formatRotation(gyroRotation);

		// Simulate altitude change (example: based on speed and time)
		double altitudeChange = speed * ((double) deltaTime / 1000);
		altitude += altitudeChange;

		// Update battery
		battery.update(deltaTime, isCharging);
	}

	public static double formatRotation(double rotationValue) {
		rotationValue %= 360;
		if (rotationValue < 0) {
			rotationValue = 360 + rotationValue;
		}
		return rotationValue;
	}

	public double getRotation() {
		return rotation;
	}

	public double getGyroRotation() {
		return gyroRotation;
	}

	public Point getOpticalSensorLocation() {
		return new Point(sensorOpticalFlow);
	}

	public void adjustPosition(double deltaX, double deltaY) {
		// Adjust the drone's position by deltaX and deltaY
		this.startPoint.x += deltaX;
		this.startPoint.y += deltaY;
	}

	public void rotateLeft(int deltaTime) {
		double rotationChanged = WorldParams.rotation_per_second * deltaTime / 1000;

		rotation += rotationChanged;
		rotation = formatRotation(rotation);

		gyroRotation += rotationChanged;
		gyroRotation = formatRotation(gyroRotation);
	}

	public void rotateRight(int deltaTime) {
		double rotationChanged = -WorldParams.rotation_per_second * deltaTime / 1000;

		rotation += rotationChanged;
		rotation = formatRotation(rotation);

		gyroRotation += rotationChanged;
		gyroRotation = formatRotation(gyroRotation);
	}

	public void speedUp(int deltaTime) {
		speed += (WorldParams.accelerate_per_second * deltaTime / 1000);
		if (speed > WorldParams.max_speed) {
			speed = WorldParams.max_speed;
		}
	}

	public void slowDown(int deltaTime) {
		speed -= (WorldParams.accelerate_per_second * deltaTime / 1000);
		if (speed < 0) {
			speed = 0;
		}
	}

	public double getAltitude() {
		return altitude;
	}

	public void startCharging() {
		isCharging = true;
	}

	public void stopCharging() {
		isCharging = false;
	}

	boolean initPaint = false;
	BufferedImage mImage;

	public void paint(Graphics g) {
		if (!initPaint) {
			try {
				File f = new File(drone_img_path);
				mImage = ImageIO.read(f);
				initPaint = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Draw the drone image at its current location
		Point p = getPointOnMap();
		g.drawImage(mImage, (int) p.x, (int) p.y, mImage.getWidth(), mImage.getHeight(), null);

		for (int i = 0; i < lidars.size(); i++) {
			Lidar lidar = lidars.get(i);
			lidar.paint(g);
		}

		// Draw the battery information
		battery.paint(g);
	}

	public String getInfoHTML() {
		DecimalFormat df = new DecimalFormat("#.####");

		DroneSensorModel sensorModel = new DroneSensorModel(this);

		Point position = new Point(this.getPointOnMap().x, this.getPointOnMap().y);
		double orientation = this.getGyroRotation();

		DistanceResult distances = sensorModel.calculateDistances(position, orientation);

		String info = "<html><br><br>";
		info += "distanceForward: " + df.format(distances.getDistanceForward()) + " cm<br>";
		info += "distanceBackward: " + df.format(distances.getDistanceBackward()) + " cm<br>";
		info += "distanceRight: " + df.format(distances.getDistanceRight()) + " cm<br>";
		info += "distanceLeft: " + df.format(distances.getDistanceLeft()) + " cm<br>";
		info += "Location: " + pointFromStart + "<br>";
		info += "gyroRotation: " + df.format(gyroRotation) + "<br>";
		info += "sensorOpticalFlow: " + sensorOpticalFlow + "<br>";
		info += "</html>";
		return info;
	}


}
