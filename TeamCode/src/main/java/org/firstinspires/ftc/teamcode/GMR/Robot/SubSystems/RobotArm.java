package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm {

    private DcMotor armPulley;
    private DcMotor armHinge;

    private Telemetry telemetry;

    private int armPulleyEncoder;
    private int armHingeEncoder;

    public RobotArm(DcMotor armPulley, DcMotor armHinge, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.telemetry = telemetry;
    }

    public void extend(boolean bumper, float trigger){
        if(bumper && trigger != 1){
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(1.0);
        } else if (!bumper && trigger == 1){
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(-1.0);
        } else {
            armPulleyEncoder = armPulley.getCurrentPosition();
            armPulley.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armPulley.setTargetPosition(armPulleyEncoder);
        }
        telemetry.addData("Pulley encoder value:", armPulleyEncoder);
    }

    public void flippy(boolean bumper, float trigger){
        if(bumper && trigger != 1){
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(0.25);
        } else if (!bumper && trigger == 1){
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(-0.25);
        } else {
            armHingeEncoder = armPulley.getCurrentPosition();
            armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armHinge.setTargetPosition(armHingeEncoder);
        }
        telemetry.addData("Hinge encoder value:", armHingeEncoder);
    }
}
