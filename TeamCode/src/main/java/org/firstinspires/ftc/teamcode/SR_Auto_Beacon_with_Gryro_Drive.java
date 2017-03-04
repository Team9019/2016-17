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
        Turn 45% to aim to beacon
        Drive into beacon
        <back up 3 inches>
        <sense beacon, taking action, based on result>
        Stop
*/

@Autonomous(name="Beacon (Gyro)", group="Autonomous")
//@Disabled
class SR_Auto_Beacon_with_Gryro_Drive extends LinearOpMode
{
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

        ///cmds.InitializeHW(robot);
        init.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Move close enough to shoot balls
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_SHOOT, 0, 5.0);
        }
        else //SHORT
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_DIST_TO_SHOOT, 0, 5.0);
        }

        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        //Use delay until ball launch is ready for use
        sleep(500);

        robot.motorCollect.setPower(1.0);

        cmds.Shoot(robot);

        robot.motorCollect.setPower(0);

        //Drive to line up with closest beacon
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_FIRST_BEACON_AIM_DIST, 0, 5.0);
        }
        else
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_FIRST_BEACON_AIM_DIST, 0, 5.0);
        }

        //Turn to face beacon
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.FORTYFIVE_DEGREE_TURN_INCHES, Configuration.FORTYFIVE_DEGREE_TURN_INCHES, 5.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.FORTYFIVE_DEGREE_TURN_INCHES, -Configuration.FORTYFIVE_DEGREE_TURN_INCHES, 5.0);
        }

        //Drive into beacon, pressing button
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_FIRST_BEACON_DIST, 0, 5.0);
        }
        else
        {
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_FIRST_BEACON_DIST, 0, 5.0);
        }

        //Backup 3 inches to assess color
        //cmds.GyroDrive(robot,-Configuration.APPROACH_SPEED, 3, 0, 5.0);

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
        cmds.SenseBeacon(robot);

        //Turn 90 degrees to drive to second beacon
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}
        //else    //BLUE
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}

        //Drive to second beacon
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 40, 40, 5.0);
        //cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 40, 0, 5.0);

        //Turn 90 degrees to face beacon
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}
        //else    //BLUE
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
//        cmds.SenseBeacon(robot);

        cmds.StopDriving(robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}
