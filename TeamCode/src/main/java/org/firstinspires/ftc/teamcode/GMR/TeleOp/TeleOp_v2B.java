package org.firstinspires.ftc.teamcode.GMR.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Robot2;

@TeleOp(name = "TeleOp_v2B")
public class TeleOp_v2B extends OpMode {

    private Robot2 robot;

    @Override
    public void init() {
        robot = new Robot2(hardwareMap, telemetry);
    }


    @Override
    public void loop() {
        robot.driveTrain.setMotorPower(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.robotLift.lift(gamepad2.left_bumper, gamepad2.left_trigger, false, false);
        robot.robotArm.extend(gamepad1.left_bumper, gamepad1.left_trigger);
        robot.robotArm.flippy(gamepad1.right_bumper, gamepad1.right_trigger);
        robot.robotArm.collect(gamepad2.right_bumper, gamepad2.right_trigger);
        robot.runServo(gamepad2.a, gamepad2.x);
        //telemetry.addData("Gold Position:", robot.sample());
        telemetry.update();
    }
}
