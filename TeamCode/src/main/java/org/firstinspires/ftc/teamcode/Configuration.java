package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
PURPOSE:
    Sets variables with constant values.  Values here are overriden by configproperties.txt (on phone).
    All variables read with getProperies MUST be set in configproperties.
*/

public class Configuration
{
    private Telemetry telemetry;
    private Properties properties = new Properties();

    //Defaults are being set within the configuration to ensure that if the configs fail to load,
    //settings will still take place.  The configproperties.txt will override these defaults.

    public static String ALLIANCE = "RED";
    public static String START_POSITION = "SHORT";

    //*****************************************
    //Phone configurable settings:
    //*****************************************
    //Motor Power
        public static double DRIVE_POWER = 0.6;
        public static double TURN_POWER = 0.5;
        public static double LAUNCH_POWER = 0.55;
        public static double APPROACH_SPEED = 0.25;

    //Servo Positions
        public static double CLOSED_LIFT_SERVO_POS = 1.0;
        public static double OPEN_LIFT_SERVO_POS = 0.4;

        public static double CLOSED_TUSK_SERVO_POS = 1.0;
        public static double OPEN_TUSK_SERVO_POS = 0.4;

    //Time Settings
        public static int AUTO_DELAY_TIME = 0;
        public static int LAUNCH_TIME = 5000;

    //Measurement Settings
        public static double LONG_DIST_TO_SHOOT =10.0;          //Inches to drive from LONG start position before shooting ball
        public static double SHORT_DIST_TO_SHOOT =0.0;          //Inches to drive from SHORT start position before shooting ball
        public static double LONG_DIST_TO_PARK = 72.0;          //Inches to drive from after shooting when starting in LONG position
        public static double SHORT_DIST_TO_PARK =60.0;          //Inches to drive from after shooting when starting in SHORT position
        public static double LONG_FIRST_BEACON_AIM_DIST =66.0;  //Inches from LONG shooter position to beacon turn
        public static double SHORT_FIRST_BEACON_AIM_DIST =60.0; //Inches from SHORT shooter position to beacon turn
        public static double LONG_FIRST_BEACON_DIST = 40.0;      //Inches from LONG turn position to beacon
        public static double SHORT_FIRST_BEACON_DIST =40.0;     //Inches from SHORT turn position to beacon

    //*****************************************
    //Variables below are not available to be configured from phone (except for COUNTS_PER_INCH)
    //*****************************************
        public static double FORTYFIVE_DEGREE_TURN_INCHES = 10.0;
        public static double NINETY_DEGREE_TURN_INCHES = 20.0;

        //COUNTS_PER_INCH is calculated here with a default, but is is being overridden by a phone setting.
        private static double COUNTS_PER_MOTOR_REV = 1120;
        private static double DRIVE_GEAR_REDUCTION = 0.39;
        private static double WHEEL_DIAMETER_INCHES = 4.0;
        public static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

        public static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
        public static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
        public static final double     P_DRIVE_COEFF           = 0.15;    // Larger is more responsive, but also less stable

    /* Constructor */
    public Configuration(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    public void loadParameters()
    {
        telemetry.addData("Configuration", "Begin Loading Parameters...");
        telemetry.update();

        try
        {
            //* Keep for troubleshooting
            //System.out.println(System.getenv());
            //System.out.println(Environment.getExternalStorageDirectory());

            // ** Requires configproperties.xt to reside in root of Phone/FIRST directory (next to 9019.xml file) **
            FileInputStream in = new FileInputStream("/storage/emulated/0/FIRST/configproperties.txt");

            properties.load(in);

            ALLIANCE = properties.getProperty("ALLIANCE");
            START_POSITION = properties.getProperty("START_POSITION");

            DRIVE_POWER = Double.parseDouble(properties.getProperty("DRIVE_POWER"));
            TURN_POWER = Double.parseDouble(properties.getProperty("TURN_POWER"));
            LAUNCH_POWER = Double.parseDouble(properties.getProperty("LAUNCH_POWER"));
            APPROACH_SPEED = Double.parseDouble(properties.getProperty("APPROACH_SPEED"));

            CLOSED_LIFT_SERVO_POS = Double.parseDouble(properties.getProperty("CLOSED_LIFT_SERVO_POS"));
            OPEN_LIFT_SERVO_POS = Double.parseDouble(properties.getProperty("OPEN_LIFT_SERVO_POS"));

            CLOSED_TUSK_SERVO_POS = Double.parseDouble(properties.getProperty("CLOSED_TUSK_SERVO_POS"));
            OPEN_TUSK_SERVO_POS = Double.parseDouble(properties.getProperty("OPEN_TUSK_SERVO_POS"));

            AUTO_DELAY_TIME = Integer.parseInt(properties.getProperty("AUTO_DELAY_TIME"));
            LAUNCH_TIME = Integer.parseInt(properties.getProperty("LAUNCH_TIME"));

            LONG_DIST_TO_SHOOT = Double.parseDouble(properties.getProperty("LONG_DIST_TO_SHOOT"));
            SHORT_DIST_TO_SHOOT = Double.parseDouble(properties.getProperty("SHORT_DIST_TO_SHOOT"));
            LONG_DIST_TO_PARK = Double.parseDouble(properties.getProperty("LONG_DIST_TO_PARK"));
            SHORT_DIST_TO_PARK = Double.parseDouble(properties.getProperty("SHORT_DIST_TO_PARK"));

            LONG_FIRST_BEACON_AIM_DIST = Double.parseDouble(properties.getProperty("LONG_FIRST_BEACON_AIM_DIST"));
            SHORT_FIRST_BEACON_AIM_DIST = Double.parseDouble(properties.getProperty("SHORT_FIRST_BEACON_AIM_DIST"));
            LONG_FIRST_BEACON_DIST = Double.parseDouble(properties.getProperty("LONG_FIRST_BEACON_DIST"));
            SHORT_FIRST_BEACON_DIST = Double.parseDouble(properties.getProperty("SHORT_FIRST_BEACON_DIST"));

            COUNTS_PER_INCH = Double.parseDouble(properties.getProperty("COUNTS_PER_INCH"));

            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        telemetry.addData("Configuration", "Loading Parameters Complete!");
        telemetry.update();
    }
}