package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/*
FUNCTION:
    Autonomous: Uses encoders only

    Steps:
        <optional delay>
        Drive to wall, slowing for last 6 inches
        Turn 45 degrees to parallel wall
        Drive to first beacon on slow
        <sense beacon, taking action, based on result>
        Drive to second beacon, slowing for last 6 inches
        <sense beacon, taking action, based on result>
        Drive to corner on slow
        Turn to align with center
        drive within shooting distance,
        Shoot ball(s)
        continue to center
        Stop
 */

@Autonomous(name="3 *NEW* Beacon SHORT (Req. ALLIANCE/DELAY)", group="Autonomous")
//@Disabled
public class W_Auto_Beacon_SHORT extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);   //, hardwareMap);
    //private Initialize init = new Initialize(telemetry);
    private Commands cmds = new Commands(robot, this);

    private int TimeDebugSleep = 1500;

    public void runOpMode() throws InterruptedException
    {
        int Direction = +1;

        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();
        sleep(TimeDebugSleep);

        robot.init(hardwareMap);
        sleep(TimeDebugSleep);

        robot.SetDefaults(hardwareMap, configs);    //hardwareMap);
        sleep(TimeDebugSleep);

        //init.InitializeHW(robot);

        telemetry.addData("Config", configs.ALLIANCE + " Alliance");
        telemetry.addData("Config", configs.AUTO_DELAY /1000 + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(configs.AUTO_DELAY);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();
        sleep(TimeDebugSleep);

        if(configs.ALLIANCE.equals("BLUE"))
        {
            //Drive in reverse
            Direction = -1;
        }

        //Drive to the wall, slowing for the last 6 inches
        cmds.EncoderDrive(//robot,
                configs.POWER_DRIVE, configs.DIST_SHORT_TO_WALL*Direction - (6*Direction), configs.DIST_SHORT_TO_WALL*Direction - (6*Direction), 5.0);
        sleep(TimeDebugSleep);
        cmds.EncoderDrive(//robot,
                configs.POWER_APPROACH, 6*Direction, 6*Direction, 5.0);
        sleep(TimeDebugSleep);

        //Turn to face beacon
        //cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, -Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, 5.0);
        if(configs.ALLIANCE.equals("RED"))
        {
            cmds.EncoderTurn(//robot,
                             "R",configs.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }
        else //BLUE
        {
            cmds.EncoderTurn(//robot,
                             "L",configs.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }
        //sleep(TimeDebugSleep);
        sleep(3000);

        //Drive to first beacon
        cmds.EncoderDrive(//robot,
                configs.POWER_APPROACH, configs.DIST_RAMP_TO_BEACON_1*Direction, configs.DIST_RAMP_TO_BEACON_1*Direction, 5.0);
        sleep(TimeDebugSleep);
        cmds.SenseBeacon(); //robot);
        sleep(TimeDebugSleep);

        //Drive to second beacon, slowing for the last 6 inches
        cmds.EncoderDrive(//robot,
                configs.POWER_DRIVE, configs.DIST_BEACON1_TO_BEACON_2*Direction - (6*Direction), configs.DIST_BEACON1_TO_BEACON_2*Direction - (6*Direction), 5.0);
        sleep(TimeDebugSleep);
        cmds.EncoderDrive(//robot,
                configs.POWER_APPROACH, 6*Direction, 6*Direction, 5.0);
        sleep(TimeDebugSleep);

        cmds.SenseBeacon(); //robot);
        sleep(TimeDebugSleep);

        //Drive to corner position
        cmds.EncoderDrive(//robot,
                configs.POWER_APPROACH, configs.DIST_BEACON2_TO_CORNER*Direction, configs.DIST_BEACON2_TO_CORNER*Direction, 5.0);
        sleep(TimeDebugSleep);

        //Turn to face center
        //cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.INCHES_FORTYFIVE_DEGREE_TURN*(-Sign), Configuration.INCHES_FORTYFIVE_DEGREE_TURN*Sign, 5.0);
        cmds.EncoderTurn(//robot,
                            "R",configs.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        sleep(TimeDebugSleep);

        //For RED, turn another 90 degrees
        if(configs.ALLIANCE.equals("RED"))
        {
            cmds.EncoderTurn(//robot,
                                "R",configs.INCHES_NINETY_DEGREE_TURN,5.0);
            sleep(TimeDebugSleep);
        }

        //reverse  to park on center
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, configs.POWER_DRIVE, configs.W_DIST_SHORT_TO_CENTER*(-Sign), configs.W_DIST_SHORT_TO_CENTER*(-Sign), 5.0);
        //}
        //else    //BLUE
        //{

        //Move close enough to shoot balls
        cmds.EncoderDrive(//robot,
                configs.POWER_DRIVE, configs.DIST_CORNER_TO_SHOOT, configs.DIST_CORNER_TO_SHOOT, 5.0);
        //sleep(TimeDebugSleep);

        robot.motorLaunch.setPower(configs.POWER_LAUNCH);

        //Use delay until ball launch is ready for use
        sleep(2000);

        robot.motorCollect.setPower(1.0);

        cmds.Shoot();   //robot);

        robot.motorCollect.setPower(0);
        sleep(TimeDebugSleep);

        //Drive to center
        cmds.EncoderDrive(//robot,
                configs.POWER_DRIVE, configs.DIST_CORNER_TO_PARK, configs.DIST_CORNER_TO_PARK, 5.0);
        sleep(TimeDebugSleep);
        //}

        cmds.StopDriving(); //robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}