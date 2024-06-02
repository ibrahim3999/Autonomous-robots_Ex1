public class PController {
    private double Kp, Ki;
    private double previousError, integral;

    public PController(double Kp, double Ki) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.previousError = 0;
        this.integral = 0;
    }

    public double calculate(double setpoint, double measuredValue, double deltaTime) {
        double error = setpoint - measuredValue;
        integral += error * deltaTime;
        double derivative = (error - previousError) / deltaTime;
        previousError = error;
        return Kp * error + Ki * integral ;
    }
}

