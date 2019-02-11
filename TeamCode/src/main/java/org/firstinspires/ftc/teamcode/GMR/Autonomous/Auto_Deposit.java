package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;



/**
 * Created by Arroz on 11/4/2018
 */

@Autonomous(name = "Auto_Deposit", group = "Blue")
public class Auto_Deposit extends OpMode {

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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.3, 1);
                } else {
                    isFinished = false;
                    state = State.STRAFE;
                }
                break;
            case STRAFE:
                if (isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.W, 0.5, 1);
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
            case DRIVEMID:
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 2);
                } else {
                    isFinished = false;
                    state = State.DRIVEMARKER;
                }
                break;
                /* THIS IS FOR AUTOCRATER
                //String samplingResult = robot.camera.detectGold();
                String samplingResult = "---";
                if (samplingResult.equals("left") {
                    // robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 35);
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3.5);
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3.5);
                    // robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 35);
                  } else if (samplingResult.equals("center") {
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3.5);
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3.5);
                    // robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 70);????
                  } else if (samplingResult.equals("right") {
                    // robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 35);
                    // robot.driveTrain.encodeDrive(DriveTrain.Direction.N, 0.5, 3.5);
                    // robot.driveTrain.encodeDrive(DriveTrain.Direction.S, 0.5, 3.5);
                  } else if (samplingResult.equals("null") {
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 3.5);
                    // robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 3.5);
                    // robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 70);?????
                  }
                 */
            case DRIVEMARKER:
                telemetry.addData("Running DRIVEMARKER", "");
                telemetry.update();
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 7);
                } else {
                    telemetry.addData("Finished DRIVEMARKER","");
                    telemetry.update();
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case TURNRIGHT:
                if (!isFinished){
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5,70);
                } else {
                    isFinished = false;
                    state = State.DROPSOAS;
                    time.reset();
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
                    state = State.END;
                break;
            case ROTATEBOT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 45);
                } else {
                    isFinished = false;
                    state = State.DRIVEFORWARD;
                }
                break;
            case DRIVEFORWARD:
                if (!isFinished){
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 17);
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
