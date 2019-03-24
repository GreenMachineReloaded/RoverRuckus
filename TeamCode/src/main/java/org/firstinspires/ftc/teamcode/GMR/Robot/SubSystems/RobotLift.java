package org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift {

    private DcMotor liftMotor;
    private Servo lockServo;

    private Telemetry telemetry;

    private boolean encodersCanRun;

    private boolean autoLift;

    private final int LIFT_MIN;
    private final int LIFT_MAX;

    private final double LOCK;
    private final double UNLOCK;

    private boolean isPressed;
    private boolean lockState;

    public RobotLift(DcMotor liftMotor, Servo lockServo, Telemetry telemetry){
        this.liftMotor = liftMotor;
        this.lockServo = lockServo;
        this.telemetry = telemetry;

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(0);

        this.encodersCanRun = true;

        LIFT_MIN = liftMotor.getCurrentPosition();
        LIFT_MAX = LIFT_MIN - 3380;

        LOCK = 0.23;
        UNLOCK = 0.35;

        isPressed = false;
        lockState = true;

        telemetry.addData("LIFT_MIN", LIFT_MIN);
        telemetry.addData("LIFT_MAX", LIFT_MAX);

        autoLift = false;
    }

    //TODO: FIX AUTO LIFT
    public void lift (boolean bumper, float trigger, boolean y, boolean a) {
        //CONTROLS: Bumper extends lift, trigger retracts lift

        int goalPos = 0;
        if(bumper){
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if(trigger == 1) {
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(-0.5);
                autoLift = false;
            }
        } else if(trigger == 1) {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if(bumper){
                liftMotor.setPower(0);
            } else {
                liftMotor.setPower(0.5);
                autoLift = false;
            }
        } else if(y){
            goalPos = LIFT_MAX;
            autoLift = true;
        } else if(a) {
            goalPos = LIFT_MIN;
            autoLift = true;
        } else {
            if(autoLift){
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(goalPos == LIFT_MAX){
                    liftMotor.setPower(-0.25);
                } else if(goalPos == LIFT_MIN){
                    liftMotor.setPower(0.25);
                }
                liftMotor.setTargetPosition(goalPos);
            } else {
                liftMotor.setPower(0.0);
            }
        }
        telemetry.addData("Lift Encoder:", liftMotor.getCurrentPosition());
    }

    public void stop(){
        liftMotor.setPower(0.00);
    }

    public void hold(int holdPosition){
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(-1);
        liftMotor.setTargetPosition(holdPosition);
    }

    public void lockButton(boolean button){
        if(!isPressed && button){
            lockState = !lockState;
            isPressed = true;
        } else if(!button){
            isPressed = false;
        }
        if(lockState){
            lockServo.setPosition(LOCK);
        }
        else if(!lockState){
            lockServo.setPosition(UNLOCK);
        }
    }

    public void lock(){
        lockServo.setPosition(LOCK);
    }

    public void unlock(){
        lockServo.setPosition(UNLOCK);
    }

    public boolean setLift (double goalPos,  double power) {
        //Input goalPos must be between 0.0 and 1.0
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentPos = liftMotor.getCurrentPosition();
        goalPos = Range.clip(goalPos, 0.0, 1.0);
        //goalPos = 1.0 - goalPos;
        goalPos = Range.scale(goalPos, 0.0, 1.0, LIFT_MIN, LIFT_MAX);

        int newGoalPos;
        newGoalPos = (int) Math.round(goalPos);
        if(encodersCanRun){
            encodersCanRun = false;
        }
        if(!encodersCanRun){
            if(currentPos > newGoalPos + 20){
                liftMotor.setPower(power);
                telemetry.addData("Current Lift Value:", currentPos);
                telemetry.addData("Lift Goal Value:", newGoalPos);
                telemetry.addData("Current Difference: ", currentPos - LIFT_MIN);
                telemetry.addData("Goal Difference: ", newGoalPos - LIFT_MIN);
            } else if(currentPos < newGoalPos - 20){
                liftMotor.setPower(-power);
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

    public int getEncoderPosition() {
        return liftMotor.getCurrentPosition();
    }
}
