import java.awt.Graphics;
public class Battery {
    private double batteryLevel; // Battery level in percentage (0 to 100)
    private double maxBatteryLevel;
    private double dischargeRate; // Rate at which battery discharges per second
    private double chargeRate; // Rate at which battery charges per second

    public Battery(double maxBatteryLevel, double dischargeRate, double chargeRate) {
        this.batteryLevel = maxBatteryLevel;
        this.maxBatteryLevel = maxBatteryLevel;
        this.dischargeRate = dischargeRate;
        this.chargeRate = chargeRate;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void update(int deltaTime, boolean isCharging) {
        if (isCharging) {
            batteryLevel += chargeRate * (deltaTime / 1000.0);
            if (batteryLevel > maxBatteryLevel) {
                batteryLevel = maxBatteryLevel;
            }
        } else {
            batteryLevel -= dischargeRate * (deltaTime / 1000.0);
            if (batteryLevel < 0) {
                batteryLevel = 0;
            }
        }
    }

    public void paint(Graphics g) {
        g.drawString("Battery: " + String.format("%.2f", batteryLevel) + "%", 10, 25);
    }
}
