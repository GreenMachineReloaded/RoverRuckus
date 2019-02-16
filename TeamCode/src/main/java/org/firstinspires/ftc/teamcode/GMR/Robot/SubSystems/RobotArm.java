package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotArm {

    private DcMotor armPulley;
    private DcMotor armHinge;
    private CRServo collector;

    private Telemetry telemetry;

    private int armPulleyEncoder;
    private int currentHingePosition;
    private int targetHingePosition;

    private boolean isLifting = false;

    public RobotArm(DcMotor armPulley, DcMotor armHinge,CRServo collector, Telemetry telemetry){
        this.armPulley = armPulley;
        this.armHinge = armHinge;
        this.collector = collector;
        this.telemetry = telemetry;
    }

    public void extend(float joystick){
        if(joystick != 0.00){
            armPulley.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPulley.setPower(-joystick/2);
        } else {
            armPulleyEncoder = armPulley.getCurrentPosition();
            armPulley.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armPulley.setTargetPosition(armPulleyEncoder);
        }
        // telemetry.addData("Pulley encoder value:", armPulleyEncoder);
    }

    public void flippy(float joystick){
        if(joystick != 0.00){
            currentHingePosition = armHinge.getCurrentPosition();
            armHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armHinge.setPower(joystick/4);
        } else {
            if ((Math.abs(currentHingePosition - armHinge.getCurrentPosition())) > 10)  {
                currentHingePosition = armHinge.getCurrentPosition();
                armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armHinge.setTargetPosition(currentHingePosition);
            } else {
                armHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armHinge.setTargetPosition(currentHingePosition);
            }
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
