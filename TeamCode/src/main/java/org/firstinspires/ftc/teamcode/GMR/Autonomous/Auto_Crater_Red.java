package org.firstinspires.ftc.teamcode.GMR.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

/**
 * Created by Arroz on 11/4/2018
 */
@Autonomous (name = "Auto_Crater_Red")
public class Auto_Crater_Red extends OpMode {
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
            telemetry.addData("State: ", state);
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.3, 1);
                    } else {
                        isFinished = false;
                        state = State.STRAFE;
                    }
                    break;
                case STRAFE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1.5);
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 1);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;
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
                        //isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.SE, 0.5, 3.5);
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 45);
                    } else {
                        isFinished = false;
                        state = State.LEFTFORWARD;
                        //state = State.RETURNLEFT;
                    }
                    break;

                case KNOCKCENTER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.RETURNCENTER;
                    }
                    break;

                case KNOCKRIGHT:
                    if (!isFinished) {
                        //isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.SW, 0.5, 3.5);
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 45);
                    } else {
                        isFinished = false;
                        state = State.RIGHTFORWARD;
                        //state = State.RETURNRIGHT;
                    }
                    break;
                case LEFTFORWARD:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.LEFTBACKWARDS;
                    }
                    break;
                case RIGHTFORWARD:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.RIGHTBACKWARDS;
                    }
                    break;
                case LEFTBACKWARDS:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.LEFTROTATION;
                    }
                    break;
                case RIGHTBACKWARDS:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.RIGHTROTATION;
                    }
                    break;
                case LEFTROTATION:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 45);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;
                case RIGHTROTATION:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 45);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;
                /*case RETURNLEFT:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.NW, 0.5, 3.5);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;

                /*case RETURNCENTER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;
*//*
                case RETURNRIGHT:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.NE, 0.5, 3.5);
                    } else {
                        isFinished = false;
                        state = State.TURNLEFT;
                    }
                    break;
*/

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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 5.8);
                    } else {
                        isFinished = false;
                        state = State.ROTATELEFT;
                    }
                    break;
                case ROTATELEFT:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 50);
                    } else {
                        isFinished = false;
                        state = State.DRIVEMARKER;
                    }
                    break;
                case DRIVEMARKER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 16);
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
                case TURNRIGHT:
                    break;
                case RAISESERVO:
                    robot.liftSoas();
                    state = State.DRIVECRATER;
                    break;
                case DRIVECRATER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 20);
                    } else {
                        isFinished = false;
                        state = State.END;
                    }
                    break;
                case END:
                    robot.driveTrain.stop();
                    break;

                case ROTATEBOT:
                    break;
                case LOWERARM:
                    break;
                case ROTATECRATER:
                    break;
            }
        }
}
