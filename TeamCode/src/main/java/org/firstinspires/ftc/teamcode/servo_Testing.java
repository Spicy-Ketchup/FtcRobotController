package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

@TeleOp
//@Disabled
public class servo_Testing extends OpMode {

    ArrayList<Servo> servo = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    Servo theServo;
    double servoPos;
    private boolean upBoolean = false;
    private boolean downBoolean = false;
    private boolean yBoolean = false;
    private boolean aBoolean = false;
    private boolean leftBoolean = false;
    private boolean rightBoolean = false;

    int servoIndex = 0;

    @Override
    public void init() {

// place names of servos into names list here
        // example: names.add("sir vo");

        names.add("elbow");

        for (String name: names) {
            servo.add(hardwareMap.get(Servo.class, name));
        }

        servoPos = 0.5;
    }

    @Override
    public void loop() {

        if (!leftBoolean && gamepad1.left_bumper)
            servoIndex--;

        if (!rightBoolean && gamepad1.right_bumper)
            servoIndex++;

        if (!upBoolean && gamepad1.dpad_up)
            servoPos += 0.01;

        if (!downBoolean && gamepad1.dpad_down)
            servoPos -= 0.01;

        if (!yBoolean && gamepad1.y)
            servoPos += 0.1;

        if (!aBoolean && gamepad1.a)
            servoPos -= 0.1;

        if (servoIndex < 0)
            servoIndex = 0;

        if (servoIndex >= servo.size())
            servoIndex = servo.size() - 1;

        if (servoPos > 1.0)
            servoPos = 1.0;

        if (servoPos < 0.0)
            servoPos = 0.0;

        theServo = servo.get(servoIndex);

        theServo.setPosition(servoPos);
        telemetry.addData("testing servo", names.get(servoIndex));
        telemetry.addData("servo pos", servoPos);
        telemetry.update();

        upBoolean = gamepad1.dpad_up;
        downBoolean = gamepad1.dpad_down;
        yBoolean = gamepad1.y;
        aBoolean = gamepad1.a;
        leftBoolean = gamepad1.left_bumper;
        rightBoolean = gamepad1.right_bumper;
    }
}