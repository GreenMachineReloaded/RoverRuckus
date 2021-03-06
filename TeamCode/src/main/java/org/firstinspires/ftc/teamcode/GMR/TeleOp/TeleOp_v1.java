package org.firstinspires.ftc.teamcode.GMR.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;

@TeleOp(name = "TeleOp_v1")
public class TeleOp_v1 extends OpMode {

    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        robot.driveTrain.setMotorPower(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.robotLift.lift(gamepad1.left_bumper, gamepad1.left_trigger, gamepad1.y, gamepad1.a);
        robot.rake(gamepad1.right_bumper, gamepad1.right_trigger);
        robot.runServo(gamepad1.a, gamepad1.x);
        telemetry.update();
    }
}
