package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm2 {

    private DcMotor armPulley;
    private DcMotor armHinge;
    private CRServo collector;

    private Telemetry telemetry;

    private int targetHingePosition;
    private int targetPulleyPosition;

    private int ARM_SCORING_POSITION;
    private int initialArmPosition;


    public RobotArm2(DcMotor armPulley, DcMotor armHinge, CRServo collector, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.collector = collector;
        this.telemetry = telemetry;
        initialArmPosition = this.armHinge.getCurrentPosition();
        ARM_SCORING_POSITION = initialArmPosition + 1930;

    }

    public void extend(boolean bumper, float trigger) {
        targetPulleyPosition = runMotorAndHoldPosition(bumper, trigger, armPulley, 0.5f, targetPulleyPosition);
    }

    public void flippy(boolean bumper, float trigger, boolean y) {
        if (y) {
            armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armHinge.setTargetPosition(ARM_SCORING_POSITION);
        } else {
            telemetry.addData("Initial arm position: ", initialArmPosition);
            telemetry.addData("Current arm position: ", armHinge.getCurrentPosition());
            targetHingePosition = runMotorAndHoldPosition(bumper, trigger, armHinge, 0.25f, targetHingePosition);
        }
    }

    public void collect(boolean bumper, float trigger){
        if (bumper && trigger != 1.0) {
            collector.setPower(0.80);
        }
        else if (!bumper && trigger == 1.0) {
            collector.setPower(-0.80);
        } else {
            collector.setPower(0);
        }
    }

    private int runMotorAndHoldPosition(boolean bumper, float trigger, DcMotor motor, float power, int targetEncoderPosition) {
        if (bumper && trigger != 1.0) {
            targetEncoderPosition = motor.getCurrentPosition();
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPower(-power);
        } else if (!bumper && trigger == 1.0) {
            targetEncoderPosition = motor.getCurrentPosition();
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPower(power);
        } else {
            if ((Math.abs(targetEncoderPosition - motor.getCurrentPosition())) > 10) {
                motor.setPower(1.00);
                targetEncoderPosition = motor.getCurrentPosition();
            }
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(targetEncoderPosition);
        }
        return targetEncoderPosition;
    }
}
