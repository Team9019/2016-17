package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
FUNCTION:
    Autonomous: Uses encoders only

    Steps:
        <optional delay>
        Drive to wall
        Turn 45 degrees to parallel wall
        Drive to first beacon
        <sense beacon, taking action, based on result>
        Drive to second beacon
        <sense beacon, taking action, based on result>
        Turn 45 degrees to align with center
        If RED,
            drive within shooting distance,
            Shoot ball(s)
            continue to center
        Else
            Drive to center
        Stop
 */

@Autonomous(name="3 Beacon SHORT *NEW* (Req. RED/BLUE/DELAY)", group="Autonomous")
//@Disabled
public class W_Auto_Beacon_SHORT extends LinearOpMode {
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry, this);
    private Initialize init = new Initialize(telemetry);

    @Override
    public void runOpMode() throws InterruptedException
    {
        int Sign;

        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        init.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.TIME_AUTO_DELAY);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        if(Configuration.ALLIANCE.equals("RED"))
        {
            //Drive forward
            Sign = +1;
        }
        {
            //Drive in reverse
            Sign = -1;
        }

        //Drive to the wall, slowing for the last 6 inches
        cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_SHORT_TO_WALL*Sign - (6*Sign), Configuration.DIST_SHORT_TO_WALL*Sign - (6*Sign), 5.0);
        cmds.EncoderDrive(robot, Configuration.POWER_APPROACH, 6*Sign, 6*Sign, 5.0);

        //Turn to face beacon
        //cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, -Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, 5.0);
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderTurn(robot,"R",Configuration.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }
        else //BLUE
        {
            cmds.EncoderTurn(robot,"L",Configuration.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }

        //Drive to first beacon
        cmds.EncoderDrive(robot, Configuration.POWER_APPROACH, Configuration.DIST_RAMP_TO_BEACON_1*Sign, Configuration.DIST_RAMP_TO_BEACON_1*Sign, 5.0);

        cmds.SenseBeacon(robot);

        //Drive to second beacon, slowing for the last 6 inches
        cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_BEACON1_TO_BEACON_2*Sign - (6*Sign), Configuration.DIST_BEACON1_TO_BEACON_2*Sign - (6*Sign), 5.0);
        cmds.EncoderDrive(robot, Configuration.POWER_APPROACH, 6*Sign, 6*Sign, 5.0);

        cmds.SenseBeacon(robot);

        //Drive to corner position
        cmds.EncoderDrive(robot, Configuration.POWER_APPROACH, Configuration.DIST_BEACON2_TO_CORNER*Sign, Configuration.DIST_BEACON2_TO_CORNER*Sign, 5.0);

        //Turn to face center
        //cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.INCHES_FORTYFIVE_DEGREE_TURN*(-Sign), Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, 5.0);
        cmds.EncoderTurn(robot,"R",Configuration.INCHES_FORTYFIVE_DEGREE_TURN,5.0);

        //For RED, turn another 90 degrees
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderTurn(robot,"R",Configuration.INCHES_NINETY_DEGREE_TURN,5.0);
        }

        //reverse  to park on center
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.W_DIST_SHORT_TO_CENTER*(-Sign), Configuration.W_DIST_SHORT_TO_CENTER*(-Sign), 5.0);
        //}
        //else    //BLUE
        //{
            //Move close enough to shoot balls
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_CORNER_TO_SHOOT, Configuration.DIST_CORNER_TO_SHOOT, 5.0);

            robot.motorLaunch.setPower(Configuration.POWER_LAUNCH);

            //Use delay until ball launch is ready for use
            sleep(2000);

            robot.motorCollect.setPower(1.0);

            cmds.Shoot(robot);

            robot.motorCollect.setPower(0);

            //Drive to center
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_CORNER_TO_PARK, Configuration.DIST_CORNER_TO_PARK, 5.0);
        //}

        cmds.StopDriving(robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}