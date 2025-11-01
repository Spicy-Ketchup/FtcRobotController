package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Classes_Shooter {
    private Telemetry telemetry;
    // TODO: Tune stuff here
    public static double SHOOTER_TPR = 28; //found at https://www.gobilda.com/5203-series-yellow-jacket-motor-1-1-ratio-24mm-length-8mm-rex-shaft-6000-rpm-3-3-5v-encoder/
    public static double SHOOTER_P = 0.00028;
    public static double SHOOTER_F = 0.000099;
    private PIDFController shooterWheelsPID;
    public DcMotorEx flyWheel = null;


    public Classes_Shooter(HardwareMap hwMap){
        flyWheel = hwMap.get(DcMotorEx.class, "fly1");
        flyWheel.setDirection(DcMotorEx.Direction.REVERSE);
        flyWheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        flyWheel.setPower(0);

        shooterWheelsPID = new PIDFController(SHOOTER_P, 0, 0, SHOOTER_F);

    }

    public void set_RPM(double targetRPM){
        if (targetRPM != 0) {
            shooterWheelsPID.setP(SHOOTER_P);
            shooterWheelsPID.setF(SHOOTER_F);

            double wheel1RPM = (60 * (flyWheel.getVelocity() / SHOOTER_TPR));
            double output = shooterWheelsPID.calculate(wheel1RPM, targetRPM);
            flyWheel.setPower(output);
        } else flyWheel.setPower(0);
    }

    public int get_wheel1_rpm() {
        return (int) (60 * (flyWheel.getVelocity() / SHOOTER_TPR));
    }

}

