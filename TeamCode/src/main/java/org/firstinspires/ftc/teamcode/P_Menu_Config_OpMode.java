package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/*
FUNCTION:
    TeleOp
*/

@Autonomous(name = "Config Menu", group = "Utilities")
public class P_Menu_Config_OpMode extends OpMode
{
    P_Menu_Commands menu =new P_Menu_Commands();

    @Override
    public void init()
    {
        menu.init(hardwareMap.appContext,this);
    }

    @Override
    public void init_loop()
    {
        menu.init_loop(hardwareMap.appContext,this);
    }

    @Override
    public void loop()
    {
        //telemetry.clearData();
        //telemetry.clearAll();

        telemetry.addData("Alliance", menu.param.colorAlliance);
        telemetry.addData("AutonType", menu.param.autonType);
        telemetry.addData("DelayInSec", Integer.toString(menu.param.delayInSec));
    }
}
