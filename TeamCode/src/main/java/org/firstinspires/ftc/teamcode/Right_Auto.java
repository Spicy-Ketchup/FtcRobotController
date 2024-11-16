package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

        /*
            Telemetry will be your best friend when troubleshooting.
            It can output sensor input/output, encoder values, timing, and much more.
        */
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        //wait for start button.
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();


        //This is where your actual autonomous code will go.
        DriveForward(800, 0.5);
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

    public void DriveForward(long mseconds, double power) throws InterruptedException {
        //encoder position that you want the motor to run to.  (1 full rotation = 537.7)


        robot.leftFront.setPower(power); //power is between -1.0 and 1.0
        robot.leftBack.setPower(power); //power is between -1.0 and 1.0
        robot.rightFront.setPower(power); //power is between -1.0 and 1.0
        robot.rightBack.setPower(power); //power is between -1.0 and 1.0

        //while motor hasn't reached position and opMode is still running, do nothing else
 wait(mseconds);

        //once motor has reached target encoder position, stop the motor.
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

    }
}