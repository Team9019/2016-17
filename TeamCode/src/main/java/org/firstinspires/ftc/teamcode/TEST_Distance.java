package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TEST - Function", group="Utilities")
//@Disabled
public class TEST_Distance extends LinearOpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);
    private Commands cmds = new Commands(robot, this);

    private ElapsedTime runtime = new ElapsedTime();

    private int TimeDebugSleep = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Testing Starting...");
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
        telemetry.addData("Config:", configs.TEST_TYPE + " Test");
        telemetry.addData("Config","Initialization Complete!");
        telemetry.update();

        waitForStart();

        switch (configs.TEST_TYPE)
        {
            case "DRIVE70INCHES":
                //Test drive for 70 inches
                cmds.EncoderDrive(configs.POWER_DRIVE, 70, 70, 5.0);
                break;
            case "DRIVE10SEC":
                //Test drive for 10 seconds to watch encoder differences
                //Encoders will be reset and set to RUN_USING_ENCODER above
                runtime.reset();

                robot.motorBackLeft.setPower(configs.POWER_DRIVE);
                robot.motorBackRight.setPower(configs.POWER_DRIVE);

                while ( opModeIsActive() && runtime.seconds() < 10)
                {
                    telemetry.addData("EncoderDrive","Diff: %7d", robot.motorBackRight.getCurrentPosition() - robot.motorBackLeft.getCurrentPosition());
                    telemetry.update();

                    idle();
                }
                robot.motorBackLeft.setPower(0);
                robot.motorBackRight.setPower(0);

                telemetry.addData("EncoderDrive", "> Left = %7d Right = :%7d", robot.motorBackLeft.getCurrentPosition(), robot.motorBackRight.getCurrentPosition());
                telemetry.update();
                sleep(3000);
                break;
            case "PUSHER":
                //Test push servo
                cmds.ExtendPusher();
                sleep(1500);
                cmds.RetractPusher();
                sleep(1500);
                break;
            case "SENSE":
                //Test sense beacon
                cmds.SenseBeacon();
                break;
            case "SHOOT":
                robot.motorLaunch.setPower(configs.POWER_LAUNCH);
                sleep(2000);
                robot.motorCollect.setPower(1.0);
                cmds.Shoot();   //robot);
                robot.motorCollect.setPower(0);
                break;
        }

        //***********************************
        cmds.StopDriving();

        //sleep(TimeDebugSleep);

        telemetry.addData("Status","Testing Complete!");
        telemetry.update();
    }
}
