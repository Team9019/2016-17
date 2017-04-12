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

@Autonomous(name="Beacon Two Stops (Short Pos) #Alliance", group="Autonomous")
//@Disabled
public class W_Auto_Beacon_SHORT_Double extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);   //, hardwareMap);
    private Commands cmds = new Commands(robot, this);

    private int TimeDebugSleep = 0;

    public void runOpMode() throws InterruptedException
    {
        int Direction = +1;

        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();

        robot.init(hardwareMap);

        robot.SetDefaults(hardwareMap);

        telemetry.addData("Config", configs.ALLIANCE + " Alliance");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        if(configs.ALLIANCE.equals("BLUE"))
        {
            //Drive in reverse
            Direction = -1;
        }

        //Drive to the wall
        cmds.EncoderDrive(configs.POWER_DRIVE, configs.DIST_SHORT_TO_WALL * Direction, configs.DIST_SHORT_TO_WALL * Direction, 5.0);
        //sleep(250); //pause for momentum
        sleep(TimeDebugSleep);

        //Turn to face beacon
        if(configs.ALLIANCE.equals("RED"))
        {
            cmds.EncoderTurn("R",configs.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }
        else //BLUE
        {
            cmds.EncoderTurn("L",configs.INCHES_FORTYFIVE_DEGREE_TURN,5.0);
        }
        sleep(250); //pause for momentum
        sleep(TimeDebugSleep);

        //Drive to first beacon, first button
        if(configs.ALLIANCE.equals("RED")){
            cmds.EncoderDrive(configs.POWER_APPROACH, configs.DIST_RAMP_TO_BEACON_1 * Direction, configs.DIST_RAMP_TO_BEACON_1 * Direction, 5.0);
        }
        else
        {   //For BLUE ALLIANCE, the turn isn't as tight so the drive distance from the ramp to the beacon is less
            cmds.EncoderDrive(configs.POWER_APPROACH, (configs.DIST_RAMP_TO_BEACON_1-1) * Direction, (configs.DIST_RAMP_TO_BEACON_1-1) * Direction, 5.0);
        }
        sleep(TimeDebugSleep);

        cmds.StopDriving();

        cmds.SenseBeaconButtons();
        sleep(TimeDebugSleep);

        //Drive to second beacon, first button
        cmds.EncoderDrive(configs.POWER_DRIVE, configs.DIST_BEACON1_TO_BEACON_2 * Direction, configs.DIST_BEACON1_TO_BEACON_2 * Direction, 5.0);
        sleep(TimeDebugSleep);

        cmds.StopDriving();

        cmds.SenseBeaconButtons();
        sleep(TimeDebugSleep);

        cmds.StopDriving();

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}