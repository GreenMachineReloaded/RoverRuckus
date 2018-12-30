package org.firstinspires.ftc.teamcode.GMR.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

/**
 * Created by Arroz on 11/4/2018
 */
@Autonomous (name = "Auto_Crater")
public class Auto_Crater extends OpMode {
    private Robot robot;

    private State state;

    private boolean isFinished;

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);

        state = State.RAISEHOOK;
        isFinished = false;
    }

        @Override
        public void loop(){
            switch (state){
                case RAISEHOOK:
                    if (!isFinished) {
                        isFinished = robot.robotLift.setLift(1,0.25);
                    }   else{
                        isFinished = false;
                        state = State.DRIVEOUT;
                    }
                    break;
                case DRIVEOUT:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.3, 2);
                    } else{
                        isFinished = false;
                        state = State.ROTATE;
                    }
                    break;
                case ROTATE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 90);
                    } else {
                        isFinished = false;
                        state = State.DRIVECRATER;
                    }
                    break;
                case DRIVECRATER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 8);
                    } else{
                        isFinished = false;
                        state = State.END;
                    }
                    break;
                case END:
                    robot.driveTrain.stop();
                    break;
            }
        }
}
