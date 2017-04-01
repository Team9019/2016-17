package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TEST - Function", group="Utilities")
//@Disabled
public class TEST_Distance extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);
    private Commands cmds = new Commands(robot, this);

    private int TimeDebugSleep = 1500;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();
        //sleep(TimeDebugSleep);

        robot.init(hardwareMap);
        //sleep(TimeDebugSleep);

        robot.SetDefaults(hardwareMap); //, configs);    //hardwareMap);
        //sleep(TimeDebugSleep);

        telemetry.addData("Config", configs.ALLIANCE + " Alliance");
        telemetry.addData("Config", configs.START_POSITION + " Starting Position");
        telemetry.addData("Config", configs.AUTO_DELAY + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //***********************************
        //Test push servo
            cmds.ExtendPusher();
            sleep(5000);
            cmds.RetractPusher();
            sleep(5000);

        //***********************************
        //Test drive for 70 inches
            //cmds.EncoderDrive(configs.POWER_DRIVE, 70, 70, 5.0);

        //***********************************
        //Test sense beacon
            //cmds.SenseBeacon(robot);

        //***********************************
        sleep(TimeDebugSleep);

        cmds.StopDriving();

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
