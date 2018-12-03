package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift {

    private DcMotor liftMotor;

    private Telemetry telemetry;

    public RobotLift(DcMotor liftMotor, Telemetry telemetry){
        this.liftMotor = liftMotor;
        this.telemetry = telemetry;

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(0);
    }

    public void lift (boolean bumper, float trigger) {
        if(bumper){
            if(trigger == 1) {
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(-0.5);
            }
        } else if(trigger == 1) {
            if(bumper){
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(0.5);
            }
        } else {
            liftMotor.setPower(0);
        }
        telemetry.addData("Lift Encoder:", liftMotor.getCurrentPosition());
    }
}
