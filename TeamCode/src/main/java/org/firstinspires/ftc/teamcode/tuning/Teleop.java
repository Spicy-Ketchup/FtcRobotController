package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Classes_Drive;
import org.firstinspires.ftc.teamcode.Classes_Shooter;

@Config
@TeleOp(name="The Teleop")
//@Disabled
public class Teleop extends LinearOpMode {

    public Servo kicker = null;

    enum Outtake {
        Rest,
        Shoot
    }

    public static int target_RPM;

    @Override
    public void runOpMode() throws InterruptedException {

        kicker  = hardwareMap.get(Servo.class, "kicker");

        Classes_Drive drive = new Classes_Drive(hardwareMap);
        double x = 0, y = 0, z = 0, speed = 1;
        target_RPM = 0;
        Classes_Shooter shooter = new Classes_Shooter(hardwareMap);
        Outtake outtake = Outtake.Rest;
        ElapsedTime Dex_Time  = new ElapsedTime();
        ElapsedTime Shoot_Time  = new ElapsedTime();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            // <editor-fold desc="Drive">
            x = gamepad1.left_stick_x;
            y = gamepad1.left_stick_y;
            z = gamepad1.right_stick_x;
                drive.set_motor_powers(x, y, z, speed);
            // </editor-fold>

            switch(outtake){
                case Rest: 
                    target_RPM = 0;
                    kicker.setPosition(0);
                    if (gamepad1.a) {
                        outtake = Outtake.Shoot; 
                        Shoot_Time.reset();
                        target_RPM = 0;}
                    else if (gamepad1.b) {
                        outtake = Outtake.Shoot; 
                        Shoot_Time.reset();
                        target_RPM = 0;}
                case Shoot:
                    if (Shoot_Time.seconds() > 1 && Shoot_Time.seconds() < 1.5){
                        kicker.setPosition(0);
                    } else if (Shoot_Time.seconds() < 2){
                        kicker.setPosition(0);
                    } else if (Shoot_Time.seconds() < 3){
                        kicker.setPosition(0);
                    } else if (Shoot_Time.seconds() < 3.5){
                        kicker.setPosition(0);
                    } else if (Shoot_Time.seconds() < 4.5){
                        kicker.setPosition(0);
                    } else if (Shoot_Time.seconds() < 5){
                        outtake = Outtake.Rest;
                    }
            }

            shooter.set_RPM(target_RPM);
            telemetry.addData("Target RPM", target_RPM);
            telemetry.addData("Fly 1 RPM", (int) (shooter.get_wheel1_rpm() * 1.25));
        //    telemetry.addData("Fly 2 RPM", shooter.get_wheel2_rpm());
            telemetry.update();
        }
    }
}
