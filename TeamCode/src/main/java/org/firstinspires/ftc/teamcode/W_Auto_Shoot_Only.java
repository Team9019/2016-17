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

@Autonomous(name="Shoot Only (Long Pos) #Delay", group="Autonomous")
//@Disabled
public class W_Auto_Shoot_Only extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);
    private Commands cmds = new Commands(robot, this);

    private int TimeDebugSleep = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();
        //sleep(TimeDebugSleep);

        robot.init(hardwareMap);
        //sleep(TimeDebugSleep);

        robot.SetDefaults(hardwareMap); //, configs);
        //sleep(TimeDebugSleep);

        //telemetry.addData("Config", configs.START_POSITION + " Starting Position");
        telemetry.addData("Config", configs.AUTO_DELAY  + " Sec. Delay");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(configs.AUTO_DELAY * 1000);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();
        sleep(TimeDebugSleep);

        //Move close enough to shoot balls
        if (configs.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(//robot,
                    configs.POWER_DRIVE, configs.DIST_CORNER_TO_SHOOT, configs.DIST_CORNER_TO_SHOOT, 5.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(//robot,
                    configs.POWER_DRIVE, configs.DIST_SIDE_TO_SHOOT, configs.DIST_SIDE_TO_SHOOT, 5.0);
        }
        cmds.StopDriving();

        //sleep(TimeDebugSleep);

        //robot.motorLaunch.setPower(configs.POWER_LAUNCH);

        //Use delay until ball launch is ready for use
        //sleep(2000);

        //robot.motorCollect.setPower(1.0);

        cmds.Shoot();   //robot);

        //robot.motorCollect.setPower(0);
        //sleep(TimeDebugSleep);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}