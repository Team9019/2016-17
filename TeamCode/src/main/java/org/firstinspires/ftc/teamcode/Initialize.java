package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Define initialization.
    (Separated from commands to allow OpMode to be passed to Commands class.)
*/

public class Initialize extends LinearOpMode
{
    //private ElapsedTime runtime = new ElapsedTime();
    //LinearOpMode opMode;

    /* Constructor */
    public Initialize(Telemetry telemetry) //, LinearOpMode opMode)
    {
        this.telemetry = telemetry;
        //this.opMode = opMode;
    }

    public void runOpMode()
    {
    }

    public void InitializeHW(Hardware robot)
    {
        //runtime.reset();

        telemetry.addData("InitializeHW", "Beginning HW Initialization...");
        telemetry.update();

        /* ******************************************************/
        // Initialize motors to off
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Motors...");
        telemetry.update();

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        robot.motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        robot.motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        robot.motorLaunch.setDirection(DcMotor.Direction.REVERSE);

        robot.motorFrontLeft.setPower(0);
        robot.motorFrontRight.setPower(0);
        robot.motorBackLeft.setPower(0);
        robot.motorBackRight.setPower(0);

        robot.motorLaunch.setPower(0);
        robot.motorLiftLeft.setPower(0);
        robot.motorLiftRight.setPower(0);
        robot.motorCollect.setPower(0);

        telemetry.addData("InitializeHW", "> Initializing Motors Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Encoders
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Resetting Encoders...");
        telemetry.update();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.motorFrontLeft.setMaxSpeed(1000);
        robot.motorFrontRight.setMaxSpeed(1000);
        robot.motorBackLeft.setMaxSpeed(1000);
        robot.motorBackRight.setMaxSpeed(1000);

        //telemetry.addData
        //        ("InitializeHW", "> > Starting at %7d :%7d :%7d :%7d",
        //                robot.motorFrontLeft.getCurrentPosition(),
        //                robot.motorFrontRight.getCurrentPosition(),
        //                robot.motorBackLeft.getCurrentPosition(),
        //                robot.motorBackRight.getCurrentPosition()
        //        );
        //telemetry.update();

        telemetry.addData("InitializeHW", "> Resetting Encoders Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Servos
        /* ******************************************************/
        telemetry.addData("Initialize Servos", "> Initializing Servo Positions...");
        telemetry.update();

        robot.servoLift.setPosition(Configuration.CLOSED_LIFT_SERVO_POS);
        robot.servoTusk.setPosition(Configuration.CLOSED_TUSK_SERVO_POS);

        //telemetry.addData("InitializeHW", "> > Lift servo position: " + robot.servoLift.getPosition());

        telemetry.addData("InitializeHW", "> Initializing Servo Positions Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Sensors
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Sensors ...");
        telemetry.update();

        telemetry.addData("InitializeHW", "> > Initializing Color Sensors ...");
        telemetry.update();

        robot.sensorColor.enableLed(false);

        telemetry.addData("InitializeHW", "> > Initializing Color Sensors Complete!");
        telemetry.update();

        telemetry.addData("InitializeHW", "> > Initializing Gyro ...");
        telemetry.update();

        //int xVal, yVal, zVal = 0;     // Gyro rate Values
        //int heading = 0;              // Gyro integrated heading
        //int angleZ = 0;
        //boolean lastResetState = false;
        //boolean curResetState  = false;

        // hsvValues is an array that will hold the hue, saturation, and value information.
        //float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        //final float values[] = hsvValues;

        robot.sensorGyro.calibrate();

        // make sure the gyro is calibrated.
        while (!isStopRequested() && robot.sensorGyro.isCalibrating())
        {
            sleep(50);
            idle();
        }

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.sensorGyro.resetZAxisIntegrator();    //03-01-2017

        telemetry.addData("InitializeHW", "> > Initializing Gyro Complete!");
        telemetry.update();

        telemetry.addData("InitializeHW", "> Initializing Sensors Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Device Modules
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Device Modules ...");
        telemetry.update();

        robot.devIM.setLED(1,false);    //RED
        robot.devIM.setLED(0,false);    //BLUE

        telemetry.addData("InitializeHW", "> Initializing Device Modules Complete!");
        telemetry.update();


        telemetry.addData("InitializeHW", "Initialization HW Complete!");
        telemetry.update();
    }

}
