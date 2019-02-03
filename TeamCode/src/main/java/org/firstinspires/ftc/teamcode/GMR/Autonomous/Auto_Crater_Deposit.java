package org.firstinspires.ftc.teamcode.GMR.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

/**
 * Created by Arroz on 11/4/2018
 */
@Autonomous (name = "Auto_Crater_Deposit")
public class Auto_Crater_Deposit extends OpMode {
    private Robot robot;

    private State state;

    private boolean isFinished;

    private ElapsedTime time = new ElapsedTime();

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);

        state = State.RAISEHOOK;
        isFinished = false;
    }

    @Override
    public void loop(){
        switch (state) {
            case RAISEHOOK:
                if (!isFinished) {
                    isFinished = robot.robotLift.setLift(1, 0.25);
                } else {
                    isFinished = false;
                    state = State.DRIVEOUT;
                }
                break;
            case DRIVEOUT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.3, 2);
                } else {
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;
            case ROTATE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 90);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;
            case DRIVEMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 2);
                } else {
                    isFinished = false;
                    state = State.TURNLEFT;
                }
                break;
            case TURNLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 70);
                } else {
                    isFinished = false;
                    state = State.DRIVEFORWARD;
                }
                break;
            case DRIVEFORWARD:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 8.5);
                } else {
                    isFinished = false;
                    state = State.ROTATELEFT;
                }
                break;
            case ROTATELEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 40);
                } else {
                    isFinished = false;
                    state = State.DRIVEMARKER;
                }
                break;
            case DRIVEMARKER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 14);
                } else {
                    isFinished = false;
                    state = State.DROPSOAS;
                    time.reset();
                }
                break;
            case ROTATERIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 90);
                } else {
                    isFinished = false;
                    state = State.DROPSOAS;
                }
                break;
            case DROPSOAS:
                robot.dropSoas();
                if (time.seconds() >=1) {
                    state = State.RAISESERVO;
                }
                break;
            case RAISESERVO:
                robot.liftSoas();
                state = State.END;
                break;
            case END:
                robot.driveTrain.stop();
                break;

        }
    }
}
