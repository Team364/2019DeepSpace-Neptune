/*
 * This is the PID calculation class. It calculates a PIDF output for
 * a motor using a setpoint and actual values.
 * Create an object for each subsystem that needs to use PID and run resetPID() and then
 * calculateOutput().
 */

package frc.robot.misc;

public class PIDCalc {

    private String pidName;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;
    private double derivative = 0;
    private double integral = 0;
    private double prev_error = 0;
    private double error = 0;
    private double result = 0;
    private boolean onTarget;
    private double tolerance;

    public PIDCalc(double pTerm, double iTerm, double dTerm, double fTerm, String name) {
        setPIDParameters(pTerm, iTerm, dTerm, fTerm);
        pidName = name;
    }

    public double calculateOutput(double setpoint, double actual) {
        error = setpoint - actual;
        integral += (error * 0.02);
        derivative = (error - prev_error) / 0.02;
        result = kF + (kP * error) + (kI * integral) + (kD * derivative);
        if (result > 1) {
            result = 1;
        } else if (result < -1) {
            result = -1;
        }
        smartDashVars();
        prev_error = error;
        return result;
    }

    public void resetPID() {
        derivative = 0;
        integral = 0;
        prev_error = 0;
        error = 0;
    }

    public double getError() {
        return error;
    }

    public void setPIDParameters(double pTerm, double iTerm, double dTerm, double fTerm) {
        kP = pTerm;
        kI = iTerm;
        kD = dTerm;
        kF = fTerm;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public boolean onTarget() {
        return Math.abs(getError()) < tolerance;
    }

    private void smartDashVars() {
        // SmartDashboard.putNumber(pidName + "Error", error);
        // SmartDashboard.putNumber(pidName + "Prev Error", prev_error);
        // SmartDashboard.putNumber(pidName + "Integral", integral);
        // SmartDashboard.putNumber(pidName + "Derivative", derivative);
        // SmartDashboard.putNumber(pidName + "Result", result);
        // SmartDashboard.putNumber(pidName + "kP", kP);
    }

}
