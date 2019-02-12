package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.util.RobotMap;
import frc.robot.util.PIDCalc;
import frc.robot.subsystems.PistonBase;
import frc.robot.subsystems.talons.TalonBase;
import frc.robot.subsystems.talons.BasicTalon;
import frc.robot.defaultcommands.DriveOpenLoop;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.defaultcommands.*;
import frc.robot.subsystems.DriveTrain;

public class SuperStructure extends Subsystem {
  // public TalonBase rightDrive;
  // public TalonBase leftDrive;
  // public TalonBase lift;
  public TalonBase arm;
  // public TalonBase intake;

  public BasicTalon rightDrive;
  public BasicTalon leftDrive;
  public BasicTalon lift;
  public BasicTalon intake;
  
  private TalonSRX rDrive;
  private TalonSRX lDrive;
  private TalonSRX lt;
  private TalonSRX a;
  private TalonSRX in;


  public DriveTrain driveTrain;

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
    // a = new TalonSRX(RobotMap.arm);
    in = new TalonSRX(RobotMap.rightClaw);

    a = new TalonSRX(10);

    //followers
    lRearDriveSlave = new VictorSPX(RobotMap.leftRearDrive);
    lFrontDriveSlave = new VictorSPX(RobotMap.leftFrontDrive);
    rRearDriveSlave = new VictorSPX(RobotMap.rightRearDrive);
    rFrontDriveSlave = new VictorSPX(RobotMap.rightFrontDrive);
    liftSlave = new VictorSPX(RobotMap.rightLift);
    intakeSlave = new VictorSPX(RobotMap.leftClaw);

    //Pistons
    //PCM 1
    cl = new DoubleSolenoid(RobotMap.robotPCM, RobotMap.intakePort1, RobotMap.intakePort2);
    le = new DoubleSolenoid(RobotMap.robotPCM, RobotMap.leverPort1, RobotMap.leverPort2);
    sh = new DoubleSolenoid(RobotMap.robotPCM, RobotMap.shifterPort1, RobotMap.shifterPort2);
    //PCM 2
    ba = new DoubleSolenoid(RobotMap.climbPCM, RobotMap.climbPort1, RobotMap.climbPort2);
    wh = new DoubleSolenoid(RobotMap.climbPCM, RobotMap.climbPort3, RobotMap.climbPort4);
   

    //Right Drive Train
    // rightDrive = new TalonBase(rDrive, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    rightDrive = new BasicTalon(rDrive, 0.5);
    rRearDriveSlave.follow(rDrive);
    rFrontDriveSlave.follow(rDrive);

    //Left Drive Train
    // leftDrive = new TalonBase(lDrive, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    leftDrive = new BasicTalon(lDrive, 0.5);
    lRearDriveSlave.follow(lDrive);
    lFrontDriveSlave.follow(lDrive);

    driveTrain = new DriveTrain(leftDrive, rightDrive);
    
    //Lift
    // lift = new TalonBase(lt, 0, 0, 0.25, -0.25, 3750, 1500, true, 0, 10000, 0.4);
    lift = new BasicTalon(lt, 0.5){ 
      public void initDefaultCommand(){
        lift.setDefaultCommand(new BasicOpenLoop(lift, 0, 0.1));
      }
    };
    liftSlave.follow(lt);
    lLL = new DigitalInput(RobotMap.lowerLiftLimitSwitch);
    uLL = new DigitalInput(RobotMap.upperLiftLimitSwitch);
    
    //Arm
    arm = new TalonBase(a, 0, 0, 1, -1, 20000, 8000, false, 0, 10000, 0.8, "Arm"){
      public void initDefaultCommand(){
        arm.setDefaultCommand(new OpenLoop(arm, 5, 0.1));
      }
    };
    aL = new DigitalInput(RobotMap.armLimitSwitch);

    //Intake 
    // intake = new TalonBase(in, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.67);
    intake = new BasicTalon(in, 0.67);
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

    /*//Talons
    this.addChild(rightDrive); 
    this.addChild(leftDrive);
    this.addChild(driveTrain);
    this.addChild(lift);
    this.addChild(arm);
    this.addChild(intake);
    //Pistons
    this.addChild(claw);
    this.addChild(lever);
    this.addChild(back);
    this.addChild(wheels);
    this.addChild(shifter);*/
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new Periodic());
  }
  //Drive Train
  public void driveOpenLoop(double left, double right){
    driveTrain.openLoop(left, right);
  }
  public void stopDrive(){
    rightDrive.stop();
    leftDrive.stop();
  }
  public void resetDriveEncoders(){
    // rightDrive.zero();
    // leftDrive.zero();
  }
  //Gyro
  public void getYaw(){
    navX.getYaw();
  }
  public void zeroYaw(){
    navX.reset();
  }
  //Misc
  public void resetEncoders(){
    // lift.zero();
    arm.zero();
  }
  public void postImplementation(){
    // lift.instrumentation();
    arm.instrumentation();
    // rightDrive.instrumentation();
    // leftDrive.instrumentation();
  }
  //Limit Switches
  private boolean getCargoLimitSwitch(){
    // return iL.get();
    return false;
  }
  private boolean getArmLimitSwitch(){
    // return aL.get();
    return false;
  }
  private boolean getLowerLiftLimitSwitch(){
    // return lLL.get();
    return false;
  }
  private boolean getUpperLiftLimitSwitch(){
    // return uLL.get();
    return false;
  }
  /**Access limit switches as follows
   * <p>0: Cargo
   * <p>1: Arm
   * <p>2: Lower Lift
   * <p>3: Upper Lift
   */
  public boolean[] limitArray = {getCargoLimitSwitch(), getArmLimitSwitch(), getLowerLiftLimitSwitch(), getUpperLiftLimitSwitch()};
  public void postSmartDashVars(){
    arm.postSmartDashVars();
  }
  }

