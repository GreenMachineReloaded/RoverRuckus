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
        robot.liftSoas();
        robot.robotLift.lock();
        robot.driveTrain.disableEncoders();
        //robot.robotLift.hold(robot.robotLift.getEncoderPosition());
    }


    @Override
    public void loop() {
        robot.driveTrain.setMotorPowerSlow(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, robot.slowToggle(gamepad1.a));
        robot.robotLift.lift(gamepad2.left_bumper, gamepad2.left_trigger, false, false);
        robot.robotArm.extend(gamepad1.left_bumper, gamepad1.left_trigger);
        robot.robotArm.flippy(gamepad1.right_bumper, gamepad1.right_trigger, gamepad1.y);
        robot.robotArm.setCollectorPower(gamepad2.dpad_up, gamepad2.dpad_down);
        robot.robotArm.collect(gamepad2.right_bumper, gamepad2.right_trigger);
        robot.runServo(gamepad2.a, gamepad2.x);
        //robot.robotLift.lockButton(gamepad2.b);
        telemetry.addData("Slow:", robot.slowToggle(gamepad1.a));
        telemetry.clearAll();
        telemetry.addData("Collector power: ", robot.robotArm.getCollectorPower());
        telemetry.update();
    }
}
