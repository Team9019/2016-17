package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.NoSuchElementException;

public class P_HalDashboard
{
    private static final String moduleName = "HalDashboard";

    public static final int MAX_NUM_TEXTLINES = 3;

    private static final String displayKeyFormat = "%02d";
    private static Telemetry telemetry = null;
    private static P_HalDashboard instance = null;
    private static String[] display = new String[MAX_NUM_TEXTLINES];

    //Constructor//
    public P_HalDashboard(Telemetry telemetry)
    {
        instance = this;
        this.telemetry = telemetry;
        //telemetry.clearData();
        //telemetry.clear();
        telemetry.clearAll();
        clearDisplay();
    }   //HalDashboard

//    public static HalDashboard getInstance(Telemetry telemetry)
//    {
//        if (instance == null)
//        {
//            instance = new HalDashboard(telemetry);
//        }
//
//        return instance;
//    }   //getInstance

    public static P_HalDashboard getInstance()
    {
        return instance;
    }   //getInstance


    public void displayPrintf(int lineNum, String format, Object... args)
    {
        if (lineNum >= 0 && lineNum < display.length)
        {
            display[lineNum] = String.format(format, args);
            telemetry.addData(String.format(displayKeyFormat, lineNum), display[lineNum]);
        }
    }   //displayPrintf

    public void clearDisplay()
    {
        for (int i = 0; i < display.length; i++)
        {
            display[i] = "";
        }
        refreshDisplay();
    }   //clearDisplay

    public void refreshDisplay()
    {
        for (int i = 0; i < display.length; i++)
        {
            telemetry.addData(String.format(displayKeyFormat, i), display[i]);
        }
    }   //refreshDisplay

    public double getNumber(String key)
    {
        double value;

        try
        {
            value = Double.parseDouble(getValue(key));
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("object is not a number");
        }

        return value;
    }   //getNumber

    public double getNumber(String key, double defaultValue)
    {
        double value;

        try
        {
            value = getNumber(key);
        }
        catch (NoSuchElementException e)
        {
            putNumber(key, defaultValue);
            value = defaultValue;
        }

        return value;
    }   //getNumber

    public void putNumber(String key, double value)
    {
        telemetry.addData(key, Double.toString(value));
    }   //putNumber

    public String getString(String key)
    {
        String value = getValue(key);

        return value;
    }   //getString

    public String getString(String key, String defaultValue)
    {
        String value;

        try
        {
            value = getString(key);
        }
        catch (NoSuchElementException e)
        {
            putString(key, defaultValue);
            value = defaultValue;
        }

        return value;
    }   //getString

    public void putString(String key, String value)
    {
        telemetry.addData(key, value);
    }   //putString

    private String getValue(String key)
    {
        //String value = telemetry.getDataStrings().get(key);
        String value = key;

        if (value == null)
        {
            throw new NoSuchElementException("No such key");
        }

        return value;
    }   //getValue

}   //class HalDashboard