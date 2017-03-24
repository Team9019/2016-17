package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Disabled
public class SR_Auto_Beacon_SHORT_TWO extends LinearOpMode {
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);   //, hardwareMap);
    //private Initialize init = new Initialize(telemetry);
    private Commands cmds = new Commands(robot, this);

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();

        robot.init(hardwareMap);

        robot.SetDefaults(hardwareMap, configs);    //hardwareMap);

        //init.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.AUTO_DELAY);

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
            cmds.EncoderDrive(//robot,
                    Configuration.POWER_DRIVE,
                    Configuration.DIST_SIDE_TO_SHOOT + Configuration.DIST_SHORT_BEACON1_AIM + 2,
                    Configuration.DIST_SIDE_TO_SHOOT + Configuration.DIST_SHORT_BEACON1_AIM + 2,
                    5.0);
        }
        else
        {
            cmds.EncoderDrive(//robot,
                    Configuration.POWER_DRIVE,
                    Configuration.DIST_SIDE_TO_SHOOT + Configuration.DIST_SHORT_BEACON1_AIM ,
                    Configuration.DIST_SIDE_TO_SHOOT + Configuration.DIST_SHORT_BEACON1_AIM ,
                    5.0);
        }
        //Turn to face beacon
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(//robot,
                                Configuration.POWER_TURN, -Configuration.INCHES_NINETY_DEGREE_TURN, Configuration.INCHES_NINETY_DEGREE_TURN, 5.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(//robot,
                                Configuration.POWER_TURN, Configuration.INCHES_NINETY_DEGREE_TURN, -Configuration.INCHES_NINETY_DEGREE_TURN, 5.0);
        }

        //Due to the position of the beacon pusher, the RED field requires a different drive distance
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(//robot,
                                Configuration.POWER_DRIVE, Configuration.DIST_SHORT_FIRST_BEACON -1, Configuration.DIST_SHORT_FIRST_BEACON -1, 5.0);
        }
        else
        {
            cmds.EncoderDrive(//robot,
                                Configuration.POWER_DRIVE, Configuration.DIST_SHORT_FIRST_BEACON, Configuration.DIST_SHORT_FIRST_BEACON, 5.0);
        }

        //Backup 3 inches to assess color
        cmds.EncoderDrive(//robot,
                        Configuration.POWER_APPROACH, -3, -3, 5.0);

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
        cmds.SenseBeacon(); //robot);

        //Backup to center
        cmds.EncoderDrive(//robot,
                            Configuration.POWER_DRIVE, -48, -48, 5.0);

        cmds.StopDriving(); //robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}


