package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import org.firstinspires.ftc.robotcore.external.Telemetry;
/*
PURPOSE:
    Tie the hardware variables to the hardware defined in the FTC Robot Controller app on the phone.
*/

public class Hardware
{
    //Define local variables for parameters passed in
    private Telemetry telemetry;
    private HardwareMap hwMap = null;

    //Establish sub-classes with Constructor call
    //private Configuration configs = new Configuration(telemetry);

    //Define variables local to the class
    //Motors
        //DcMotor motorFrontLeft=null;
        //DcMotor motorFrontRight=null;
        DcMotor motorBackLeft=null;
        DcMotor motorBackRight=null;

        DcMotor motorLaunch=null;

        DcMotor motorCollect=null;

        DcMotor motorLiftLeft=null;
        DcMotor motorLiftRight=null;

    //Servos
        Servo servoLift=null;
        Servo servoTusk=null;
        Servo servoPusher=null;

    //Sensors
        ModernRoboticsI2cGyro sensorGyro=null;
        ModernRoboticsI2cColorSensor sensorColor=null;

    //Device
        DeviceInterfaceModule devIM = null;

    /* Constructor */
    public Hardware(Telemetry telemetry) //, HardwareMap ahwMap)
    {
        this.telemetry = telemetry;
        //this.hwMap = ahwMap;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        telemetry.addData("Hardware", "Begin Hardware Definition...");
        telemetry.update();

        hwMap = ahwMap;

        // Define Motors
            //motorFrontLeft = hwMap.dcMotor.get("front_left");
            //motorFrontRight = hwMap.dcMotor.get("front_right");
            motorBackLeft = hwMap.dcMotor.get("back_left");
            motorBackRight = hwMap.dcMotor.get("back_right");

            motorLaunch = hwMap.dcMotor.get("shoot");

            motorCollect = hwMap.dcMotor.get("collect");

            motorLiftLeft = hwMap.dcMotor.get("lift_left");
            motorLiftRight = hwMap.dcMotor.get("lift_right");

        // Define Servos
            servoLift = hwMap.servo.get("fork");
            servoTusk = hwMap.servo.get("tusk");
            servoPusher = hwMap.servo.get("pusher");

        // Define Sensors
            sensorGyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
            sensorColor = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");
            //sensorColor.setI2cAddress(I2cAddr.create7bit(0x3c));

        // Define Devices
            devIM = (DeviceInterfaceModule) hwMap.deviceInterfaceModule.get("Device Interface Module 1");

        telemetry.addData("Hardware", "Hardware Definition Complete!");
        telemetry.update();
    }

    public void SetDefaults(HardwareMap ahwMap) // Configuration configs)   //HardwareMap ahwMap)
    {
        hwMap = ahwMap;

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

        //motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorLaunch.setDirection(DcMotor.Direction.REVERSE);

        //motorFrontLeft.setPower(0);
        //motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);

        motorLaunch.setPower(0);
        motorLiftLeft.setPower(0);
        motorLiftRight.setPower(0);
        motorCollect.setPower(0);

        telemetry.addData("InitializeHW", "> Initializing Motors Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Encoders
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Resetting Encoders...");
        telemetry.update();

        //motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //idle();

        //motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //motorFrontLeft.setMaxSpeed(3000);
        //motorFrontRight.setMaxSpeed(3000);
        motorBackLeft.setMaxSpeed(3000);
        motorBackRight.setMaxSpeed(3000);

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

        servoPusher.setPosition(Configuration.POS_IN_PUSHER_SERVO);
        servoLift.setPosition(Configuration.POS_CLOSED_LIFT_SERVO);
        servoTusk.setPosition(Configuration.POS_CLOSED_TUSK_SERVO);

        telemetry.addData("InitializeHW", "> Initializing Servo Positions Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Sensors
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Sensors ...");
        telemetry.update();

        telemetry.addData("InitializeHW", "> > Initializing Color Sensors ...");
        telemetry.update();

        sensorColor.enableLed(false);

        telemetry.addData("InitializeHW", "> > Initializing Color Sensors Complete!");
        telemetry.update();

        //telemetry.addData("InitializeHW", "> > Initializing Gyro ...");
        //telemetry.update();

        //int xVal, yVal, zVal = 0;     // Gyro rate Values
        //int heading = 0;              // Gyro integrated heading
        //int angleZ = 0;
        //boolean lastResetState = false;
        //boolean curResetState  = false;

        // hsvValues is an array that will hold the hue, saturation, and value information.
        //float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        //final float values[] = hsvValues;

        //robot.sensorGyro.calibrate();

        // make sure the gyro is calibrated.
        //while (!isStopRequested() && robot.sensorGyro.isCalibrating())
        //{
        //    sleep(50);
        //    idle();
        //}

        //robot.sensorGyro.resetZAxisIntegrator();    //03-01-2017

        //telemetry.addData("InitializeHW", "> > Initializing Gyro Complete!");
        //telemetry.update();

        telemetry.addData("InitializeHW", "> Initializing Sensors Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Device Modules
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Device Modules ...");
        telemetry.update();

        devIM.setLED(1,false);    //RED
        devIM.setLED(0,false);    //BLUE

        telemetry.addData("InitializeHW", "> Initializing Device Modules Complete!");
        telemetry.update();


        telemetry.addData("InitializeHW", "Initialization HW Complete!");
        telemetry.update();
    }
}