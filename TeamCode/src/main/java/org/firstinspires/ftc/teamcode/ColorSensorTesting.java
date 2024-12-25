package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="LL+CS+LED Spintake Testing")
public class ColorSensorTesting extends LinearOpMode {
    hwmap robot = new hwmap();
    double speed_x = 0;
    double speed_a = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.limelight.pipelineSwitch(0);
        robot.limelight.start();

        waitForStart();

        while (opModeIsActive()) {

            LLResult result = robot.limelight.getLatestResult();
            if (result.getTx() == 0)
                speed_x = 0;
                else if (result.getTx() > 4.25)
                    speed_x = .03;
                else if (result.getTx() < -4.25)
                    speed_x = -.03;
                else
                    speed_x = 0;

                if (result.getTa() == 0)
                    speed_a = 0;
                else if (result.getTa() < 15)
                    speed_a = .02;
                else if (result.getTa() > 20)
                    speed_a = -.04;
                else
                    speed_a = 0;

                robot.leftFront.setPower(speed_x + speed_a);
                robot.rightFront.setPower(-speed_x + speed_a);
                robot.leftBack.setPower(-speed_x + speed_a);
                robot.rightBack.setPower(speed_x + speed_a);

                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                 telemetry.addData("ta", result.getTa());


            //     int red = robot.colorSensor.red();
       //     int green = robot.colorSensor.green();
       //     int blue = robot.colorSensor.blue();

        //    if (red>=250 &&  green>=450) {
       //         robot.LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
       //     } else if (red >= 250 && green<900) {
        //        robot.LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        //    }


           //   if (red > 300 && green <=400) {
          //        robot.Spintake1.setPower(-1.0);
          //        robot.Spintake2.setPower(-1.0);
          //  } else if (gamepad1.a) {
          //     robot.Spintake1.setPower(0.28);
          //        robot.Spintake2.setPower(0.28);
          //    }
          //    else if (gamepad1.b) {
           //       robot.Spintake1.setPower(-1.0);
          //        robot.Spintake2.setPower(-1.0);
          //    }
          //    else{
            //      robot.Spintake1.setPower(0);
           //       robot.Spintake2.setPower(0);
           //   }


           // telemetry.addData("Blue: ", blue);
           // telemetry.addData("Red: ", red);
          //  telemetry.addData("Green: ", green);
            telemetry.update();

        }
    }
}
