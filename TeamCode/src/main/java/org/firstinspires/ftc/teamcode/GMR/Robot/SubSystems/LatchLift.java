package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LatchLift {
    public DcMotor liftMotor;

    private int liftEncoder;

    public LatchLift(DcMotor liftMotor, Telemetry telemetry) {
        this.liftMotor = liftMotor;

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void lift(boolean bumper, float trigger, Telemetry telemetry) {
        if(bumper && trigger == 1){
            liftMotor.setPower(0);
        }
        else if(bumper && trigger != 1){
            liftMotor.setPower(-5);
        }
        else if(trigger == 1 && !bumper){
            liftMotor.setPower(-.5);
        }
        else if(!bumper && trigger != 1){
            liftMotor.setPower(0);
        }
        telemetry.addData("Lift Encoder:", liftMotor.getCurrentPosition());
    }
}
