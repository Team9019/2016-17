package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Config Menu", group = "Utilities")
//@Disabled
public class W_Menu_OpMode extends OpMode
{
    //Establish sub-classes with Constructor call
    Menu_Commands menu =new Menu_Commands(this);

    private ElapsedTime runtime = new ElapsedTime();

    private int TimeDebugSleep = 0;

    @Override
    public void init()
    {
        //read values from file
        menu.ReadFile();//this);

        runtime.reset();

        //Display file values to the driver
        while (runtime.seconds() < 2)
        {
            telemetry.addData("Read from File:", menu.param.colorAlliance + " Alliance");
            telemetry.addData("Read from File:", menu.param.autonType + " Starting Position");
            telemetry.addData("Read from File:", Integer.toString(menu.param.delayInSec) + " Sec. Delay");
            telemetry.addData("Read from File:", menu.param.testList + " Test");
            telemetry.update();
        }
    }

    @Override
    public void init_loop()
    {
        //Collect new settings from driver
        menu.CollectNew();
    }

    @Override
    public void loop()
    {
        //Show menu settings to driver
        telemetry.addData("Set on Menu:", menu.param.colorAlliance + " Alliance");
        telemetry.addData("Set on Menu:", menu.param.autonType + " Starting Position");
        telemetry.addData("Set on Menu:", Integer.toString(menu.param.delayInSec) + " Sec. Delay");
        telemetry.addData("Set on Menu:", menu.param.testList + " Test");
        telemetry.addData("DONE", "Press STOP to close.");
        telemetry.update();
    }
}
