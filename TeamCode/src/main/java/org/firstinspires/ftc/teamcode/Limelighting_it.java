package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//
//@Config
//@TeleOp(name="Limylit")
////@Disabled
//public class Limelighting_it extends LinearOpMode {
//    public Limelight3A limelight;
//    public CRServo servo = null;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        servo = hardwareMap.get(CRServo.class, "servo");
//        servo.setDirection(CRServo.Direction.REVERSE);
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        telemetry.setMsTransmissionInterval(11);
//        limelight.pipelineSwitch(5);
//        limelight.start();
//        servo.setPower(0);
//        waitForStart();
//        while (opModeIsActive()) {
//            LLResult result = limelight.getLatestResult();
//            if (result != null && result.isValid()) {
//                    double tx = result.getTx();
//                    double servo_power = (tx > -1 && tx < 1) ? 0 : tx / 132;
//                    servo_power = (servo_power > 1) ? 1 : (servo_power < -1) ? -1 : servo_power;
//                    servo_power = (servo_power < .05 && servo_power > 0) || (servo_power > -.05 && servo_power < 0) ? 0 : servo_power;
//                    servo.setPower(servo_power);
//                    telemetry.addData("tx", tx);
//                    telemetry.addData("Servo Power", servo_power);
//                    telemetry.update();
//            } else servo.setPower(0);
//        }
//    }
//}