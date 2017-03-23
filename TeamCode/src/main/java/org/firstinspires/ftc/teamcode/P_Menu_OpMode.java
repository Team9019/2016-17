package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/*
FUNCTION:
    TeleOp - Must be OpMode due to looping
*/

@Autonomous(name = "Config Menu", group = "Utilities")
public class P_Menu_OpMode extends OpMode
{
    private Configuration configs = new Configuration(telemetry);
    P_Menu_Commands menu =new P_Menu_Commands();

    @Override
    public void init()
    {
        menu.init(hardwareMap.appContext,this);

        configs.loadParameters();
    }

    @Override
    public void init_loop()
    {
        menu.init_loop(hardwareMap.appContext,this);
    }

    @Override
    public void loop()
    {
        telemetry.addData("File:", Configuration.ALLIANCE + " Alliance");
        telemetry.addData("File:", Configuration.START_POSITION + " Starting Position");
        telemetry.addData("File:", Configuration.AUTO_DELAY /1000 + " Sec. Delay");

        telemetry.addData("Menu:", menu.param.colorAlliance + "Alliance");
        telemetry.addData("Menu:", menu.param.autonType + "Starting Position");
        telemetry.addData("Menu:", Integer.toString(menu.param.delayInSec) + "Sec. Delay");

        telemetry.addData("Config","Overrides Complete!  PRESS STOP!!");
        telemetry.update();
    }
}
