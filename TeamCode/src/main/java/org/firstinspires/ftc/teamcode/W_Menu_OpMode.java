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

    @Override
    public void init()
    {
        //read values from file

        //Is the context needed?  "this" represents the OpMode
        //menu.ReadFile(hardwareMap.appContext,this);
        menu.ReadFile();//this);

        runtime.reset();

        //Display file values to the driver
        while (runtime.seconds() < 2)
        {
            telemetry.addData("Read from File:", menu.param.colorAlliance + " Alliance");
            telemetry.addData("Read from File:", menu.param.autonType + " Starting Position");
            telemetry.addData("Read from File:", Integer.toString(menu.param.delayInSec / 1000 ) + " Sec. Delay");
            telemetry.update();
        }
    }

    @Override
    public void init_loop()
    {
        //Collect new settings from driver
        //menu.CollectNew(hardwareMap.appContext,this);
        menu.CollectNew(); //this);
    }

    @Override
    public void loop()
    {
        //Show menu settings to driver
        telemetry.addData("Set on Menu:", menu.param.colorAlliance + " Alliance");
        telemetry.addData("Set on Menu:", menu.param.autonType + " Starting Position");
        telemetry.addData("Set on Menu:", Integer.toString(menu.param.delayInSec / 1000) + " Sec. Delay");
        telemetry.update();
    }
}
