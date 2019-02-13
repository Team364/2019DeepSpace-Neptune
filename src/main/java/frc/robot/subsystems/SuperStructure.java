package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.util.RobotMap;
import frc.robot.util.PIDCalc;
import frc.robot.util.prefabs.subsystems.*;
import frc.robot.util.prefabs.commands.*;
import frc.robot.defaultcommands.DriveOpenLoop;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Robot;
import frc.robot.defaultcommands.*;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.States;
public class SuperStructure extends Subsystem {
  // public TalonBase rightDrive;
  // public TalonBase leftDrive;
  // public TalonBase lift;
  public ComplexTalon arm;
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

  public Piston claw;
  public Piston lever;
  public Piston back;
  public Piston front;
  public Piston shifter;

  private DoubleSolenoid cl;
  private DoubleSolenoid le;
  private DoubleSolenoid ba;
  private DoubleSolenoid wh;
  private DoubleSolenoid sh;

  public AHRS navX;
  public PIDCalc pidNavX;

  public DigitalInput iL;
  public DigitalInput aL;
  public DigitalInput lLL;
  public DigitalInput uLL;

  /**Access limit switches as follows
   * <p>0: Cargo
   * <p>1: Arm
   * <p>2: Lower Lift
   * <p>3: Upper Lift
   */
  public boolean[] limitArray = {false, false, false, false};

  public SuperStructure(){
    //masters
    rDrive = new TalonSRX(RobotMap.rightTopDrive);
    lDrive = new TalonSRX(RobotMap.leftTopDrive);
    lt = new TalonSRX(RobotMap.leftLift);
    a = new TalonSRX(RobotMap.arm);
    // in = new TalonSRX(RobotMap.rightClaw);
    in = new TalonSRX(10);

    // a = new TalonSRX(10);

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
    rightDrive = new BasicTalon(rDrive, 0.5, "Right Drive");
    rRearDriveSlave.follow(rDrive);
    rFrontDriveSlave.follow(rDrive);

    //Left Drive Train
    // leftDrive = new TalonBase(lDrive, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    leftDrive = new BasicTalon(lDrive, 0.5, "Left Drive");
    lRearDriveSlave.follow(lDrive);
    lFrontDriveSlave.follow(lDrive);

    driveTrain = new DriveTrain(leftDrive, rightDrive);
    
    //Lift
    // lift = new TalonBase(lt, 0, 0, 0.25, -0.25, 3750, 1500, true, 0, 10000, 0.4);
    lift = new BasicTalon(lt, 0.5, "Lift"){ 
      public void initDefaultCommand(){
        lift.setDefaultCommand(new BasicOpenLoop(lift, 0, 0.1));
      }
    };
    liftSlave.follow(lt);
    lLL = new DigitalInput(RobotMap.lowerLiftLimitSwitch);
    uLL = new DigitalInput(RobotMap.upperLiftLimitSwitch);
    
    //Arm
    arm = new ComplexTalon(a, 0, 0, 1, -1, 20000, 8000, false, 0, 10000, 0.8, "Arm"){
      public void initDefaultCommand(){
        arm.setDefaultCommand(new OpenLoop(arm, 5, 0.1));
      }
    };
    aL = new DigitalInput(RobotMap.armLimitSwitch);

    //Intake 
    // intake = new TalonBase(in, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.67);
    intake = new BasicTalon(in, 0.67, "Intake");
    intakeSlave.follow(in);
    iL = new DigitalInput(RobotMap.ballLimitSwitch);

    //Pistons
    claw = new Piston(cl, "Claw");
    lever = new Piston(le, "Lever");
    back = new Piston(ba, "Back");
    front = new Piston(wh, "Front");
    shifter = new Piston(sh, "Shifter");

    //Gyro
    navX = new AHRS(SPI.Port.kMXP);
    pidNavX = new PIDCalc(0.0005, 0.1, 50, 0, "NavX");
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
  public double getYaw(){
    return navX.getYaw();
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
    // arm.instrumentation();
    // rightDrive.instrumentation();
    // leftDrive.instrumentation();
  }

  public void postSmartDashVars(){
    intake.postSmartDashVars();
    arm.postSmartDashVars();
    claw.postSmartDashVars();
    lever.postSmartDashVars();
    back.postSmartDashVars();
    front.postSmartDashVars();
    shifter.postSmartDashVars();
    SmartDashboard.putString("Object State:", States.objState.toString());
    SmartDashboard.putString("Action State:", States.actionState.toString());
    SmartDashboard.putString("Loop State:", States.loopState.toString());
    SmartDashboard.putString("Drive State:", States.driveState.toString());
    SmartDashboard.putString("Drive Motion State:", States.driveMotionState.toString());
    SmartDashboard.putString("Score State:", States.scoreState.toString());
    SmartDashboard.putString("Climb State:", States.climbState.toString());

    SmartDashboard.putBoolean("Lim: ", limitArray[0]);
  }
  }

