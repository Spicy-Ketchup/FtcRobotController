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

@TeleOp(name="LM1 TeleOp")
//@Disabled

public class Comp1Tele extends LinearOpMode {


    hwmap robot = new hwmap();

    public double speed = 1.0;      //Speed of the robot, either base speed at 1.0 or slow speed at 0.3

    public static double p = .006, i = 0, d = 0.0;

    public static int LiftTarget = 0; // target position
    public static int ClawTarget = 0;
    public PIDController controller;


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Lift lift = new Lift(hardwareMap);
        robot.elbow.setPosition(.7);
        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            telemetry.update();


            robot.leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
            robot.rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);
            robot.leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
            robot.rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);

         //   speed = gamepad1.a && speed == 1.0 ? .1 : gamepad1.a && speed == .1 ? .1 : speed;
            telemetry.addData("speed: ", speed);

//comment
  if (gamepad1.dpad_down)
       LiftTarget = 0;
   else if (gamepad1.dpad_right)
       LiftTarget = 2300;
   else if (gamepad1.dpad_up)
       LiftTarget = 3200;

   if (gamepad2.left_trigger > 0.8){
       robot.claw.setPosition(.6);
   } else if (gamepad2.right_trigger > 0.8){
       robot.claw.setPosition(1.0);
   }
   if (gamepad2.a && robot.elbow.getPosition() > .4)
       robot.elbow.setPosition(.12);
   else if (gamepad2.a && robot.elbow.getPosition() < .2)
       robot.elbow.setPosition(.95);
   else if (gamepad2.b)
       robot.elbow.setPosition(.7);


   if (gamepad2.x)
       robot.wrist.setPosition(.9);
   else if (gamepad2.y)
        robot.wrist.setPosition(.3);

   if (gamepad1.left_trigger > .8) {
       robot.bucket.setPosition(0.1);
   } else if (gamepad1.right_trigger> .8){
       robot.bucket.setPosition(0.8);
   }

        if (gamepad2.left_bumper)
            ClawTarget = -55;
        else if (gamepad2.right_bumper)
            ClawTarget = 880;

        lift.update();
        }
    }
               class Lift {
                  public Lift(HardwareMap hardwareMap) {
            // Beep boop this is the the constructor for the lift
            // Assume this sets up the lift hardware
                      robot.LLarm = hardwareMap.get(DcMotorEx.class,"ll");
                      robot.LRarm = hardwareMap.get(DcMotorEx.class,"lr");
                      robot.Harm = hardwareMap.get(DcMotorEx.class, "harm");

                      robot.LLarm.setDirection(DcMotor.Direction.FORWARD);
                      robot.LRarm.setDirection(DcMotor.Direction.REVERSE);
                      robot.Harm.setDirection(DcMotor.Direction.FORWARD);

                      robot.LLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                      robot.LRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                      robot.Harm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                      robot.LLarm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                      robot.LRarm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                      robot.Harm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                     controller = new PIDController(p, i, d);

                  }

                 public void update() {
            // Beep boop this is the lift update function
            // Assume this runs some PID controller for the lift

                  controller.setPID(p, i, d);


                  int LLarmPos = robot.LLarm.getCurrentPosition();
                  int LRarmPos = robot.LRarm.getCurrentPosition();
                  int HarmPos = robot.Harm.getCurrentPosition();


                   double LLarmPID = controller.calculate(LLarmPos, LiftTarget);
                   double LRarmPID = controller.calculate(LRarmPos, LiftTarget);
                   double HarmPID = controller.calculate(HarmPos, ClawTarget);



                   double LLPower = LLarmPID;
                   double LRPower = LRarmPID;
                   double HarmPower = HarmPID;

                  robot.LLarm.setPower(LLPower);
                  robot.LRarm.setPower(LRPower);
                  robot.Harm.setPower(HarmPower);
                  telemetry.addData("lift: ",LiftTarget);
                  telemetry.addData("lift power: ", LLPower);
                  telemetry.addData("Lift Pos: ", robot.LLarm.getCurrentPosition());
                  telemetry.addData("Claw Pos: ", robot.Harm.getCurrentPosition());
                }
             }
        }


