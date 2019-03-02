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

        //robot.liftSoas();

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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 2.25);
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
                        state = State.SAMPLERIGHT;
                    }
                    break;
                case SAMPLERIGHT:
                    // samplingResult = robot.detectGold();
                    boolean samplingResult = false;
                    if (samplingResult) {
                        state = State.STRAFETOGOLDFROMRIGHT;
                    } else {
                        state = State.STRAFETOCENTER;
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 2.25);
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 10.2);
                    } else {
                        isFinished = false;
                        state = State.END;//TURNLEFT;
                    }
                    break;
                case STRAFETOCENTER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 4.5);
                    } else {
                        isFinished = false;
                        state = State.SAMPLEMID;
                    }
                    break;
                case SAMPLEMID:
                    // boolean samplingResult = robot.detectGold();
                    samplingResult = false;
                    if (samplingResult) {
                        state = State.STRAFETOGOLDFROMCENTER;
                    } else {
                        state = State.STRAFETOLEFT;
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 2.5);
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.25, 5.5);
                    } else {
                        isFinished = false;
                        state = State.END;//TURNLEFT;
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
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.25, 3);
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
                        state = State.END;//TURNLEFT;
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
                    break;

            }
        }
}
