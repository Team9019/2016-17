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
        public static double POWER_DRIVE;
        public static double POWER_TURN;
        public static double POWER_LAUNCH;
        public static double POWER_APPROACH;

    //Servo Positions
        public static double POS_CLOSED_LIFT_SERVO;
        public static double POS_OPEN_LIFT_SERVO;

        public static double POS_CLOSED_TUSK_SERVO;
        public static double POS_OPEN_TUSK_SERVO;

    //Time Settings
        public static int TIME_AUTO_DELAY;
        public static int TIME_LAUNCH;

    //Measurement Settings
        public static int DIST_LONG_TO_SHOOT;          //Inches to drive from LONG start position before shooting ball
        public static int DIST_SHORT_TO_SHOOT;          //Inches to drive from SHORT start position before shooting ball
        public static int DIST_LONG_TO_PARK;          //Inches to drive from after shooting when starting in LONG position
        public static int DIST_SHORT_TO_PARK;          //Inches to drive from after shooting when starting in SHORT position
        public static int DIST_LONG_FIRST_BEACON_AIM;  //Inches from LONG shooter position to beacon turn
        public static int DIST_SHORT_FIRST_BEACON_AIM; //Inches from SHORT shooter position to beacon turn
        public static int DIST_LONG_FIRST_BEACON;      //Inches from LONG turn position to beacon
        public static int DIST_SHORT_FIRST_BEACON;     //Inches from SHORT turn position to beacon

        public static int W_DIST_SHORT_TO_WALL;
        public static int W_DIST_SHORT_TO_BEACON_1;
        public static int W_DIST_SHORT_TO_BEACON_2;
        public static int W_DIST_SHORT_TO_CENTER;

        public static int INCHES_NINETY_DEGREE_TURN;

        public static int COLOR_RED_LOW = 3;
        public static int COLOR_RED_HIGH = 8;
        public static int COLOR_BLUE_LOW = 1;
        public static int COLOR_BLUE_HIGH = 2;

    //*****************************************
    //Variables below are not available to be configured from phone (except for COUNTS_PER_INCH)
    //*****************************************
        public static double INCHES_FORTYFIVE_DEGREE_TURN = INCHES_NINETY_DEGREE_TURN / 2;

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

            POWER_DRIVE = Double.parseDouble(properties.getProperty("POWER_DRIVE"));
            POWER_TURN = Double.parseDouble(properties.getProperty("POWER_TURN"));
            POWER_LAUNCH = Double.parseDouble(properties.getProperty("POWER_LAUNCH"));
            POWER_APPROACH = Double.parseDouble(properties.getProperty("POWER_APPROACH"));

            POS_CLOSED_LIFT_SERVO = Double.parseDouble(properties.getProperty("POS_CLOSED_LIFT_SERVO"));
            POS_OPEN_LIFT_SERVO = Double.parseDouble(properties.getProperty("POS_OPEN_LIFT_SERVO"));

            POS_CLOSED_TUSK_SERVO = Double.parseDouble(properties.getProperty("POS_CLOSED_TUSK_SERVO"));
            POS_OPEN_TUSK_SERVO = Double.parseDouble(properties.getProperty("POS_OPEN_TUSK_SERVO"));

            TIME_AUTO_DELAY = Integer.parseInt(properties.getProperty("TIME_AUTO_DELAY"));
            TIME_LAUNCH = Integer.parseInt(properties.getProperty("TIME_LAUNCH"));

            DIST_LONG_TO_SHOOT = Integer.parseInt(properties.getProperty("DIST_LONG_TO_SHOOT"));
            DIST_SHORT_TO_SHOOT = Integer.parseInt(properties.getProperty("DIST_SHORT_TO_SHOOT"));
            DIST_LONG_TO_PARK = Integer.parseInt(properties.getProperty("DIST_LONG_TO_PARK"));
            DIST_SHORT_TO_PARK = Integer.parseInt(properties.getProperty("DIST_SHORT_TO_PARK"));

            DIST_LONG_FIRST_BEACON_AIM = Integer.parseInt(properties.getProperty("DIST_LONG_FIRST_BEACON_AIM"));
            DIST_SHORT_FIRST_BEACON_AIM = Integer.parseInt(properties.getProperty("DIST_SHORT_FIRST_BEACON_AIM"));
            DIST_LONG_FIRST_BEACON = Integer.parseInt(properties.getProperty("DIST_LONG_FIRST_BEACON"));
            DIST_SHORT_FIRST_BEACON = Integer.parseInt(properties.getProperty("DIST_SHORT_FIRST_BEACON"));

            W_DIST_SHORT_TO_WALL = Integer.parseInt(properties.getProperty("W_DIST_SHORT_TO_WALL"));
            W_DIST_SHORT_TO_BEACON_1 = Integer.parseInt(properties.getProperty("W_DIST_SHORT_TO_BEACON_1"));
            W_DIST_SHORT_TO_BEACON_2 = Integer.parseInt(properties.getProperty("W_DIST_SHORT_TO_BEACON_2"));
            W_DIST_SHORT_TO_CENTER = Integer.parseInt(properties.getProperty("W_DIST_SHORT_TO_CENTER"));

            COUNTS_PER_INCH = Double.parseDouble(properties.getProperty("COUNTS_PER_INCH"));
            INCHES_NINETY_DEGREE_TURN = Integer.parseInt(properties.getProperty("INCHES_NINETY_DEGREE_TURN"));

            COLOR_RED_LOW = Integer.parseInt(properties.getProperty("COLOR_RED_LOW"));
            COLOR_RED_HIGH = Integer.parseInt(properties.getProperty("COLOR_RED_HIGH"));
            COLOR_BLUE_LOW = Integer.parseInt(properties.getProperty("COLOR_BLUE_LOW"));
            COLOR_BLUE_HIGH = Integer.parseInt(properties.getProperty("COLOR_BLUE_HIGH"));

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