package org.firstinspires.ftc.teamcode.GMR.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2.Camera;

/**
 * Created by Arroz on 11/4/2018
 */
@Autonomous (name = "Auto_Crater_Sampling")
public class Auto_Crater_Sampling extends OpMode {
    private Robot robot;

    private Camera camera;

    private State state;

    private boolean isFinished;
    private Camera.Mineral samplingResult = Camera.Mineral.UNKNOWN;

    private ElapsedTime time = new ElapsedTime();

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);
        camera = new Camera(hardwareMap, telemetry);
        camera.activate();

        robot.liftSoas();

        robot.robotLift.lock();

        state = State.TIME;
        isFinished = false;
        //robot.robotLift.hold(robot.robotLift.getEncoderPosition());
    }

    @Override
    public void loop(){
        telemetry.addData("State: ", state);
        switch (state) {
            case TIME:
                time.reset();
                state = State.UNLOCK;
                break;
            case UNLOCK:
                robot.robotLift.unlock();
                if (time.seconds() >=0.5) {
                    state = State.RAISEHOOK;
                }
                break;
            case RAISEHOOK:
                if (!isFinished && time.seconds() < 3.0) {
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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1);
                } else {
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;
            case ROTATE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 70);
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
                    state = State.TURNTOBALL;
                }
                break;
            case TURNTOBALL:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 8.5f);
                } else {
                    isFinished = false;
                    state = State.STRAFETOBALL;
                }
                break;
            case STRAFETOBALL:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5, 1.5);
                } else {
                    isFinished = false;
                    time.reset();
                    state = State.SAMPLERIGHT;
                }
                break;
            case SAMPLERIGHT:
                samplingResult = camera.sampleHighest();
                if (samplingResult == Camera.Mineral.SILVER || (time.seconds() >= 2 && samplingResult == Camera.Mineral.UNKNOWN)) {
                    state = State.STRAFETOCENTER;
                } else if (samplingResult == Camera.Mineral.GOLD) {
                    state = State.STRAFETOGOLDFROMRIGHT;
                }
                break;
            case STRAFETOGOLDFROMRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5, 1.5);
                } else {
                    isFinished = false;
                    state = State.KNOCKRIGHT;
                }
                break;
            case KNOCKRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 3);//2.25);
                } else {
                    isFinished = false;
                    state = State.RETURNRIGHT;
                }
                break;
            case RETURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.25, 2);
                } else {
                    isFinished = false;
                    state = State.STRAFELEFTFROMRIGHT;
                }
                break;
            case STRAFELEFTFROMRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 11.2);
                } else {
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case STRAFETOCENTER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 4.5);
                } else {
                    isFinished = false;
                    time.reset();
                    state = State.SAMPLEMID;
                }
                break;
            case SAMPLEMID:
                samplingResult = camera.sampleHighest();
                if (samplingResult == Camera.Mineral.SILVER || (time.seconds() >= 2 && samplingResult == Camera.Mineral.UNKNOWN)) {
                    state = State.STRAFETOLEFT;
                } else if (samplingResult == Camera.Mineral.GOLD) {
                    state = State.KNOCKMID;
                }
                break;
            case STRAFETOGOLDFROMCENTER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5, 1);
                } else {
                    isFinished = false;
                    state = State.KNOCKMID;
                }
                break;
            case KNOCKMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 3);//2.5);
                } else {
                    isFinished = false;
                    state = State.RETURNMID;
                }
                break;
            case RETURNMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.25, 2);
                } else {
                    isFinished = false;
                    state = State.STRAFELEFTFROMMID;
                }
                break;
            case STRAFELEFTFROMMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 6.5);
                } else {
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case STRAFETOLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 5);
                } else {
                    isFinished = false;
                    state = State.KNOCKLEFT;
                }
                break;
            case KNOCKLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 3.5);//3);
                } else {
                    isFinished = false;
                    state = State.RETURNLEFT;
                }
                break;
            case RETURNLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.25, 3);
                } else {
                    isFinished = false;
                    state = State.STRAFETOENDPOINT;
                }
                break;
            case STRAFETOENDPOINT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 1);
                } else {
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case TURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5,145);
                } else {
                    isFinished = false;
                    state = State.ALIGN;
                }
                break;
            case ALIGN:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 4.5);
                } else {
                    isFinished = false;
                    state = State.STRAFETOWARDSDEPOT;
                }
                break;
            case STRAFETOWARDSDEPOT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5,7);
                } else {
                    isFinished = false;
                    state = State.FINALE;//DROPSOAS;//STRAFETOCRATER;
                }
                break;
            case STRAFETOCRATER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5, 14);
                } else  {
                    isFinished = false;
                    state = State.FINALE;
                }
                break;
/*            case TURNLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 45);
                } else {
                    isFinished = false;
                    state = State.STRAFETODEPOSIT;
                }
                break;
            case STRAFETODEPOSIT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 5);
                } else {
                isFinished = false;
                state = State.DROPSOAS;
                }
                break;
                */
            /*case DRIVEFORWARD:
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
                break; */
            case DROPSOAS:
                robot.dropSoas();
                if (time.seconds() >=1) {
                    state = State.RAISESERVO;
                }
                break;
            case RAISESERVO:
                robot.liftSoas();
                state = State.DRIVECRATER;
                break;
            case STRAFECRATER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5, 10);
                } else {
                    isFinished = false;
                    state = State.FINALE;
                }
                break;
/*            case END:
                if (!isFinished) {
                    //~~~~~~~~~~~~~~~~~~~~~TAKE OUT LATER~~~~~~~~~~~~~~~~~~~~~~
                    isFinished = robot.robotLift.setLift(0, 0.25);
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                } else {
                    isFinished = false;
                    state = State.FINALE;
                }
                break;
                */
            case FINALE:
                robot.driveTrain.stop();
                break;

        }
    }
}
