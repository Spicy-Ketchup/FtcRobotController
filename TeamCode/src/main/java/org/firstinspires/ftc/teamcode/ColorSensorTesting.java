package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ColorSensorTestingRedSide")
public class ColorSensorTesting extends LinearOpMode {
    hwmap robot = new hwmap();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            int red = robot.colorSensor.red();
            int green = robot.colorSensor.green();
            int blue = robot.colorSensor.blue();

            if (red>=250 &&  green>=450) {
                robot.leftLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            } else if (red >= 250 && green<900) {
                robot.leftLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }


              if (red > 300 && green <=400) {
                  robot.Spintake1.setPower(-1.0);
                  robot.Spintake2.setPower(-1.0);
            } else if (gamepad1.a) {
               robot.Spintake1.setPower(0.28);
                  robot.Spintake2.setPower(0.28);
              }
              else if (gamepad1.b) {
                  robot.Spintake1.setPower(-1.0);
                  robot.Spintake2.setPower(-1.0);
              }
              else{
                  robot.Spintake1.setPower(0);
                  robot.Spintake2.setPower(0);
              }


            telemetry.addData("Blue: ", blue);
            telemetry.addData("Red: ", red);
            telemetry.addData("Green: ", green);
            telemetry.update();

        }
    }
}
