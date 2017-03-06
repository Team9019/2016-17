package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Define all movement commands
*/

public class Commands extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    LinearOpMode opMode;

    /* Constructor */
    public Commands(Telemetry telemetry, LinearOpMode opMode)
    {
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void runOpMode()
    {
    }

    public void EncoderDrive(Hardware robot,
                             double speed,
                             double leftInches, double rightInches,
                             double timeoutS) //throws InterruptedException
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        telemetry.addData("EncoderDrive", "EncoderDrive Starting...");
        telemetry.update();

        //System.out.println(Configuration.COUNTS_PER_INCH);
        //System.out.println(opModeIsActive());
        //System.out.println(opMode.opModeIsActive());

        //robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Display start position
        telemetry.addData("EncoderDrive", "> Starting at %7d :%7d :%7d :%7d",
                robot.motorFrontLeft.getCurrentPosition(),
                robot.motorFrontRight.getCurrentPosition(),
                robot.motorBackLeft.getCurrentPosition(),
                robot.motorBackRight.getCurrentPosition());
        telemetry.update();
        sleep(1500);

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive())
        {
            // Calculate new target position
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
            newRightBackTarget = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);

            //Display target positions
            telemetry.addData("EncoderDrive", "> Destination of %7d :%7d :%7d :%7d",
                   newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
            telemetry.update();
            sleep(1500);

            // Pass target position to motor controller
            robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
            robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
            robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
            robot.motorBackRight.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            speed = Math.abs(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while ( opMode.opModeIsActive() &&
                    runtime.seconds() < timeoutS &&
                    robot.motorFrontLeft.isBusy() &&
                    robot.motorFrontRight.isBusy() //&&
                    //robot.motorBackLeft.isBusy() &&
                    //robot.motorBackRight.isBusy()
                    )
            {
                // Display positions for the driver.
                telemetry.addData("EncoderDrive", "> Currently at %7d :%7d :%7d :%7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition());
                telemetry.addData("EncoderDrive", "> Destination of %7d :%7d :%7d :%7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.update();

                //sleep(1000);
                idle();
            }

            // Stop all motion;
            StopDriving(robot);

            // Display current position
            telemetry.addData("EncoderDrive", "> Final position of %7d :%7d :%7d :%7d",
                    robot.motorFrontLeft.getCurrentPosition(),
                    robot.motorFrontRight.getCurrentPosition(),
                    robot.motorBackLeft.getCurrentPosition(),
                    robot.motorBackRight.getCurrentPosition());
            telemetry.update();
            sleep(3000);

            robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            idle();

            // Turn off RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //idle();   // optional pause after each move
        }
        telemetry.addData("EncoderDrive", "EncoderDrive Complete!");
        telemetry.update();
    }

    public void Shoot(Hardware robot) //throws InterruptedException
    {
        telemetry.addData("LaunchNewBall", "Beginning Ball Launch ...");
        telemetry.update();

        if (opMode.opModeIsActive())
        {
            runtime.reset();
            robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

            runtime.reset();
            while (opMode.opModeIsActive() &&
                    runtime.milliseconds() < Configuration.LAUNCH_TIME) {
                telemetry.addData("Status", " Wait for ball launch:  %2.5f S Elapsed", runtime.seconds());
                telemetry.update();

                idle();
            }

            robot.motorLaunch.setPower(0);
        }

        telemetry.addData("LaunchNewBall", "Ball Launch Complete!");
        telemetry.update();
    }

    public void GyroDrive (Hardware robot,
                           double speed,
                           double distance,
                           double angle,
                           double timeoutS)
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        telemetry.addData("GyroDrive", "GyroDrive Starting...");
        telemetry.update();

        //robot.sensorGyro.resetZAxisIntegrator();

        // Determine new target position, and pass to motor controller
        moveCounts = (int)(distance * Configuration.COUNTS_PER_INCH);

        // Calculate new target position
        newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + moveCounts;
        newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + moveCounts;
        newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + moveCounts;
        newRightBackTarget = robot.motorBackRight.getCurrentPosition() + moveCounts;

        // Set Target and Turn On RUN_TO_POSITION
        robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
        robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
        robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
        robot.motorBackRight.setTargetPosition(newRightBackTarget);

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive())
        {
            //telemetry.addData("Debug", "Count:  " + Configuration.COUNTS_PER_INCH);
            //telemetry.update();
            //sleep(1000);

            // start motion.
            runtime.reset();
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opMode.opModeIsActive() &&
                    runtime.seconds() < timeoutS &&
                    //robot.motorFrontLeft.isBusy() &&
                    //robot.motorFrontRight.isBusy() &&
                    robot.motorBackLeft.isBusy() &&
                    robot.motorBackRight.isBusy()
                    )
            {
                // adjust relative speed based on heading error.
                error = getError(robot, angle);
                steer = getSteer(error, Configuration.P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if any one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.motorFrontLeft.setPower(leftSpeed);
                robot.motorFrontRight.setPower(rightSpeed);
                robot.motorBackLeft.setPower(leftSpeed);
                robot.motorBackRight.setPower(rightSpeed);

                // Display it for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("GyroDrive", "> Currently at %7d :%7d :%7d :&7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition());
                telemetry.addData("GyroDrive", "> Destination of %7d :%7d :%7d :&7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();

                idle();
            }

            StopDriving(robot);
        }

        //robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //idle();

        // Turn off RUN_TO_POSITION
        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("GyroDrive", "GyroDrive Complete!");
        telemetry.update();
    }

    public void GyroTurn(Hardware robot, double speed, double angle)
    {
        robot.sensorGyro.resetZAxisIntegrator();

        while ( opMode.opModeIsActive() &&
                !onHeading(robot, speed,angle, Configuration.P_TURN_COEFF))
        {
            telemetry.update();
            idle();
        }
    }

    boolean onHeading(Hardware robot, double speed, double angle, double PCoeff)
    {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(robot, angle);

        if (Math.abs(error) <= Configuration.HEADING_THRESHOLD)
        {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else
        {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }
        if (opMode.opModeIsActive()) {
            // Send desired speeds to motors.
            robot.motorFrontLeft.setPower(leftSpeed);
            robot.motorFrontRight.setPower(rightSpeed);
            robot.motorBackLeft.setPower(leftSpeed);
            robot.motorBackRight.setPower(rightSpeed);

            // Display it for the driver.
            telemetry.addData("Target", "%5.2f", angle);
            telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
            telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
        }

        return onTarget;
    }

    public double getError(Hardware robot, double targetAngle)
    {
        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.sensorGyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public double getSteer(double error, double PCoeff)
    {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void GyroHold(Hardware robot, double speed, double angle, double holdTime)
    {

        //ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        //holdTimer.reset();
        runtime.reset();

        while ( opMode.opModeIsActive() &&
                //(holdTimer.time() < holdTime))
                (runtime.seconds() < holdTime))
        {
            // Update telemetry & Allow time for other processes to run.
            onHeading(robot, speed, angle, Configuration.P_TURN_COEFF);
//            telemetry.update();
            idle();
        }

        StopDriving(robot);
    }

    public void StopDriving(Hardware robot)
    {
        telemetry.addData("Stop Drive", "Halting ...");
        telemetry.update();

        robot.motorFrontRight.setPower(0);
        robot.motorFrontLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorBackLeft.setPower(0);

        telemetry.addData("Stop Drive", "Halt Complete!");
        telemetry.update();
    }

    public void SenseBeacon(Hardware robot)
    {
        telemetry.addData("SenseBeacon", "Beginning Beacon Sensing ...");
        telemetry.update();

        // convert the RGB values to HSV values.
        //float hsvValues [] = {0,0,0};
        //Color.RGBToHSV(robot.sensorColor.red() * 8, robot.sensorColor.green() * 8, robot.sensorColor.blue() * 8, hsvValues);

//        robot.sensorColor.red();
//        robot.sensorColor.blue();
//        robot.sensorColor.green();
//        robot.sensorColor.alpha();    //luminosity
//        robot.sensorColor.argb();     //hue

        runtime.reset();

        //temporary loop (15 seconds) to test beacon
        while ( opMode.opModeIsActive() &&
                runtime.seconds() < 15)
        {
            telemetry.addData("SenseBeacon", "> Red Value :" + robot.sensorColor.red());
            telemetry.addData("SenseBeacon", "> Blue Value: " + robot.sensorColor.blue());
            telemetry.update();

            idle();
        }

//        while ( opMode.opModeIsActive() &&
//                robot.sensorColor.alpha() < 20 &&
//                runtime.seconds() < 15)
//        {
//            telemetry.addData("SenseBeacon", "> Red Value :" + robot.sensorColor.red());
//            telemetry.addData("SenseBeacon", "> Blue Value: " + robot.sensorColor.blue());
//            telemetry.update();
//
//            if (robot.sensorColor.red()>=8)
//            {
//                telemetry.addData("SenseBeacon", "> FOUND Red - Value :" + robot.sensorColor.red());
//                telemetry.update();
//                robot.devIM.setLED(1,true);     //Red
//                robot.devIM.setLED(0,false);    //Blue
//
//                if (Configuration.ALLIANCE.equals("BLUE"))
//                {
//                    //wait 6 seconds before determining whether to drive forward again (wrong color)
//                    sleep(6000);
//
//                    // Drive forward 3 inches to bump beacon, then back off
//                    //EncoderDrive(robot, Configuration.APPROACH_SPEED, 3, 3, 3.0);
//                    //EncoderDrive(robot, Configuration.APPROACH_SPEED,-3,-3, 3.0);
//                }
//            }
//            else if (robot.sensorColor.blue()<=3)
//            {
//                telemetry.addData("SenseBeacon", "> FOUND Blue - Value: " + robot.sensorColor.blue());
//                telemetry.update();
//                robot.devIM.setLED(1,false);     //Red
//                robot.devIM.setLED(0,true);    //Blue
//
//                if (Configuration.ALLIANCE.equals("RED"))
//                {
//                    //wait 6 seconds before determining whether to drive forward again (wrong color)
//                    sleep(6000);
//
//                    // Drive forward 3 inches to bump beacon, then back off
//                    //EncoderDrive(robot, Configuration.APPROACH_SPEED, 3, 3, 3.0);
//                    //EncoderDrive(robot, Configuration.APPROACH_SPEED,-3,-3, 3.0);
//                }
//            }
//            idle();
//        }

        telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        telemetry.update();
    }

//    public void DriveForward(Hardware robot, double power, int drivetime)
//    {
//        telemetry.addData("Drive", "Begin Drive ...");
//        telemetry.update();
//
//        // No telemetry message since drive will not complete until sleep expires.
//        robot.motorFrontRight.setPower(power);
//        robot.motorFrontLeft.setPower(power);
//        robot.motorBackRight.setPower(power);
//        robot.motorBackLeft.setPower(power);
//
//        runtime.reset();
//        while (runtime.milliseconds() < drivetime)
//        {
//            telemetry.addData("Status", "Drive time: " + runtime.toString());
//            telemetry.update();
//        }
//
//        telemetry.addData("Drive", "Drive Complete!");
//        telemetry.update();
//    }

//    public void DriveReverse(Hardware robot, double power, int drivetime)
//    {
//        // No telemetry message since drive will not complete until sleep expires.
//        DriveForward(robot, -power, drivetime);
//    }

//    public void TurnLeft(Hardware robot, double power, int turntime)
//    {
//        telemetry.addData("Turn", "Begin Turn ...");
//        telemetry.update();
//
//        // No telemetry message since drive will not complete until sleep expires.
//        robot.motorFrontRight.setPower(power);
//        robot.motorFrontLeft.setPower(-power);
//        robot.motorBackRight.setPower(power);
//        robot.motorBackLeft.setPower(-power);
//
//        runtime.reset();
//        while (runtime.milliseconds() < turntime)
//        {
//            telemetry.addData("Status", "Turn time: " + runtime.toString());
//            telemetry.update();
//        }
//
//        telemetry.addData("Turn", "Turn Complete!");
//        telemetry.update();
//    }

//    public void TurnRight(Hardware robot, double power, int turntime)
//    {
//        TurnLeft(robot, -power, turntime);
//    }
}