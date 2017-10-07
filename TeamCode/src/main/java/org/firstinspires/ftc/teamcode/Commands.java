package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
PURPOSE:
    Define all movement commands

*/

public class Commands
{
    //Define local variables for parameters passed in
    LinearOpMode opMode;
    private Hardware robot;

    //Define variables local to the class
    private ElapsedTime runtime = new ElapsedTime();

    /* Constructor */
    public Commands(Hardware inrobot, LinearOpMode opMode)
    {
        this.robot = inrobot;
        this.opMode = opMode;
    }

    public void ExtendPusher()
    {
        opMode.telemetry.addData("ExtendPusher", "Extend Pusher Starting...");
        opMode.telemetry.update();

        if (opMode.opModeIsActive())
        {
            //try

            //{
            robot.servoPusher.setPosition(Configuration.POS_OUT_PUSHER_SERVO);
            opMode.sleep(3500);
            //Thread.sleep(2000);
            //}
            //catch (InterruptedException e)
            //{
            //    e.printStackTrace();
            //}
        }
        opMode.telemetry.addData("ExtendPusher", "Extend Pusher Complete!");
        opMode.telemetry.update();
    }

    public void RetractPusher()
    {
        
        opMode.telemetry.addData("RetractPusher", "Retract Pusher Starting...");
        opMode.telemetry.update();

        if (opMode.opModeIsActive())
        {
            //try
            //{
            robot.servoPusher.setPosition(Configuration.POS_IN_PUSHER_SERVO);
            opMode.sleep(3500);
            //Thread.sleep(2000);
            //}
            //catch (InterruptedException e)
            //{
            //    e.printStackTrace();
            //}
        }
        opMode.telemetry.addData("RetractPusher", "Retract Pusher Complete!");
        opMode.telemetry.update();
    }

    public void SenseBeacon() //Hardware robot)
    {
        boolean Searching = true;
        boolean BlueFound = false;
        boolean RedFound = false;

        opMode.telemetry.addData("SenseBeacon", "Beginning Beacon Sensing ...");
        opMode.telemetry.update();

        //Initial button push
        ExtendPusher();
        //opMode.sleep(1500);
        RetractPusher();
        //opMode.sleep(1500);

        runtime.reset();
        while ( opMode.opModeIsActive() &&
                robot.sensorColor.alpha() < 20 &&
                Searching &&
                runtime.seconds() < 7
                )
        {
            opMode.telemetry.addData("SenseBeacon", "> Red Value :" + robot.sensorColor.red());
            opMode.telemetry.addData("SenseBeacon", "> Blue Value: " + robot.sensorColor.blue());
            opMode.telemetry.update();
            opMode.sleep(1000);

            //Test for RED
            if (robot.sensorColor.red()>=Configuration.COLOR_RED_LOW && robot.sensorColor.red()<=Configuration.COLOR_RED_HIGH)
            {
                RedFound = true;
                opMode.telemetry.addData("SenseBeacon", "> FOUND Red - Value :" + robot.sensorColor.red());
                opMode.telemetry.update();
                //robot.devIM.setLED(1,true);     //Red
            }
            else
            {
                //Test for BLUE
                if (robot.sensorColor.blue() >= Configuration.COLOR_BLUE_LOW && robot.sensorColor.blue() <= Configuration.COLOR_BLUE_HIGH) {
                    BlueFound = true;
                    opMode.telemetry.addData("SenseBeacon", "> FOUND Blue - Value: " + robot.sensorColor.blue());
                    opMode.telemetry.update();
                    //robot.devIM.setLED(0, true);    //Blue
                }
            }

            //If beacon is the wrong color wait then push the button.
            //If beacon is the correct color, turn off searching to allow moving on
            if (    (   (RedFound) && (Configuration.ALLIANCE.equals("BLUE"))) |
                    (   (BlueFound) && (Configuration.ALLIANCE.equals("RED")))
                    )
            {
                //wait 6 seconds before determining whether to drive forward again (wrong color)
                //opMode.sleep(1500);

                ExtendPusher();
                //opMode.sleep(1500);
                RetractPusher();
                //opMode.sleep(1500);
            }
            else
            {
                Searching= false;
            }

            opMode.idle();
        }

        opMode.telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        opMode.telemetry.update();
    }

    public void SenseBeaconButtons()
    {
        boolean Searching = true;
        boolean BlueFound = false;
        boolean RedFound = false;
        double RemDistance = 0;

        opMode.telemetry.addData("SenseBeacon", "Beginning Beacon Sensing ...");
        opMode.telemetry.update();

        RemDistance = Configuration.DIST_BEACON1_TO_BEACON_2;

        //test color of first button

        //Test for RED
        if (robot.sensorColor.red()>=Configuration.COLOR_RED_LOW && robot.sensorColor.red()<=Configuration.COLOR_RED_HIGH)
        {
            RedFound = true;
        }
        else
        {
            //Test for BLUE
            if (robot.sensorColor.blue() >= Configuration.COLOR_BLUE_LOW && robot.sensorColor.blue() <= Configuration.COLOR_BLUE_HIGH) {
                BlueFound = true;
            }
        }

        //if button <> alliance
        if (    (   (RedFound) && (Configuration.ALLIANCE.equals("BLUE"))) |
                (   (BlueFound) && (Configuration.ALLIANCE.equals("RED")))
                )
        {
            //  drive 5 inches
            if (Configuration.ALLIANCE.equals("RED"))
            {
                EncoderDrive(Configuration.POWER_APPROACH, 5 , 5, 5.0);
            }
            else
            {
                EncoderDrive(Configuration.POWER_APPROACH, -5 , -5, 5.0);
            }
            RemDistance = RemDistance - 5;
        }

        //  press
        ExtendPusher();
        RetractPusher();

        //drive beacon to beacon minus 5
        if (Configuration.ALLIANCE.equals("RED"))
        {
                EncoderDrive(Configuration.POWER_DRIVE, RemDistance , RemDistance, 5.0);
        }
        else
        {
                EncoderDrive(Configuration.POWER_DRIVE, -RemDistance , -RemDistance, 5.0);
        }

        opMode.telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        opMode.telemetry.update();
    }

    public void EncoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS)
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;
        //int error;
        //double slavespeed = speed;

        opMode.telemetry.addData("EncoderDrive", "Drive:  L(" + leftInches +") R(" + rightInches + ") Starting...");
        opMode.telemetry.update();

        //System.out.println(Configuration.COUNTS_PER_INCH);

        //robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Display start position
        opMode.telemetry.addData("EncoderDrive", "> Starting at %7d :%7d",
                //robot.motorFrontLeft.getCurrentPosition(),
                //robot.motorFrontRight.getCurrentPosition(),
                robot.motorBackLeft.getCurrentPosition(),
                robot.motorBackRight.getCurrentPosition());
        opMode.telemetry.update();
        //opMode.sleep(1500);

        // Calculate new target position
        //newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
        //newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);
        newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
        newRightBackTarget = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);

        //Display target positions
        opMode.telemetry.addData("EncoderDrive", "> Destination of %7d :%7d",
                   //newLeftFrontTarget,newRightFrontTarget,
                newLeftBackTarget, newRightBackTarget);
        opMode.telemetry.update();
        //opMode.sleep(1500);

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive())
        {
            // Pass target position to motor controller
            //robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
            //robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
            robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
            robot.motorBackRight.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            //robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //speed = Math.abs(speed);
            //robot.motorFrontLeft.setPower(speed);
            //robot.motorFrontRight.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);

            // reset the timeout time and start motion.
            runtime.reset();

            // keep looping while we are still active, and there is time left, and both motors are running.
            while ( opMode.opModeIsActive() &&
                    runtime.seconds() < timeoutS &&
                    //robot.motorFrontLeft.isBusy() && robot.motorFrontRight.isBusy() &&
                    robot.motorBackLeft.isBusy() &&
                    robot.motorBackRight.isBusy()
                    )
            {
                // Display positions for the driver.
                //opMode.telemetry.addData("EncoderDrive", "> Currently at %7d :%7d",
                        //robot.motorFrontLeft.getCurrentPosition(), robot.motorFrontRight.getCurrentPosition(),
                //        robot.motorBackLeft.getCurrentPosition(),
                //        robot.motorBackRight.getCurrentPosition());
                //opMode.telemetry.addData("EncoderDrive", "> Destination of %7d :%7d",
                        //newLeftFrontTarget, newRightFrontTarget,
                //        newLeftBackTarget, newRightBackTarget);
                //error = robot.motorBackRight.getCurrentPosition() - robot.motorBackLeft.getCurrentPosition();
                //slavespeed = slavespeed + error * 0.2;

                //opMode.telemetry.addData("EncoderDrive","Diff: %7d apply slave speed = :%7d ?", error, slavespeed);
                //opMode.telemetry.addData("EncoderDrive","Slave Speed:  %7d", slavespeed);
                //opMode.telemetry.update();

                //robot.motorBackLeft.setPower();

                opMode.idle();
            }
            //opMode.sleep(3000);

            // Stop all motion;
            //StopDriving(); //robot);

            // Display current position
            opMode.telemetry.addData("EncoderDrive", "> Final position of %7d :%7d",
                    //robot.motorFrontLeft.getCurrentPosition(), robot.motorFrontRight.getCurrentPosition(),
                    robot.motorBackLeft.getCurrentPosition(),
                    robot.motorBackRight.getCurrentPosition());
            opMode.telemetry.update();
            //opMode.sleep(3000);

            //robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //idle();

            // Turn off RUN_TO_POSITION
            //robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
            //robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            opMode.idle();
        }
        opMode.telemetry.addData("EncoderDrive", "Drive:  L(" + leftInches +") R(" + rightInches + ") Complete!");
        opMode.telemetry.update();
    }

    public void EncoderTurn (String LR, double Inches, double timeoutS)
    {
        opMode.telemetry.addData("EncoderTurn", "Turn " + LR + "(" + Inches + ") Starting...");
        opMode.telemetry.update();

        if (LR.equals("L"))
        {
            EncoderDrive(Configuration.POWER_TURN, -Inches, Inches, timeoutS);
        }
        else
        {
            EncoderDrive(Configuration.POWER_TURN, Inches, -Inches, timeoutS);
        }
        opMode.telemetry.addData("EncoderTurn", "Turn " + LR + "(" + Inches + ") Complete!");
        opMode.telemetry.update();
    }

    public void Shoot()
    {
        opMode.telemetry.addData("LaunchNewBall", "Beginning Ball Launch ...");
        opMode.telemetry.update();

        if (opMode.opModeIsActive())
        {
            robot.motorLaunch.setPower(Configuration.POWER_LAUNCH);
            opMode.sleep(2000);
            robot.motorCollect.setPower(1.0);

            runtime.reset();
            while (opMode.opModeIsActive() &&
                    runtime.seconds() < Configuration.TIME_LAUNCH)
            {
                opMode.telemetry.addData("Status", " Wait for ball launch:  %2.5f S Elapsed", runtime.seconds());
                opMode.telemetry.update();

                opMode.idle();
            }

            robot.motorLaunch.setPower(0);
            robot.motorCollect.setPower(0);
        }

        opMode.telemetry.addData("LaunchNewBall", "Ball Launch Complete!");
        opMode.telemetry.update();
    }

    public void StopDriving() //Hardware robot)
    {
        opMode.telemetry.addData("Stop Drive", "Halting ...");
        opMode.telemetry.update();

        //robot.motorFrontRight.setPower(0);
        //robot.motorFrontLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorBackLeft.setPower(0);

        opMode.telemetry.addData("Stop Drive", "Halt Complete!");
        opMode.telemetry.update();
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

//    public double getError(Hardware robot, double targetAngle)
//    {
//        double robotError;
//
//        // calculate error in -179 to +180 range  (
//        robotError = targetAngle - robot.sensorGyro.getIntegratedZValue();
//        while (robotError > 180)  robotError -= 360;
//        while (robotError <= -180) robotError += 360;
//        return robotError;
//    }
//
//    public double getSteer(double error, double PCoeff)
//    {
//        return Range.clip(error * PCoeff, -1, 1);
//    }
//
//    public void GyroHold(Hardware robot, double speed, double angle, double holdTime)
//    {
//
//        //ElapsedTime holdTimer = new ElapsedTime();
//
//        // keep looping while we have time remaining.
//        //holdTimer.reset();
//        runtime.reset();
//
//        while ( opMode.opModeIsActive() &&
//                //(holdTimer.time() < holdTime))
//                (runtime.seconds() < holdTime))
//        {
//            // Update telemetry & Allow time for other processes to run.
//            onHeading(robot, speed, angle, Configuration.P_TURN_COEFF);
////            telemetry.update();
//            idle();
//        }
//
//        StopDriving(robot);
//    }

//    public void GyroDrive (Hardware robot,
//                           double speed,
//                           double distance,
//                           double angle,
//                           double timeoutS)
//    {
//        int newLeftFrontTarget;
//        int newRightFrontTarget;
//        int newLeftBackTarget;
//        int newRightBackTarget;
//
//        int     moveCounts;
//        double  max;
//        double  error;
//        double  steer;
//        double  leftSpeed;
//        double  rightSpeed;
//
//        telemetry.addData("GyroDrive", "GyroDrive Starting...");
//        telemetry.update();
//
//        //robot.sensorGyro.resetZAxisIntegrator();
//
//        // Determine new target position, and pass to motor controller
//        moveCounts = (int)(distance * Configuration.COUNTS_PER_INCH);
//
//        // Calculate new target position
//        newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + moveCounts;
//        newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + moveCounts;
//        newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + moveCounts;
//        newRightBackTarget = robot.motorBackRight.getCurrentPosition() + moveCounts;
//
//        // Set Target and Turn On RUN_TO_POSITION
//        robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
//        robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
//        robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
//        robot.motorBackRight.setTargetPosition(newRightBackTarget);
//
//        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        // Ensure that the opmode is still active
//        if (opMode.opModeIsActive())
//        {
//            //telemetry.addData("Debug", "Count:  " + Configuration.COUNTS_PER_INCH);
//            //telemetry.update();
//            //sleep(1000);
//
//            // start motion.
//            runtime.reset();
//            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
//            robot.motorFrontLeft.setPower(speed);
//            robot.motorFrontRight.setPower(speed);
//            robot.motorBackLeft.setPower(speed);
//            robot.motorBackRight.setPower(speed);
//
//            // keep looping while we are still active, and BOTH motors are running.
//            while (opMode.opModeIsActive() &&
//                    runtime.seconds() < timeoutS &&
//                    //robot.motorFrontLeft.isBusy() &&
//                    //robot.motorFrontRight.isBusy() &&
//                    robot.motorBackLeft.isBusy() &&
//                    robot.motorBackRight.isBusy()
//                    )
//            {
//                // adjust relative speed based on heading error.
//                error = getError(robot, angle);
//                steer = getSteer(error, Configuration.P_DRIVE_COEFF);
//
//                // if driving in reverse, the motor correction also needs to be reversed
//                if (distance < 0)
//                    steer *= -1.0;
//
//                leftSpeed = speed - steer;
//                rightSpeed = speed + steer;
//
//                // Normalize speeds if any one exceeds +/- 1.0;
//                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
//                if (max > 1.0)
//                {
//                    leftSpeed /= max;
//                    rightSpeed /= max;
//                }
//
//                robot.motorFrontLeft.setPower(leftSpeed);
//                robot.motorFrontRight.setPower(rightSpeed);
//                robot.motorBackLeft.setPower(leftSpeed);
//                robot.motorBackRight.setPower(rightSpeed);
//
//                // Display it for the driver.
//                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
//                telemetry.addData("GyroDrive", "> Currently at %7d :%7d :%7d :&7d",
//                        robot.motorFrontLeft.getCurrentPosition(),
//                        robot.motorFrontRight.getCurrentPosition(),
//                        robot.motorBackLeft.getCurrentPosition(),
//                        robot.motorBackRight.getCurrentPosition());
//                telemetry.addData("GyroDrive", "> Destination of %7d :%7d :%7d :&7d",
//                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
//                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
//                telemetry.update();
//
//                idle();
//            }
//
//            StopDriving(robot);
//        }
//
//        //robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //idle();
//
//        // Turn off RUN_TO_POSITION
//        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
//        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        telemetry.addData("GyroDrive", "GyroDrive Complete!");
//        telemetry.update();
//    }
//
//    public void GyroTurn(Hardware robot, double speed, double angle)
//    {
//        robot.sensorGyro.resetZAxisIntegrator();
//
//        while ( opMode.opModeIsActive() &&
//                !onHeading(robot, speed,angle, Configuration.P_TURN_COEFF))
//        {
//            telemetry.update();
//            idle();
//        }
//    }
//
//    boolean onHeading(Hardware robot, double speed, double angle, double PCoeff)
//    {
//        double   error ;
//        double   steer ;
//        boolean  onTarget = false ;
//        double leftSpeed;
//        double rightSpeed;
//
//        // determine turn power based on +/- error
//        error = getError(robot, angle);
//
//        if (Math.abs(error) <= Configuration.HEADING_THRESHOLD)
//        {
//            steer = 0.0;
//            leftSpeed  = 0.0;
//            rightSpeed = 0.0;
//            onTarget = true;
//        }
//        else
//        {
//            steer = getSteer(error, PCoeff);
//            rightSpeed  = speed * steer;
//            leftSpeed   = -rightSpeed;
//        }
//        if (opMode.opModeIsActive()) {
//            // Send desired speeds to motors.
//            robot.motorFrontLeft.setPower(leftSpeed);
//            robot.motorFrontRight.setPower(rightSpeed);
//            robot.motorBackLeft.setPower(leftSpeed);
//            robot.motorBackRight.setPower(rightSpeed);
//
//            // Display it for the driver.
//            telemetry.addData("Target", "%5.2f", angle);
//            telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
//            telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
//        }
//
//        return onTarget;
//    }
}