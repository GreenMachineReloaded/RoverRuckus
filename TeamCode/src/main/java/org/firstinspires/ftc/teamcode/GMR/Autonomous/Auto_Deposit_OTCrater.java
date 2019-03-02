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

@Autonomous(name = "Auto_Deposit_OTCrater", group = "Blue")
public class Auto_Deposit_OTCrater extends OpMode {

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

        state = State.RAISEHOOK;
        isFinished = false;
    }

    @Override
    public void loop() {
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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1);
                } else {
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;
            case ROTATE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5,83);
                } else {
                    isFinished = false;
                    state = State.DRIVEMID;
                }
                break;
            case DRIVEMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 2.75);
                } else {
                    isFinished = false;
                    state = State.STRAFECENTERMINERAL;
                }
                break;
            case STRAFECENTERMINERAL:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 2);
                } else {
                    isFinished = false;
                    time.reset();
                    state = State.SAMPLEMID;
                }
                break;
            case SAMPLEMID:
                samplingResult = camera.sampleHighest();
                if (samplingResult == Camera.Mineral.SILVER || (time.seconds() >= 2 && samplingResult == Camera.Mineral.UNKNOWN)) {
                    state = State.STRAFEMINRIGHT;
                } else if (samplingResult == Camera.Mineral.GOLD) {
                    state = State.STRAFETOGOLDFROMCENTER;
                }
                break;
            case STRAFETOGOLDFROMCENTER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.25, 1.5);
                } else {
                    isFinished = false;
                    state = State.KNOCKMID;
                }
                break;
            case KNOCKMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.25,3);
                } else {
                    isFinished = false;
                    state = State.RETURNMID;
                }
                break;
            case RETURNMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.25,3);
                } else {
                    isFinished = false;
                    state = State.DRIVELEFT;
                }
                break;
            case STRAFEMINRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E,0.25,5);
                } else {
                    isFinished = false;
                    time.reset();
                    state = State.SAMPLERIGHT;
                }
                break;
            case SAMPLERIGHT:
                samplingResult = camera.sampleHighest();
                if (samplingResult == Camera.Mineral.SILVER || samplingResult == Camera.Mineral.UNKNOWN) {
                    state = State.STRAFEMINLEFT;
                    } else if (samplingResult == Camera.Mineral.GOLD ){
                    state = State.STRAFETOGOLDFROMRIGHT;
                }
                break;
            case STRAFETOGOLDFROMRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.25, 1.5);
                } else {
                    isFinished = false;
                    state = State.KNOCKRIGHT;
                }
                break;
            case KNOCKRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.25,3);
                } else {
                    isFinished = false;
                    state = State.RETURNRIGHT;
                }
                break;
            case RETURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.25,3);
                } else {
                    isFinished = false;
                    state = State.STRAFECENTERFROMRIGHT;
                }
                break;
            case STRAFECENTERFROMRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E,0.5,3);
                } else {
                    isFinished = false;
                    state = State.DRIVELEFT;
                }
                break;
            case STRAFEMINLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W,0.25,9);
                } else {
                    isFinished = false;
                    state = State.KNOCKLEFT;
                }
                break;
            case KNOCKLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.25,3);
                } else {
                    isFinished = false;
                    state = State.RETURNLEFT;
                }
                break;
            case RETURNLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.25,3);
                } else {
                    isFinished = false;
                    state = State.STRAFECRATERFROMLEFT;
                }
                break;
            case STRAFECRATERFROMLEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E,0.25,4.5);
                } else {
                    isFinished = false;
                    state = State.DRIVELEFT;
                }
                break;




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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.E, 0.5,13);
                } else {
                    time.reset();
                    isFinished = false;
                    state = State.DROPSOAS;
                }
                break;
            case DROPSOAS:
                robot.dropSoas();
                if (time.seconds() >=1){
                    state = State.DRIVEABIT;
                }
                break;
            case DRIVEABIT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1);
                } else {
                    isFinished = false;
                    state = State.RAISESERVO;
                }
                break;
            case RAISESERVO:
                robot.liftSoas();
                state = State.STRAFELEFT;
                break;
            case STRAFELEFT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W,0.5,12);
                } else {
                    isFinished = false;
                    state = State.BACKUP;
                }
                break;
            case BACKUP:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S,0.25,0.5);
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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N,0.5,2);
                } else {
                    isFinished = false;
                    state = State.FINALE;
                }
                break;
            case END:
                if (!isFinished) {
                    //~~~~~~~~~~~~~~~~~~~~~TAKE OUT LATER~~~~~~~~~~~~~~~~~~~~~~
                    isFinished = robot.robotLift.setLift(0, 0.25);
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                } else {
                    isFinished = false;
                    state = State.FINALE;
                }
                break;
            case FINALE:
                robot.driveTrain.stop();
                camera.shutdown();
                break;

        }
        telemetry.update();

    }
}

