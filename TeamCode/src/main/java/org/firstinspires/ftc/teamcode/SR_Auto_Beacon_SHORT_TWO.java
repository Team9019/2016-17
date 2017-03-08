package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
FUNCTION:
    Autonomous: Uses encoders only

    Steps:
        <optional delay>
        Drive within shooting distance
        Shoot ball(s)
        Drive to align with first beacon
        Turn 90 degrees
        Drive into beacon
        <back up 3 inches>
        <sense beacon, taking action, based on result>
        Stop
 */

@Autonomous(name="4 Beacon SHORT TWO (Req. RED/BLUE/DELAY)", group="Autonomous")
//@Disabled
public class SR_Auto_Beacon_SHORT_TWO extends LinearOpMode {
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry, this);
    private Initialize init = new Initialize(telemetry);

    @Override
    public void runOpMode() throws InterruptedException
    {
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

        sleep(Configuration.AUTO_DELAY_TIME);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Move close enough to shoot balls
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_DIST_TO_SHOOT, Configuration.SHORT_DIST_TO_SHOOT, 5.0);


        //robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        //Use delay until ball launch is ready for use
        //sleep(2000);

        //robot.motorCollect.setPower(1.0);

        //cmds.Shoot(robot);

        //robot.motorCollect.setPower(0);

        //Due to the position of the beacon pusher, the RED field requires a different drive distance
        if(Configuration.ALLIANCE.equals("RED")) {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER,
                    Configuration.SHORT_DIST_TO_SHOOT + Configuration.SHORT_FIRST_BEACON_AIM_DIST + 2,
                    Configuration.SHORT_DIST_TO_SHOOT + Configuration.SHORT_FIRST_BEACON_AIM_DIST + 2,
                    5.0);
        }
        else
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER,
                    Configuration.SHORT_DIST_TO_SHOOT + Configuration.SHORT_FIRST_BEACON_AIM_DIST ,
                    Configuration.SHORT_DIST_TO_SHOOT + Configuration.SHORT_FIRST_BEACON_AIM_DIST ,
                    5.0);
        }
        //Turn to face beacon
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 5.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 5.0);
        }

        //Due to the position of the beacon pusher, the RED field requires a different drive distance
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_FIRST_BEACON_DIST -1, Configuration.SHORT_FIRST_BEACON_DIST -1, 5.0);
        }
        else
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_FIRST_BEACON_DIST, Configuration.SHORT_FIRST_BEACON_DIST, 5.0);
        }

        //Backup 3 inches to assess color
        cmds.EncoderDrive(robot,Configuration.APPROACH_SPEED, -3, -3, 5.0);

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
        cmds.SenseBeacon(robot);

        //Backup to center
        cmds.EncoderDrive(robot,Configuration.DRIVE_POWER, -48, -48, 5.0);

        cmds.StopDriving(robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}


