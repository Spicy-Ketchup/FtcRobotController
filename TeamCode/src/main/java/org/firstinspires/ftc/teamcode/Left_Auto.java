package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="LeftAuto")
//@Disabled
public class Left_Auto extends LinearOpMode{

    public PIDController LiftController;
    public PIDController HarmController;
    public static double p = .006, i = 0, d = 0.0;
    int LT = 0;
    int HT = 0;
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

    private Servo elbow = null;
    private Servo claw = null;
    private Servo wrist = null;
    private Servo bucket = null;

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

            LiftController = new PIDController(p, i, d);
        }

        public void update() {
            LiftController.setPID(p, i, d);


            int Pos = (LL.getCurrentPosition()+LR.getCurrentPosition())/2;



            double LPID = LiftController.calculate(Pos, LT);


            double LPower = LPID;

            LL.setPower(LPower);
            LR.setPower(LPower);
        }
    }

    public class Harm {
        private DcMotorEx harm;

        public Harm(HardwareMap hardwareMap){
            harm = hardwareMap.get(DcMotorEx.class, "harm");
            harm.setDirection(DcMotor.Direction.FORWARD);
            harm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            harm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            HarmController = new PIDController(p, i, d);
        }

        public void update() {
            HarmController.setPID(p, i, d);


            int Pos = harm.getCurrentPosition();



            double HPID = HarmController.calculate(Pos, HT);


            double HPower = HPID;

            harm.setPower(HPower);
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {
        Lift AutoLift = new Lift(hardwareMap);
        Harm AutoHarm = new Harm(hardwareMap);
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        elbow = hardwareMap.get(Servo.class, "elbow");
        claw = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class, "wrist");
        bucket = hardwareMap.get(Servo.class, "bucket");
        bucket.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();


            Actions.runBlocking(

                            new ParallelAction(
                                    (p) -> {AutoLift.update(); return true;},
                                    (p) -> {AutoHarm.update(); return true;},
                                    new SequentialAction(
                                            (p) -> {elbow.setPosition(elbowNeutral); return false;},
                                            (p) -> {wrist.setPosition(wristNeutral); return false;},
                                            (p) -> {claw.setPosition(clawClose); return false;},
                                            drive.actionBuilder(initialPose)
                                                    .waitSeconds(.3)
                                                    .strafeToLinearHeading(new Vector2d(10,17),Math.toRadians(-34))
                                    .build(),
                                    (p) -> {LT=2600; return false;},
                                            drive.actionBuilder(new Pose2d(10,17,Math.toRadians(-34)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {bucket.setPosition(bucketScore); return false;},
                                            drive.actionBuilder(new Pose2d(10,17,Math.toRadians(-34)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {LT=50; return false;},
                                            (p) -> {bucket.setPosition(bucketNeutral); return false;},
                                            drive.actionBuilder(new Pose2d(10,17,Math.toRadians(-34)))
                                    .strafeToLinearHeading(new Vector2d(14.4,11),Math.toRadians(-10))
                                    .build(),
                                            (p) -> {HT=900; return false;},
                                            (p) -> {claw.setPosition(clawOpen); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(.3)
                                                    .build(),
                                            (p) -> {wrist.setPosition(wristGrab); return false;},
                                            (p) -> {elbow.setPosition(elbowGrab); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                    .waitSeconds(.5)
                                    .build(),
                                            (p) -> {claw.setPosition(clawClose); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(.2)
                                                    .build(),
                                            (p) -> {elbow.setPosition(elbowBucket); return false;},
                                            (p) -> {wrist.setPosition(wristBucket); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(.3)
                                                    .build(),
                                            (p) -> {HT = 5; return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(.65)
                                                    .build(),
                                            (p) -> {claw.setPosition(clawOpen); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(.4)
                                                    .build(),
                                            (p) -> {elbow.setPosition(elbowNeutral); return false;},
                                            (p) -> {wrist.setPosition(wristNeutral); return false;},
                                            (p) -> {claw.setPosition(clawClose); return false;},
                                            drive.actionBuilder(new Pose2d(14.4,11,Math.toRadians(-10)))
                                                    .waitSeconds(0.5)
                                                    .strafeToLinearHeading(new Vector2d(9.7,13.5),Math.toRadians(-33))
                                                 .build(),
                                    (p) -> {LT=2600; return false;},   //here
                                            drive.actionBuilder(new Pose2d(9.7,13.5,Math.toRadians(-33)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {bucket.setPosition(bucketScore); return false;},
                                            drive.actionBuilder(new Pose2d(9.7,13.5,Math.toRadians(-33)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {LT=50; return false;},
                                            (p) -> {bucket.setPosition(bucketNeutral); return false;},
                            drive.actionBuilder(new Pose2d(9.7,13.5,Math.toRadians(-33)))
                                    .waitSeconds(.65)
                                    .strafeToLinearHeading(new Vector2d(15.5,23.09),Math.toRadians(-9))
                                    .waitSeconds(.35)
                                    .build(),
                                            (p) -> {HT=900; return false;},
                                            (p) -> {claw.setPosition(clawOpen); return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.3)
                                                    .build(),
                                            (p) -> {wrist.setPosition(wristGrab); return false;},
                                            (p) -> {elbow.setPosition(elbowGrab); return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.5)
                                                    .build(),
                                            (p) -> {claw.setPosition(clawClose); return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.3)
                                                    .build(),
                                            (p) -> {elbow.setPosition(elbowBucket); return false;},
                                            (p) -> {wrist.setPosition(wristBucket); return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.45)
                                                    .build(),
                                            (p) -> {HT = 5; return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.65)
                                                    .build(),
                                            (p) -> {claw.setPosition(clawOpen); return false;},
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.3)
                                                    .build(),
                                            (p) -> {elbow.setPosition(elbowNeutral); return false;},
                                            (p) -> {wrist.setPosition(wristNeutral); return false;},
                                            (p) -> {claw.setPosition(clawClose); return false;},//here
                                            drive.actionBuilder(new Pose2d(15.5,23.09,Math.toRadians(-9)))
                                                    .waitSeconds(.45)
                                                    .strafeToLinearHeading(new Vector2d(11.5,15),Math.toRadians(-33))
                                    .build(), //really here
                                            (p) -> {LT=2600; return false;},   //here
                                            drive.actionBuilder(new Pose2d(11.5,15,Math.toRadians(-33)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {bucket.setPosition(bucketScore); return false;},
                                            drive.actionBuilder(new Pose2d(11.5,15,Math.toRadians(-33)))
                                                    .waitSeconds(1)
                                                    .build(),
                                            (p) -> {LT=50; return false;},
                                            (p) -> {bucket.setPosition(bucketNeutral); return false;},
                                            drive.actionBuilder(new Pose2d(11.5,15,Math.toRadians(-33)))
                                                    .waitSeconds(0.55)
                                                    .build(),
                            new ParallelAction(
                                    drive.actionBuilder(new Pose2d(11,15,Math.toRadians(-33)))
                                            .strafeToLinearHeading(new Vector2d(57,0),Math.toRadians(87))
                                            .strafeToLinearHeading(new Vector2d(57,-23.1),Math.toRadians(87))
                                            .build(),
                                    (p) -> {LT=1200; return false;}
                                    ))
                    ));

    }

}