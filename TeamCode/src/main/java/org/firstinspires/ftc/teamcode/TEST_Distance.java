package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="TEST - Function", group="Utilities")
//@Disabled
public class TEST_Distance extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);   //, hardwareMap);
    //private Initialize init = new Initialize(telemetry);
    private Commands cmds = new Commands(robot, this);

    private int TimeDebugSleep = 2500;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();
        sleep(TimeDebugSleep);

        robot.init(hardwareMap);
        sleep(TimeDebugSleep);

        robot.SetDefaults(hardwareMap, configs);    //hardwareMap);
        sleep(TimeDebugSleep);

        //init.InitializeHW(robot);
        //sleep(TimeDebugSleep);

        telemetry.addData("Config", configs.ALLIANCE + " Alliance");
        telemetry.addData("Config", configs.START_POSITION + " Starting Position");
        telemetry.addData("Config", configs.AUTO_DELAY /1000 + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //***********************************
        //Test push servo
            robot.servoPusher.setPosition(configs.POS_OUT_PUSHER_SERVO);
            sleep(1000);
            robot.servoPusher.setPosition(configs.POS_IN_PUSHER_SERVO);

        //***********************************
        //Test drive for 70 inches
            //cmds.EncoderDrive(configs.POWER_DRIVE, 70, 70, 5.0);

        //***********************************
        //Test sense beacon
            //cmds.SenseBeacon(robot);

        //***********************************

        sleep(TimeDebugSleep);

        cmds.StopDriving(); //robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
