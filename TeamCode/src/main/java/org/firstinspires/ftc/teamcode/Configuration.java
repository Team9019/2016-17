package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
PURPOSE:
    Values are assigned by configproperties.txt (on robot phone).
    All variables read with getProperies MUST be set in configproperties.

#ALLIANCE:              RED or BLUE
#START_POSITION:        LONG or SHORT
#AUTO_DELAY_TIME:       Motor-time:  Time to delay in autonomous to prevent penalty if crossing line
#DRIVE_POWER:           Motor-power:  Drive speed
#TURN_POWER:            Motor-power:  Turn power
#LAUNCH_POWER:          Motor-power:  Ball launch power
#APPROACH_SPEED:        Motor-power:  Tank power to approach beacon
#CLOSED_LIFT_SERVO_POS  Servo: Closed position
#OPEN_LIFT_SERVO_POS    Servo:  Open (drop) position
#CLOSED_TUSK_SERVO_POS  Servo:  Closed position
#OPEN_TUSK_SERVO_POS    Servo:  Open (drop)position
#LAUNCH_TIME            Motor-time:  Time to power motor to launch ball
#LONG_DIST_TO_SHOOT     Distance:   Inches to drive from LONG start position before shooting ball
#LONG_DIST_TO_PARK      Distance:   Inches to drive from after shooting when starting in LONG position
#SHORT_DIST_TO_SHOOT    Distance:   Inches to drive from SHORT start position before shooting ball
#SHORT_DIST_TO_PARK     Distance:   Inches to drive from after shooting when starting in SHORT position
#LONG_FIRST_BEACON_AIM_DIST Distance:   Inches from LONG shooter position to beacon turn
#LONG_FIRST_BEACON_DIST     Distance:   Inches from LONG turn position to beacon
#SHORT FIRST_BEACON_AIM_DIST Distance:   Inches from SHORT shooter position to beacon turn
#SHORT_FIRST_BEACON_DIST    Distance:   Inches from SHORT turn position to beacon
#COUNTS_PER_INCH        Measure:  Counts per inch
#RED_COLOR_LOW          Color:  Red range low
#RED_COLOR_HIGH         Color:  Red range high
#BLUE_COLOR_LOW         Color:  Blue range low
#BLUE_COLOR_HIGH        Color:  Blue range high
*/

public class Configuration
{
    //Define local variables for parameters passed in
    private Telemetry telemetry;

    //Define variables local to the class
    private Properties properties = new Properties();
    private Properties propertiesmenu = new Properties();

    //*****************************************
    //Phone configurable settings:
    //*****************************************
    //Match settings
        public static String ALLIANCE;
        public static String START_POSITION;
        public static int AUTO_DELAY;

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
        public static double POS_IN_PUSHER_SERVO;
        public static double POS_OUT_PUSHER_SERVO;

    //Time Settings
        public static int TIME_LAUNCH;

    //Measurement Settings
        public static int DIST_CORNER_TO_SHOOT;          //Inches to drive from LONG start position before shooting ball
        public static int DIST_SIDE_TO_SHOOT;          //Inches to drive from SHORT start position before shooting ball
        public static int DIST_CORNER_TO_PARK;          //Inches to drive from after shooting when starting in LONG position
        public static int DIST_SIDE_TO_PARK;          //Inches to drive from after shooting when starting in SHORT position
        public static int DIST_LONG_BEACON1_AIM;  //Inches from LONG shooter position to beacon turn
        public static int DIST_SHORT_BEACON1_AIM;       //Inches from SHORT shooter position to beacon turn
        public static int DIST_LONG_FIRST_BEACON;      //Inches from LONG turn position to beacon
        public static int DIST_SHORT_FIRST_BEACON;     //Inches from SHORT turn position to beacon

        public static int DIST_SHORT_TO_WALL;
        public static int DIST_RAMP_TO_BEACON_1;
        public static int DIST_BEACON1_TO_BEACON_2;
        public static int DIST_BEACON2_TO_CORNER;
        //public static int W_DIST_SHORT_TO_CENTER;

        public static double INCHES_NINETY_DEGREE_TURN;

        public static int COLOR_RED_LOW;
        public static int COLOR_RED_HIGH;
        public static int COLOR_BLUE_LOW;
        public static int COLOR_BLUE_HIGH;

    //*****************************************
    //Variables below are not available to be configured from phone (except for COUNTS_PER_INCH)
    //*****************************************
        public static double INCHES_FORTYFIVE_DEGREE_TURN;

        //COUNTS_PER_INCH is calculated here with a default, but is is being overridden by a phone setting.
        //private static double COUNTS_PER_MOTOR_REV = 1120;
        //private static double DRIVE_GEAR_REDUCTION = 0.39;
        //private static double WHEEL_DIAMETER_INCHES = 4.0;
        public static double COUNTS_PER_INCH;   // = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

        //public static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
        //public static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
        //public static final double     P_DRIVE_COEFF           = 0.15;    // Larger is more responsive, but also less stable

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

            // ** Requires configproperties.Txt to reside in root of Phone/FIRST directory (next to 9019.xml file) **

            //Read menu file
            FileInputStream inmenu = new FileInputStream("/storage/emulated/0/FIRST/configpropertiesmenu.txt");
            propertiesmenu.load(inmenu);

            ALLIANCE = propertiesmenu.getProperty("ALLIANCE");
            START_POSITION = propertiesmenu.getProperty("START_POSITION");
            AUTO_DELAY = Integer.parseInt(propertiesmenu.getProperty("AUTO_DELAY")) ;

            //Read configuration file
            FileInputStream in = new FileInputStream("/storage/emulated/0/FIRST/configproperties.txt");
            properties.load(in);

            POWER_DRIVE = Double.parseDouble(properties.getProperty("POWER_DRIVE"));
            POWER_TURN = Double.parseDouble(properties.getProperty("POWER_TURN"));
            POWER_LAUNCH = Double.parseDouble(properties.getProperty("POWER_LAUNCH"));
            POWER_APPROACH = Double.parseDouble(properties.getProperty("POWER_APPROACH"));

            POS_CLOSED_LIFT_SERVO = Double.parseDouble(properties.getProperty("POS_CLOSED_LIFT_SERVO"));
            POS_OPEN_LIFT_SERVO = Double.parseDouble(properties.getProperty("POS_OPEN_LIFT_SERVO"));
            POS_CLOSED_TUSK_SERVO = Double.parseDouble(properties.getProperty("POS_CLOSED_TUSK_SERVO"));
            POS_OPEN_TUSK_SERVO = Double.parseDouble(properties.getProperty("POS_OPEN_TUSK_SERVO"));
            POS_IN_PUSHER_SERVO = Double.parseDouble(properties.getProperty("POS_IN_PUSHER_SERVO"));
            POS_OUT_PUSHER_SERVO = Double.parseDouble(properties.getProperty("POS_OUT_PUSHER_SERVO"));

            TIME_LAUNCH = Integer.parseInt(properties.getProperty("TIME_LAUNCH")) * 1000;

            DIST_CORNER_TO_SHOOT = Integer.parseInt(properties.getProperty("DIST_CORNER_TO_SHOOT"));
            DIST_SIDE_TO_SHOOT = Integer.parseInt(properties.getProperty("DIST_SIDE_TO_SHOOT"));
            DIST_CORNER_TO_PARK = Integer.parseInt(properties.getProperty("DIST_CORNER_TO_PARK"));
            DIST_SIDE_TO_PARK = Integer.parseInt(properties.getProperty("DIST_SIDE_TO_PARK"));

            DIST_LONG_BEACON1_AIM = Integer.parseInt(properties.getProperty("DIST_LONG_BEACON1_AIM"));
            DIST_SHORT_BEACON1_AIM = Integer.parseInt(properties.getProperty("DIST_SHORT_BEACON1_AIM"));
            DIST_LONG_FIRST_BEACON = Integer.parseInt(properties.getProperty("DIST_LONG_FIRST_BEACON"));
            DIST_SHORT_FIRST_BEACON = Integer.parseInt(properties.getProperty("DIST_SHORT_FIRST_BEACON"));

            DIST_SHORT_TO_WALL = Integer.parseInt(properties.getProperty("DIST_SHORT_TO_WALL"));
            DIST_RAMP_TO_BEACON_1 = Integer.parseInt(properties.getProperty("DIST_RAMP_TO_BEACON_1"));
            DIST_BEACON1_TO_BEACON_2 = Integer.parseInt(properties.getProperty("DIST_BEACON1_TO_BEACON_2"));
            DIST_BEACON2_TO_CORNER = Integer.parseInt(properties.getProperty("DIST_BEACON2_TO_CORNER"));
            //W_DIST_SHORT_TO_CENTER = Integer.parseInt(properties.getProperty("W_DIST_SHORT_TO_CENTER"));

            COUNTS_PER_INCH = Double.parseDouble(properties.getProperty("COUNTS_PER_INCH"));
            INCHES_NINETY_DEGREE_TURN = Double.parseDouble(properties.getProperty("INCHES_NINETY_DEGREE_TURN"));
            INCHES_FORTYFIVE_DEGREE_TURN = INCHES_NINETY_DEGREE_TURN / 2;

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