package org.firstinspires.ftc.teamcode;

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
@Disabled

public class Comp1Tele extends LinearOpMode {
    hwmap robot = new hwmap();

    public double speed = 1;      //Speed of the robot, either base speed at 1.0 or slow speed at 0.3

    public static double p = .006, i = 0, d = 0.0;

    public static int LiftTarget = 0; // target position
    public static int ClawTarget = 0;
    double clawOpen = 0.3;
    double clawClose = 0.7;
    double wristGrab = 0.55;
    double wristNeutral = 0.4;
    double wristBucket = 0;
    double elbowGrab = 0.147;
    double elbowNeutral = 0.7;
    double elbowBucket = 0.82;
    double bucketScore = 0;
    double bucketNeutral = 0.9;
    int liftHigh = 2600;
    int liftMiddle = 1350;
    int liftNeutral = 50;
    public PIDController controller;


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Lift lift = new Lift(hardwareMap);
        robot.bucket.setPosition(bucketNeutral);
        robot.elbow.setPosition(elbowNeutral);
        robot.claw.setPosition(clawClose);
        robot.wrist.setPosition(wristNeutral);
        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            double leftFrontSpeed =  (-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            double rightFrontSpeed = (-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
            double leftBackSpeed = (-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);
            double rightBackSpeed = (-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);

            robot.rightFront.setPower(rightFrontSpeed);
            robot.leftFront.setPower(leftFrontSpeed);
            robot.leftBack.setPower(leftBackSpeed);
            robot.rightBack.setPower(rightBackSpeed);
//comment

            if (gamepad1.a){
                lift.reset();
            }
  if (gamepad1.dpad_down)
       LiftTarget = liftNeutral;
   else if (gamepad1.dpad_right)
       LiftTarget = liftMiddle;
   else if (gamepad1.dpad_up)
       LiftTarget = liftHigh;

   if (gamepad2.left_trigger > 0.8){
       robot.claw.setPosition(clawOpen);
   } else if (gamepad2.right_trigger > 0.8){
       robot.claw.setPosition(clawClose);
   }
   if (gamepad2.a)
       robot.elbow.setPosition(elbowGrab);
   else if (gamepad2.x)
       robot.elbow.setPosition(elbowBucket);
   else if (gamepad2.b)
       robot.elbow.setPosition(elbowNeutral);


   if (gamepad2.dpad_down)
       robot.wrist.setPosition(wristGrab);
   else if (gamepad2.dpad_right)
        robot.wrist.setPosition(wristBucket);

   if (gamepad1.left_trigger > .8) {
       robot.bucket.setPosition(bucketNeutral);
   } else if (gamepad1.right_trigger> .8){
       robot.bucket.setPosition(bucketScore);
   }

        if (gamepad2.left_bumper)
            ClawTarget = 5;
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

                      robot.LLarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                      robot.LRarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                      robot.Harm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
                  telemetry.addData("speed divide ", speed);
                }
                public void reset(){
                    robot.LLarm.setPower(-.4);
                    robot.LRarm.setPower(-.4);
                    robot.LLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.LRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.LLarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.LRarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
             }
        }


