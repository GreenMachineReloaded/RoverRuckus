package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm {

    private DcMotor armPulley;
    private DcMotor armHinge;
    private Servo collector;

    private Telemetry telemetry;

    private int armPulleyEncoder;
    private int armHingeEncoder;

    public RobotArm(DcMotor armPulley, DcMotor armHinge, Servo collector, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.collector = collector;
        this.telemetry = telemetry;
    }

    public void extend(float joystick){
        if(joystick != 0.00){
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(joystick);
        } else {
            armPulleyEncoder = armPulley.getCurrentPosition();
            armPulley.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armPulley.setTargetPosition(armPulleyEncoder);
        }
        telemetry.addData("Pulley encoder value:", armPulleyEncoder);
    }

    public void flippy(float joystick){
        if(joystick != 0.00){
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(joystick);
        } else {
            armHingeEncoder = armPulley.getCurrentPosition();
            armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armHinge.setTargetPosition(armHingeEncoder);
        }
        telemetry.addData("Hinge encoder value:", armHingeEncoder);
    }

    public void collect(boolean bumper){
        if(bumper){
            collector.setPosition(1.0);
        }
        else{
            collector.setPosition(0.5);
        }
    }
}
