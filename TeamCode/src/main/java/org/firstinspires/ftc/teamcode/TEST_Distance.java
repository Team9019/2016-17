package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TEST - Function", group="Utilities")
//@Disabled
public class TEST_Distance extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry, this);
    private Initialize init = new Initialize(telemetry);

    private int TimeDebugSleep = 1500;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        init.InitializeHW(robot);

        telemetry.addData("Config", Configuration.ALLIANCE + " Alliance");
        telemetry.addData("Config", Configuration.START_POSITION + " Starting Position");
        telemetry.addData("Config", Configuration.AUTO_DELAY /1000 + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //***** Place test command here *****

            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_SHOOT, Configuration.LONG_DIST_TO_SHOOT, 5.0);
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, 70, 70, 5.0);
            //cmds.SenseBeacon(robot);

        //***********************************
        sleep(TimeDebugSleep);

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
