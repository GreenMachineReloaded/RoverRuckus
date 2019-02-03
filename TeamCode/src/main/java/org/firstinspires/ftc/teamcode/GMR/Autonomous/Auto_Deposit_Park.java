package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;



/**
 * Created by Arroz on 11/4/2018
 */

@Autonomous(name = "Auto_Deposit_Park", group = "Blue")
public class Auto_Deposit_Park extends OpMode {

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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 2);
                } else {
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;
            case ROTATE:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 65);
                } else {
                    isFinished = false;
                    state = State.DRIVEMARKER;
                }
                break;
            case DRIVEMARKER:
                telemetry.addData("Running DRIVEMARKER", "");
                telemetry.update();
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 4.5);
                } else {
                    telemetry.addData("Finished DRIVEMARKER", "");
                    telemetry.update();
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case TURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 95);
                } else {
                    isFinished = false;
                    state = State.DRIVEFORWARD;
                    time.reset();
                }
                break;
            case DRIVEFORWARD:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 7);
                } else {
                    isFinished = false;
                    state = State.ROTATERIGHT;
                }
                break;
            case ROTATERIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 35);
                } else {
                    isFinished = false;
                    state = State.DRIVECRATER;
                }
                break;
            case DRIVECRATER:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 7


                    );
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


