// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1073.SwerveBaseJ.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc1073.SwerveBaseJ.Robot;

/**
 *
 */
public class  Drive extends Command implements PIDCommand {
	
	private double deadZone;
	private double errorFL;
	private double errorFR;
	private double errorBL;
	private double errorBR;
	
	private double setpointFL;
	private double setpointFR;
	private double setpointBL;
	private double setpointBR;
	
	// The units of L and W are not relevant - only the ratio is used in calculations
    //drivetrain Wheelbase
    public final double L = 30;
    //drivetrain Trackwidth
    public final double W = 30;
    //hypotenuse
    public final double R = Math.sqrt(Math.pow(L, 2) + Math.pow(W, 2));
    //SWERVE MODULE LIMITS (DO NOT CHANGE):
    private final int F_MIN_LIMIT = -20;
    private final int B_MAX_LIMIT = 25;
	public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        deadZone = Robot.deadZone;
        errorFL = 0;
        errorFR = 0;
        errorBL = 0;
        errorBR = 0;
        
        setpointBL = 0;
        setpointBR = 0;
        setpointFR = 0;
        setpointFL = 0;
        
    }

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//get the joystick values for swerve drive
		//axis designations may need to change. 
    	double y = -Robot.oi.getDriver().getY();
    	double x = Robot.oi.getDriver().getX();
		double twist = Robot.oi.getDriver().getRawAxis(5);
		
		//deadzone adjust
		if(Math.abs(x) <= deadZone){
			x = 0;
		}
		if(Math.abs(y) <= deadZone){
			y = 0;
		}
		if(Math.abs(twist) <= deadZone){
			twist = 0;
		}
		
		//Swerve Deflections below. See Ether's swerve deflections document for explanation
		
		double A = x - (twist * (L/R));
		double B = x + (twist * (L/R));
		double C = y - (twist * (W/R));
		double D = y + (twist * (W/R));
		
		double frontRightWS = Math.sqrt(Math.pow(B, 2) + Math.pow(C, 2));
		double frontLeftWS = Math.sqrt(Math.pow(B, 2) + Math.pow(D, 2));
		double backLeftWS = Math.sqrt(Math.pow(A, 2) + Math.pow(D, 2));
		double backRightWS = Math.sqrt(Math.pow(A, 2) + Math.pow(C, 2));
		
		double frontRightWA = Math.atan2(B, C) * (-180/Math.PI);
		double frontLeftWA = Math.atan2(B, D) * (-180/Math.PI);
		double backLeftWA = Math.atan2(A, D) * (-180/Math.PI);
		double backRightWA = Math.atan2(A, C) * (-180/Math.PI);
		
		//Operational Swerve Module calculations below
		if(frontRightWA < F_MIN_LIMIT){
			frontRightWA += 180;
			frontRightWS *= -1;
		}
		if(frontLeftWA < F_MIN_LIMIT){
			frontLeftWA += 180;
			frontLeftWS *= -1;
		}
		if(backLeftWA > B_MAX_LIMIT){
			backLeftWA -= 180;
			backLeftWS *= -1;
		}
		if(backRightWA > B_MAX_LIMIT){
			backRightWA -= 180;
			backRightWS *= -1;
		}
		//uncomment any of the below to flip wheel speed direction:
		//frontLeftWS *= -1;
		//frontRightWS *= -1;
		//backLeftWS *= -1;
		//backRightWS *= -1;
		
		setpointFL = frontLeftWA/180;
		setpointFR = frontRightWA/180;
		setpointBL = backLeftWA/180;
		setpointBR = backRightWA/180;
		
		//Don't drive the motors unless the modules are almost in position
		if(errorFL >= 10 || errorFR >= 10 || errorBL >= 10 || errorBR >= 10){
			Robot.driveTrain.setFrontLeftSpeed(0);
			Robot.driveTrain.setFrontRightSpeed(0);
			Robot.driveTrain.setRearLeftSpeed(0);
			Robot.driveTrain.setRearRightSpeed(0);
		}
		else{
			Robot.driveTrain.setFrontLeftSpeed(frontLeftWS);
			Robot.driveTrain.setFrontRightSpeed(frontRightWS);
			Robot.driveTrain.setRearLeftSpeed(backLeftWS);
			Robot.driveTrain.setRearRightSpeed(backRightWS);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	public double getPIDSetpoint(int marker) {
		switch(marker){
		case 0: return setpointFL;
		case 2: return setpointFR;
		case 4: return setpointBL;
		case 6: return setpointBR;
		default: return 0;
		}
	}

	@Override
	public boolean isPIDEnabled() {
		return true;
	}

	@Override
	public void updateError(double error, int marker) {
		switch(marker){
		case 0: errorFL = error; break;
		case 2: errorFR = error; break;
		case 4: errorBL = error; break;
		case 6: errorBR = error; break; 
		}
		
	}
}
