package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift2 {

    private DcMotor liftMotor;

    private Telemetry telemetry;

    private boolean encodersCanRun;
    
    private final int LIFT_MIN;
    private final int LIFT_MAX;

    public RobotLift2(DcMotor liftMotor, Telemetry telemetry){
        this.liftMotor = liftMotor;
        this.telemetry = telemetry;

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(0);

        this.encodersCanRun = true;

        LIFT_MIN = liftMotor.getCurrentPosition();
        LIFT_MAX = LIFT_MIN - 3380;

        telemetry.addData("LIFT_MIN", LIFT_MIN);
        telemetry.addData("LIFT_MAX", LIFT_MAX);

    }

    //TODO: FIX AUTO LIFT
    public void lift (boolean bumper, float trigger) {
        //CONTROLS: Bumper extends lift, trigger retracts lift
        if(bumper){
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if(trigger == 1) {
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(-0.5);
            }
        } else if(trigger == 1) {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if(bumper){
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(0.5);
            }
        }
        telemetry.addData("Lift Encoder:", liftMotor.getCurrentPosition());
    }

    public boolean setLift (double goalPos,  double power) {
        //Input goalPos must be between 0.0 and 1.0

        int currentPos = liftMotor.getCurrentPosition();
        goalPos = Range.clip(goalPos, 0.0, 1.0);
        goalPos = Range.scale(goalPos, 0.0, 1.0, LIFT_MIN, LIFT_MAX);
        int newGoalPos;
        newGoalPos = (int) Math.round(goalPos);
        if(encodersCanRun){
            encodersCanRun = false;
        }
        if(!encodersCanRun){
            if(currentPos > newGoalPos + 20){
                liftMotor.setPower(-power);
                telemetry.addData("Current Lift Value:", currentPos);
                telemetry.addData("Lift Goal Value:", newGoalPos);
                telemetry.addData("Current Difference: ", currentPos - LIFT_MIN);
                telemetry.addData("Goal Difference: ", newGoalPos - LIFT_MIN);
            } else if(currentPos < newGoalPos - 20){
                liftMotor.setPower(power);
                telemetry.addData("Current Lift Value:", currentPos);
                telemetry.addData("Lift Goal Value:", newGoalPos);
                telemetry.addData("Current Difference: ", currentPos - LIFT_MIN);
                telemetry.addData("Goal Difference: ", newGoalPos - LIFT_MIN);
            } else {
                encodersCanRun = true;
                liftMotor.setPower(0.0);
                return encodersCanRun;
            }
        }
        return false;
    }
}
