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
        Drive to center park position
        Stop
*/

@Autonomous(name="2 Center Park (Req. POS/DELAY)", group="Autonomous")
//@Disabled
public class W_Auto_Center_Park extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry,this);
    private Initialize init = new Initialize(telemetry);

    private int TimeDebugSleep = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        init.InitializeHW(robot);

        telemetry.addData("Config", Configuration.START_POSITION + " Starting Position");
        telemetry.addData("Config", Configuration.AUTO_DELAY /1000 + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.AUTO_DELAY);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();
        sleep(TimeDebugSleep);

        //Move close enough to shoot balls
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_CORNER_TO_SHOOT, Configuration.DIST_CORNER_TO_SHOOT, 5.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_SIDE_TO_SHOOT, Configuration.DIST_SIDE_TO_SHOOT, 5.0);
        }
        sleep(TimeDebugSleep);

        robot.motorLaunch.setPower(Configuration.POWER_LAUNCH);

        //Use delay until ball launch is ready for use
        sleep(2000);

        robot.motorCollect.setPower(1.0);

        cmds.Shoot(robot);

        robot.motorCollect.setPower(0);
        sleep(TimeDebugSleep);

        //Drive to center
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_CORNER_TO_PARK, Configuration.DIST_CORNER_TO_PARK, 5.0);
        }
        else
        {
            cmds.EncoderDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_SIDE_TO_PARK, Configuration.DIST_SIDE_TO_PARK, 5.0);
            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 6, 6, 5.0);
        }
        sleep(TimeDebugSleep);

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}