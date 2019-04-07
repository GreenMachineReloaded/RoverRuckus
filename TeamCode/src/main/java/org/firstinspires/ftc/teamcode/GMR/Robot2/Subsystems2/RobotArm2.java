package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm2 {

    private DcMotor armPulley;
    private DcMotor armHinge;
    private DcMotor collector;

    private Telemetry telemetry;

    private int targetHingePosition;
    private int targetPulleyPosition;

    private int ARM_SCORING_POSITION;
    private int initialArmPosition;

    private double collectorPower;
    private boolean isPressed;


    public RobotArm2(DcMotor armPulley, DcMotor armHinge, DcMotor collector, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.collector = collector;
        this.telemetry = telemetry;
        initialArmPosition = this.armHinge.getCurrentPosition();
        ARM_SCORING_POSITION = initialArmPosition + 1930;
        collectorPower = 0.75;
        isPressed = false;
    }

    public void extend(boolean bumper, float trigger) {
        targetPulleyPosition = runMotorAndHoldPosition(bumper, trigger, armPulley, 0.5f, targetPulleyPosition);
    }

    public void flippy(boolean bumper, float trigger, boolean y) {

        float power = 0.25f;
        if(Math.abs(armHinge.getCurrentPosition() - ARM_SCORING_POSITION) <= 300){
            power = 0.10f;
        }
        if (y) {
            armHinge.setPower(-power);
            armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armHinge.setTargetPosition(ARM_SCORING_POSITION);
        } else {
//            telemetry.addData("Initial arm position: ", initialArmPosition);
//            telemetry.addData("Current arm position: ", armHinge.getCurrentPosition());
            targetHingePosition = runMotorAndHoldPosition(bumper, trigger, armHinge, power, targetHingePosition);
        }
    }

    public void collect(boolean bumper, float trigger){
        if (bumper && trigger != 1.0) {
            collector.setPower(collectorPower);
        }
        else if (!bumper && trigger == 1.0) {
            collector.setPower(-collectorPower);
        } else {
            collector.setPower(0);
        }
    }

    public void setCollectorPower(boolean up, boolean down){
        if(up && !isPressed){
            isPressed = true;
            collectorPower += 0.05;
        }
        else if(down && !isPressed){
            isPressed = true;
            collectorPower -= 0.05;
        }
        else if(isPressed && !up && !down){
            isPressed = false;
        }
    }
    public double getCollectorPower(){
        return collectorPower;
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
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if ((Math.abs(targetEncoderPosition - motor.getCurrentPosition())) > 10) {
                motor.setPower(1.00);
                targetEncoderPosition = motor.getCurrentPosition();
            }

            motor.setTargetPosition(targetEncoderPosition);
        }
        return targetEncoderPosition;
    }
}
