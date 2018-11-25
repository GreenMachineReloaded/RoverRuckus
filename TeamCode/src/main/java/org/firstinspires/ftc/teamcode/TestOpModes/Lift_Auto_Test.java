package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;

@Autonomous(name = "Lift_Auto_Test", group = "test")
public class Lift_Auto_Test extends OpMode {
    private Robot robot;
    private boolean isFinished;
    private int step;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        step = 1;
        isFinished = false;
    }

    @Override
    public void loop() {
        switch(step){
            case 1:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(0.5, 0.25);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
            case 2:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(0.25, 0.25);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
            case 3:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(0.75, 0.25);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
            case 4:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(0.5, 0.25);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
            case 5:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(1.0, 0.25);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
            case 6:
                if(!isFinished){
                    isFinished = robot.robotLift.setLift(0.0, 0.5);
                } else {
                    isFinished = false;
                    step++;
                }
                break;
        }
    }
}
