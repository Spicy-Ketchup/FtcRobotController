package org.firstinspires.ftc.teamcode;
/*
 * Some declarations that are boilerplate are
 * skipped for the sake of brevity.
 * Since there are no real values to use, named constants will be used.
 */

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Comp 1 TeleOp")
//@Disabled

public class Comp1Tele extends LinearOpMode {


    hwmap robot = new hwmap();

    ElapsedTime toggleTimer = new ElapsedTime();
    public double slowMode = .3;
    public double fastMode = 1;
    public int lowLift = 0;
    public int mediumLift = 700;
    public int highLift = 1200;
    public int tiltUp = 0;
    public int tiltDown = -300;
    public double elbowDown = 0;
    public double elbowUp = 500;
    public double clawOpen = 0;

 @Override
    public void runOpMode() throws InterruptedException {

 }

    }
