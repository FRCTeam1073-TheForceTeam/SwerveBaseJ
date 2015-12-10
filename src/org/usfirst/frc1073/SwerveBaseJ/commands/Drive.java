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
	 // The units of L and W are not relevant - only the ratio is used in calculations
    //drivetrain Wheelbase
    public final double L = 30;
    //drivetrain Trackwidth
    public final double W = 30;
    //hypotenuse
    public final double R = Math.sqrt(Math.pow(L, 2) + Math.pow(W, 2));
    
	public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        deadZone = 0.01;
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
		if(Math.abs(x) <= 0.04){
			x = 0;
		}
		if(Math.abs(y) <= 0.04){
			y = 0;
		}
		if(Math.abs(twist) <= 0.04){
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPIDEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
