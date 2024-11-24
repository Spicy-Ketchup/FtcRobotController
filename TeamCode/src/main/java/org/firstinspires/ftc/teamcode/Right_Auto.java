package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    Uncomment the @Disabled annotation to remove the OpMode from the OpMode list.
    Make sure to do this if you are moving something to a disabled folder or just
    don't want it tp show up on the phone.

    The green "autonTemplate" text below is the name of your program.
    This can be changed to whatever you want to show up on the driver station.

    The green "Learning Autonomous" text will place your code in order on the phones by group.
    If you have multiple groups, there will be a line in between them on the phone screen.
    I'd recommend using a different group for each person's test code, and each individual competition's code.
*/
@Autonomous(name="rightAuto")
//@Disabled

/*
    Make sure that the code below (currently labeled autonTemplate) always matches your file name.
*/
public class Right_Auto extends LinearOpMode{



    hwmap robot = new hwmap();

    private ElapsedTime runtime = new ElapsedTime();

    //called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException {
        //initialize hardwaremap from above
        robot.init(hardwareMap);

        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();



        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .turnTo(45);

        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .build();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(tab1.build())
        );

        /** RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0))
                        .forward(30)
                        .turn(Math.toRadians(90))
                        .build());

        //This is where your actual autonomous code will go.


        /*
            Example of using an encoder:
                leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //encoder position that you want the motor to run to.  (1 full rotation = 537.7)
                leftMotor.setTargetPosition(800);
                leftMotor.setPower(.5); //power is between -1.0 and 1.0
            //while motor hasn't reached position and opMode is still running, do nothing else
                leftMotor (rearLeft.isBusy() && opModeIsActive()) {
                }
            //once motor has reached target encoder position, stop the motor.
                leftMotor.setPower(0);
        }*/
    }

}