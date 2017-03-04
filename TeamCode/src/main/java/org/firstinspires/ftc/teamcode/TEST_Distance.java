package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Aimee on 3/2/2017.
 */

@Autonomous(name="TEST", group="Autonomous")
//@Disabled
public class TEST_Distance extends LinearOpMode
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

        //cmds.InitializeHW(robot);
        init.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //***** Place test command here *****

            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_SHOOT, Configuration.LONG_DIST_TO_SHOOT, 5.0);
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 70, 70, 5.0);
            //cmds.SenseBeacon(robot);

        //***********************************

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
