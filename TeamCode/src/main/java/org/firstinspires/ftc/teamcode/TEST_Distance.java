package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Aimee on 3/2/2017.
 */

@Autonomous(name="TEST - Function", group="Utilities")
//@Disabled
public class TEST_Distance extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry, this);
    private Initialize init = new Initialize(telemetry);

    P_Menu_Commands menu =new P_Menu_Commands();

    private int TimeDebugSleep = 1500;

    @Override
    public void runOpMode() throws InterruptedException
    {
        menu.init(hardwareMap.appContext,this);

        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        init.InitializeHW(robot);

        //if Config Menu has been run, use it's value.  If not, program will continue to use configproperties value
//        if (menu.param.colorAlliance != null)
//        {
//            telemetry.addData("Config", "Menu:  " + menu.param.colorAlliance + " Alliance");
            Configuration.ALLIANCE = (String) menu.param.colorAlliance.name();
//        }
        telemetry.addData("Config", Configuration.ALLIANCE + " Alliance");

//        if (menu.param.autonType != null)
//        {
//            telemetry.addData("Config", "Menu:  " + menu.param.autonType + " Position");
            Configuration.START_POSITION =  (String) menu.param.autonType.name();
//        }
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
