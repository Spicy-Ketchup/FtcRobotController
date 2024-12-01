package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="rightAuto")
//@Disabled
public class Right_Auto extends LinearOpMode{

    public static int LTarget = 0;
    public PIDController Controller;
    public static double p = .006, i = 0, d = 0.0;

    public class Lift {
        private DcMotorEx LL;
        private DcMotorEx LR;

        public Lift(HardwareMap hardwareMap){
            LL = hardwareMap.get(DcMotorEx.class, "ll");
            LR = hardwareMap.get(DcMotorEx.class, "lr");
            LL.setDirection(DcMotor.Direction.FORWARD);
            LR.setDirection(DcMotor.Direction.REVERSE);
            LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            Controller = new PIDController(p, i, d);
        }

        public void update() {
            // Beep boop this is the lift update function
            // Assume this runs some PID controller for the lift

            Controller.setPID(p, i, d);


            int LLPos = LL.getCurrentPosition();
            int LRPos = LR.getCurrentPosition();



            double LLarmPID = Controller.calculate(LLPos, LTarget);
            double LRarmPID = Controller.calculate(LRPos, LTarget);


            double LLPower = LLarmPID;
            double LRPower = LRarmPID;

            LL.setPower(LLPower);
            LR.setPower(LRPower);
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();



        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(10,17),Math.toRadians(-34))
                .waitSeconds(2.5)
                .strafeToLinearHeading(new Vector2d(21,7),Math.toRadians(-4))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(13,15),Math.toRadians(-34))
                .waitSeconds(2.5)
                .strafeToLinearHeading(new Vector2d(20,23),Math.toRadians(-5))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(9,17),Math.toRadians(-35))
                .waitSeconds(2.5)
                .strafeToLinearHeading(new Vector2d(21.5,18.5),Math.toRadians(24))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(8.5,12.5),Math.toRadians(-29))
                .waitSeconds(2.5)
                .strafeToLinearHeading(new Vector2d(55,0),Math.toRadians(87))
                .strafeToConstantHeading(new Vector2d(55,-24));


        Action trajectoryAction = tab1.build();

            Actions.runBlocking(
                    new SequentialAction(trajectoryAction));

    }

}