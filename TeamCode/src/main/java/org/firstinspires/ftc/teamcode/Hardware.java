package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Tie the hardware variables to the hardware defined in the FTC Robot Controller app on the phone.
*/

public class Hardware
{
    private Telemetry telemetry;
    private HardwareMap hwMap = null;

    //Motors
        DcMotor motorFrontLeft=null;
        DcMotor motorBackLeft=null;
        DcMotor motorFrontRight=null;
        DcMotor motorBackRight=null;

        DcMotor motorLaunch=null;

        DcMotor motorCollect=null;

        DcMotor motorLiftLeft=null;
        DcMotor motorLiftRight=null;

    //Servos
        Servo servoLift=null;
        Servo servoTusk=null;

    //Sensors
        ModernRoboticsI2cGyro sensorGyro=null;
        ModernRoboticsI2cColorSensor sensorColor=null;

    //Device
        DeviceInterfaceModule devIM = null;

    /* Constructor */
    public Hardware(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        telemetry.addData("Hardware", "Begin Hardware Definition...");
        telemetry.update();

        hwMap = ahwMap;

        // Define Motors
            motorFrontLeft = hwMap.dcMotor.get("front_left");
            motorBackLeft = hwMap.dcMotor.get("back_left");
            motorFrontRight = hwMap.dcMotor.get("front_right");
            motorBackRight = hwMap.dcMotor.get("back_right");

            motorLaunch = hwMap.dcMotor.get("shoot");

            motorCollect = hwMap.dcMotor.get("collect");

            motorLiftLeft = hwMap.dcMotor.get("lift_left");
            motorLiftRight = hwMap.dcMotor.get("lift_right");

        // Define Servos
            servoLift = hwMap.servo.get("fork");
            //servoTusk = hwMap.servo.get("tusk");

        // Define Sensors
            sensorGyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
            sensorColor = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");
            //sensorColor.setI2cAddress(I2cAddr.create7bit(0x3c));

        // Define Devices
            devIM = (DeviceInterfaceModule) hwMap.deviceInterfaceModule.get("Device Interface Module 1");

        telemetry.addData("Hardware", "Hardware Definition Complete!");
        telemetry.update();
    }
}