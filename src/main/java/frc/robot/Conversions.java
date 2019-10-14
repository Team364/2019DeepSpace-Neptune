package frc.robot;

import static frc.robot.RobotMap.*;

public class Conversions{

    public static double toCounts(double units){
        return units * ENCODERTICKS / 360.0;
    }

    public static double toDegrees(double units){
        return units * (360 / ENCODERTICKS);
    }

    public static double modulate360(double units){
        return units %= 360;
    }

    /**
     * @return encoder counts for the drive wheel to reach so that angle desired is acheived whilst
     * in X-drive formation
     */
    public static double degreeToCounts(double units){
        return units / 360 * WHEELBASE * ENCODERTICKS / WHEELDIAMETER;
    }
    
}