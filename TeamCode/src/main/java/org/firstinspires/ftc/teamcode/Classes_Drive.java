package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Classes_Drive
{
    /* Public OpMode members. */
    public DcMotorEx leftFront = null;
    public DcMotorEx rightFront = null;
    public DcMotorEx leftBack = null;
    public DcMotorEx rightBack = null;

    public double x = 0, y = 0, z = 0;


    public Classes_Drive(HardwareMap hwMap){
        // Define and Initialize Motors
        leftFront  = hwMap.get(DcMotorEx.class, "lf");
        rightFront = hwMap.get(DcMotorEx.class, "rf");
        leftBack  = hwMap.get(DcMotorEx.class, "lb");
        rightBack = hwMap.get(DcMotorEx.class, "rb");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

   // public void init(HardwareMap ahwMap) {}

    public void set_motor_powers(double x, double y, double z, double speed){
        rightFront.setPower((-y - x - z) * speed);
        leftFront.setPower((-y + x + z) * speed);
        leftBack.setPower((-y - x + z) * speed);
        rightBack.setPower((-y + x - z) * speed);
    }
}
