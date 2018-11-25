package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift {

    private DcMotor liftMotor;

    private Telemetry telemetry;

    private boolean encodersCanRun;

    public RobotLift(DcMotor liftMotor, Telemetry telemetry){
        this.liftMotor = liftMotor;
        this.telemetry = telemetry;

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(0);

        this.encodersCanRun = true;
    }

    public void lift (boolean bumper, float trigger) {
        //CONTROLS: Bumper extends lift, trigger retracts lift

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

    public boolean setLift (double goalPos,  double power) {
        //Input goalPos must be between 0.0 and 1.0

        int currentPos = liftMotor.getCurrentPosition();

        if(encodersCanRun){
            //goalPos is scaled out to work with encoder values
            goalPos = Range.clip(goalPos, 0.0, 1.0);
            goalPos = Range.scale(goalPos, 0.0, 1.0, 5, 2830);
            encodersCanRun = false;
            return encodersCanRun;
        } else {
            if(currentPos < goalPos){
                liftMotor.setPower(-power);
                telemetry.addData("Current Lift Value:", currentPos);
                telemetry.addData("Lift Goal Value:", goalPos);
            } else if(currentPos > goalPos){
                liftMotor.setPower(power);
                telemetry.addData("Current Lift Value:", currentPos);
                telemetry.addData("Lift Goal Value:", goalPos);
            } else {
                encodersCanRun = true;
                liftMotor.setPower(0.0);
                return encodersCanRun;
            }
        }
        return false;
    }
}
