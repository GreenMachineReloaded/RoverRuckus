package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm2 {

    private DcMotor armPulley;
    private DcMotor armHinge;
    private CRServo collector;

    private Telemetry telemetry;

    private int armPulleyEncoder;
    private int currentHingePosition;
    private int targetHingePosition;

    private boolean isLifting = false;

    public RobotArm2(DcMotor armPulley, DcMotor armHinge, CRServo collector, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.collector = collector;
        this.telemetry = telemetry;
        armPulley.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void extend(boolean bumper, float trigger){
        if(bumper && trigger != 1.0) {
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(-0.5);
        } else if(!bumper && trigger == 1.0) {
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(0.5);
        } else if(!bumper && trigger != 1.0) {
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(0.0);
        } else if(bumper && trigger == 1.0){
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(0.0);
        } else {
            armPulleyEncoder = armPulley.getCurrentPosition();
            armPulley.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armPulley.setTargetPosition(armPulleyEncoder);
        }
        // telemetry.addData("Pulley encoder value:", armPulleyEncoder);
    }

    public void flippy(boolean bumper, float trigger){
        if(bumper && trigger != 1.0) {
            currentHingePosition = armHinge.getCurrentPosition();
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(-0.25);
        } else if(!bumper && trigger == 1.0) {
            currentHingePosition = armHinge.getCurrentPosition();
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(0.25);
        } else {
            armHinge.setPower(0.0);
        }
        telemetry.addData("Hinge encoder value:", armPulley.getCurrentPosition());
        telemetry.addData("Hinge goal position:", currentHingePosition);
    }

    public void collect(boolean bumper){
        if(bumper){
            collector.setPower(-1.0);
        }
        else{
            collector.setPower(0.0);
        }
        // telemetry.addData("Right bumber value: ", bumper);
        // telemetry.addData("Collector power: ", collector.getPower());
    }
}
