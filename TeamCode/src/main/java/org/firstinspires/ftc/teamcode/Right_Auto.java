package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
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

    public PIDController Controller;
    public static double p = .006, i = 0, d = 0.0;
    int LT = 0;

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
            LL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            Controller = new PIDController(p, i, d);
        }

        public void update() {
            Controller.setPID(p, i, d);


            int Pos = (LL.getCurrentPosition()+LR.getCurrentPosition())/2;



            double LPID = Controller.calculate(Pos, LT);


            double LPower = LPID;

            LL.setPower(LPower);
            LR.setPower(LPower);
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {
        Lift AutoLift = new Lift(hardwareMap);
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();


            Actions.runBlocking(

                            new ParallelAction(
                                    (p) -> {
                                        AutoLift.update();
                                        return true;
                                    },
                                    new SequentialAction(
                            drive.actionBuilder(initialPose)
                                    .strafeToLinearHeading(new Vector2d(10,17),Math.toRadians(-34))
                                    .build(),
                                    (p) -> {LT=300; return false;},
                                            drive.actionBuilder(drive.pose)
                                                    .waitSeconds(2.5)
                                                    .build(),
                                    (p) -> {LT=50; return false;},
                            drive.actionBuilder(drive.pose)
                                    .strafeToLinearHeading(new Vector2d(21,7),Math.toRadians(-4))
                                    .waitSeconds(1.5)
                                    .strafeToLinearHeading(new Vector2d(13,15),Math.toRadians(-34))
                                    .build(),
                                    (p) -> {LT=300; return false;},
                                            drive.actionBuilder(drive.pose)
                                                    .waitSeconds(2.5)
                                                    .build(),
                                    (p) -> {LT=50; return false;},
                            drive.actionBuilder(drive.pose)
                                    .strafeToLinearHeading(new Vector2d(20,23),Math.toRadians(-5))
                                    .waitSeconds(1.5)
                                    .strafeToLinearHeading(new Vector2d(9,17),Math.toRadians(-35))
                                    .build(),
                                    (p) -> {LT=300; return false;},
                                            drive.actionBuilder(drive.pose)
                                                    .waitSeconds(2.5)
                                                    .build(),
                                    (p) -> {LT=50; return false;},
                            drive.actionBuilder(drive.pose)
                                    .strafeToLinearHeading(new Vector2d(21.5,18.5),Math.toRadians(24))
                                    .waitSeconds(1.5)
                                    .strafeToLinearHeading(new Vector2d(8.5,12.5),Math.toRadians(-29))
                                    .build(),
                                    (p) -> {LT=500; return false;},
                                            drive.actionBuilder(drive.pose)
                                                    .waitSeconds(2.5)
                                                    .build(),
                                    (p) -> {LT=50; return false;},
                            drive.actionBuilder(drive.pose)
                                    .strafeToLinearHeading(new Vector2d(55,0),Math.toRadians(87))
                                    .build(),
                            new ParallelAction(
                                    drive.actionBuilder(drive.pose)
                                            .strafeToLinearHeading(new Vector2d(55,0),Math.toRadians(87))
                                            .build(),
                                    (p) -> {LT=500; return false;}))
                    ));

    }

}