package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
FUNCTION:
    TeleOp
*/

@TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOpIterative extends OpMode
{
    //Establish sub-classes with Constructor call
    private Configuration configs = new Configuration(telemetry);
    private Hardware robot = new Hardware(telemetry);   //, hardwareMap);
    //private Initialize init = new Initialize(telemetry);
    //private Commands cmds = new Commands(robot, this);

    private final ElapsedTime runtime = new ElapsedTime();

    private double LeftFY;
    private double LeftBY;
    private double RightFY;
    private double RightBY;

    private double LiftY;
    private double LaunchY;

    private double Collect;
//    private double CollectUT;

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init()
    {
        telemetry.addData("Initialization","Starting TeleOp Initialization ...");
        telemetry.update();

        configs.loadParameters();

        robot.init(hardwareMap);

        robot.SetDefaults(hardwareMap, configs);    //hardwareMap);

        //init.InitializeHW(robot);

        //put this here to ensure encoders are disabled for teleop
        //robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Initialization", "TeleOp Initialization Complete!");
        telemetry.update();
    }

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop()
    {
    }

    /* Code to run ONCE when the driver hits PLAY */
    @Override
    public void start()
    {
        telemetry.addData("Run Mode", "Starting Run Mode ...");
        telemetry.update();

        runtime.reset();
    }

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop()
    {
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.update();

        //Give buttons a variable name
            LeftFY = -gamepad1.left_stick_y;
            LeftBY = -gamepad1.left_stick_y;
            RightFY = -gamepad1.right_stick_y;
            RightBY = -gamepad1.right_stick_y;

            Collect = gamepad1.right_trigger -gamepad1.left_trigger;

            LaunchY = gamepad2.right_stick_y;
            LiftY = gamepad2.left_stick_y;

        //Apply button settings to robot
            //robot.motorFrontLeft.setPower(LeftFY);
            robot.motorBackLeft.setPower(LeftBY);
            //robot.motorFrontRight.setPower(RightFY);
            robot.motorBackRight.setPower(RightBY);

            robot.motorCollect.setPower(Collect);

            //robot.motorLaunch.setPower(LaunchY);

            robot.motorLiftLeft.setPower(LiftY);
            robot.motorLiftRight.setPower(LiftY);


        //Main driver - servo controls
        if (gamepad1.right_bumper)
        {
            robot.servoLift.setPosition(configs.POS_OPEN_LIFT_SERVO);
            robot.servoTusk.setPosition(configs.POS_OPEN_TUSK_SERVO);
        }
        else
        {
            robot.servoLift.setPosition(configs.POS_CLOSED_LIFT_SERVO);
            robot.servoTusk.setPosition(configs.POS_CLOSED_TUSK_SERVO);
        }

        //Alternate driver
        if (gamepad2.b)
        {
            robot.sensorColor.enableLed(true);
        }
        else
        {
            robot.sensorColor.enableLed(false);
        }

        if (gamepad2.right_bumper)
        {
            robot.motorLaunch.setPower(configs.POWER_LAUNCH);
        }
        else
        {
            robot.motorLaunch.setPower(0);
        }

        //Alternate driver - servo controls
//        if (gamepad2.x)
//        {
//            cmds.ReadyBeaconArm(robot);
//        }

        // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
        //cmds.waitForTick(40);
    }

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop()
    {
        telemetry.addData("Run Mode", "Run Mode Complete!");
        telemetry.update();
    }
}
