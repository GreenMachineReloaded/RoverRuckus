package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;



/**
 * Created by Arroz on 11/4/2018
 */

@Autonomous(name = "Auto_Deposit_OTCrater", group = "Blue")
public class Auto_Deposit_OTCrater extends OpMode {

    private Robot robot;

    private State state;

    private boolean isFinished;

    private ElapsedTime time = new ElapsedTime();

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);

        robot.liftSoas();

        state = State.RAISEHOOK;
        isFinished = false;
    }

    @Override
    public void loop() {
        telemetry.addData("State: ", state);
        switch (state) {
            case RAISEHOOK:
                if (!isFinished) {
                    isFinished = robot.robotLift.setLift(.986, 0.25);
                } else {
                    isFinished = false;
                    state = State.DRIVEOUT;
                }
                break;
            case DRIVEOUT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.3, 1);
                } else {
                    isFinished = false;
                    state = State.STRAFE;
                }
                break;
            case STRAFE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1);
                } else {
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;
            case ROTATE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5,85);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;
            case DRIVEMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 2.25);
                } else {
                    isFinished = false;
                    state = State.SAMPLEMID;
                }
                break;
            case SAMPLING:
                //String samplingResult = robot.detectGold();
                String samplingResult = "left";
                if (samplingResult.equals("left")) {
                    state = State.STRAFEMINLEFT;
                } else if (samplingResult.equals("center")) {
                    state = State.SAMPLEMID;
                } else if (samplingResult.equals("right")) {
                    state = State.STRAFEMINRIGHT;
                } else if (samplingResult.equals("null")) {
                    state = State.KNOCKCENTER;
                }
                break;
            case SAMPLEMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.5,1);
                } else {
                    isFinished = false;
                    state = State.KNOCKMID;
                }
                break;
            case KNOCKMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.5,3);
                } else {
                    isFinished = false;
                    state = State.RETURNMID;
                }
                break;
            case RETURNMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.05,4);
                } else {
                    isFinished = false;
                    state = State.DRIVELEFT;
                }
                break;
            case STRAFEMINRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E,0.5,3);
                } else {
                    isFinished = false;
                    state = State.SAMPLEMINRIGHT;
                }
                    /*case SAMPLING:
                //String samplingResult = robot.detectGold();
                String samplingResult = "left";
                if (samplingResult.equals("left")) {
                    state = State.KNOCKLEFT;
                } else if (samplingResult.equals("center")) {
                    state = State.KNOCKCENTER;
                } else if (samplingResult.equals("right")) {
                    state = State.KNOCKRIGHT;
                } else if (samplingResult.equals("null")) {
                    state = State.KNOCKCENTER;
                }
                break;
            case KNOCKLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.NW, 0.5, 3.5);
                } else {
                    isFinished = false;
                    state = State.RETURNLEFT;
                }
                break;
            case KNOCKCENTER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3);
                } else {
                    isFinished = false;
                    state = State.RETURNCENTER;
                }
                break;
            case KNOCKRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.NE, 0.5, 3.5);
                } else {
                    isFinished = false;
                    state = State.RETURNRIGHT;
                }
                break;
            case RETURNLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.SE, 0.5, 3.5);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;
            case RETURNCENTER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;
            case RETURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.SW, 0.5, 3.5);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;*/
            case DRIVELEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W,0.5, 10 );
                } else {
                    isFinished = false;
                    state = State.ROTATELEFT;
                }
                break;

            case ROTATELEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT,0.5,35);
                } else {
                    isFinished = false;
                    state = State.DRIVETOWALL;
                }
                break;
            case DRIVETOWALL:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.25,4);
                } else {
                    isFinished = false;
                    state = State.DRIVEDEPOT;
                }
                break;
            case DRIVEDEPOT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5,9);
                    time.reset();
                } else {
                    if (time.seconds() >=1) {
                        isFinished = false;
                        state = State.STRAFELEFT;
                    }

                }
                break;
            case DROPSOAS:
                robot.dropSoas();
                if (time.seconds() >=1){
                    state = State.RAISESERVO;
                }
                break;
            case RAISESERVO:
                robot.liftSoas();
                state = State.STRAFELEFT;
                break;
            case STRAFELEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W,0.5,9);
                } else {
                    isFinished = false;
                    state = State.BACKUP;
                }
                break;
            case BACKUP:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.5,1);
                } else {
                    isFinished = false;
                    state = State.ROTATELEFTTOCRATER;
                }
                break;
            case ROTATELEFTTOCRATER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT,0.5,80);
                } else {
                    isFinished = false;
                    state = State.DRIVECRATER;
                }
                break;
            case DRIVECRATER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.5,5);
                } else {
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

