package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.OpenLoop;
import frc.robot.util.PIDCalc;
import frc.robot.util.PistonBase;
import frc.robot.util.TalonBase;
import frc.robot.defaultcommands.DriveOpenLoop;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DigitalInput;
/**
 * Add your docs here.
 */
public class SuperStructure extends Subsystem {
  public TalonBase rightDrive;
  public TalonBase leftDrive;
  public TalonBase lift;
  public TalonBase arm;
  public TalonBase intake;
  
  private TalonSRX rDrive;
  private TalonSRX lDrive;
  private TalonSRX lt;
  private TalonSRX a;
  private TalonSRX in;

  private VictorSPX lRearDriveSlave;
  private VictorSPX lFrontDriveSlave;
  private VictorSPX rRearDriveSlave;
  private VictorSPX rFrontDriveSlave;
  private VictorSPX liftSlave;
  private VictorSPX intakeSlave;

  public PistonBase claw;
  public PistonBase lever;
  public PistonBase back;
  public PistonBase wheels;
  public PistonBase shifter;

  private DoubleSolenoid cl;
  private DoubleSolenoid le;
  private DoubleSolenoid ba;
  private DoubleSolenoid wh;
  private DoubleSolenoid sh;

  public AHRS navX;
  public PIDCalc pidNavX;

  private DigitalInput iL;
  private DigitalInput aL;
  private DigitalInput lLL;
  private DigitalInput uLL;


  public SuperStructure(){
    //masters
    rDrive = new TalonSRX(RobotMap.rightTopDrive);
    lDrive = new TalonSRX(RobotMap.leftTopDrive);
    lt = new TalonSRX(RobotMap.leftLift);
    a = new TalonSRX(RobotMap.arm);
    in = new TalonSRX(RobotMap.rightClaw);

    //followers
    lRearDriveSlave = new VictorSPX(RobotMap.leftRearDrive);
    lFrontDriveSlave = new VictorSPX(RobotMap.leftFrontDrive);
    rRearDriveSlave = new VictorSPX(RobotMap.rightRearDrive);
    rFrontDriveSlave = new VictorSPX(RobotMap.rightFrontDrive);
    liftSlave = new VictorSPX(RobotMap.rightLift);
    intakeSlave = new VictorSPX(RobotMap.leftClaw);

    //Pistons
    cl = new DoubleSolenoid(RobotMap.intakePort1, RobotMap.intakePort2);
    le = new DoubleSolenoid(RobotMap.leverPort1, RobotMap.leverPort2);
    ba = new DoubleSolenoid(RobotMap.climbPort1, RobotMap.climbPort2);
    wh = new DoubleSolenoid(RobotMap.climbPort3, RobotMap.climbPort4);
    sh = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);

    //Right Drive Train
    rightDrive = new TalonBase(rDrive, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    rightDrive.setDefaultCommand(new DriveOpenLoop());
    rRearDriveSlave.follow(rDrive);
    rFrontDriveSlave.follow(rDrive);

    //Left Drive Train
    leftDrive = new TalonBase(lDrive, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    leftDrive.setDefaultCommand(new DriveOpenLoop());
    lRearDriveSlave.follow(lDrive);
    lFrontDriveSlave.follow(lDrive);

    //Lift
    lift = new TalonBase(lt, 0, 0, 0.25, -0.25, 3750, 1500, true, 0, 10000, 0.4);
    lift.setDefaultCommand(new OpenLoop(lift, 0, 0.1));
    liftSlave.follow(lt);
    lLL = new DigitalInput(RobotMap.lowerLiftLimitSwitch);
    uLL = new DigitalInput(RobotMap.upperLiftLimitSwitch);
    
    //Arm
    arm = new TalonBase(a, 0, 0, 0.25, -0.25, 3750, 1500, true, 0, 10000, 0.4);
    arm.setDefaultCommand(new OpenLoop(arm, 0, 0.1));
    aL = new DigitalInput(RobotMap.armLimitSwitch);

    //Intake 
    intake = new TalonBase(in, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.67);
    intakeSlave.follow(in);
    iL = new DigitalInput(RobotMap.ballLimitSwitch);

    //Pistons
    claw = new PistonBase(cl);
    lever = new PistonBase(le);
    back = new PistonBase(ba);
    wheels = new PistonBase(wh);
    shifter = new PistonBase(sh);

    //Gyro
    navX = new AHRS(SPI.Port.kMXP);
    pidNavX = new PIDCalc(0.0005, 0.1, 50, 0, "NavX");

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new Periodic());
  }
  public void driveOpenLoop(double right, double left){
    rightDrive.openLoop(right);
    leftDrive.openLoop(left);
  }
  public void stopDrive(){
    rightDrive.stop();
    leftDrive.stop();
  }
  public void resetEncoders(){
    lift.zero();
    arm.zero();
  }
  public void postImplementation(){
    lift.instrumentation();
    arm.instrumentation();
    rightDrive.instrumentation();
    leftDrive.instrumentation();
  }
  private boolean getCargoLimitSwitch(){
    return iL.get();
  }
  private boolean getArmLimitSwitch(){
    return aL.get();
  }
  private boolean getLowerLiftLimitSwitch(){
    return lLL.get();
  }
  private boolean getUpperLiftLimitSwitch(){
    return uLL.get();
  }
  /**Access limit switches as follows
   * <p>0: Cargo
   * <p>1: Arm
   * <p>2: Lower Lift
   * <p>3: Upper Lift
   */
  public boolean[] limitArray = {getCargoLimitSwitch(), getArmLimitSwitch(), getLowerLiftLimitSwitch(), getUpperLiftLimitSwitch()};
  }

