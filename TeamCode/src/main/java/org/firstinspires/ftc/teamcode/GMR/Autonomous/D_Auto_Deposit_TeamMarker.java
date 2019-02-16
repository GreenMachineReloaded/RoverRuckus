package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;


/**
 * Created by Arroz on 11/4/2018
 */

@Autonomous(name = "Auto_Deposit_TeamMarker", group = "Blue")
public class D_Auto_Deposit_TeamMarker extends OpMode {

    private Robot robot;

    private State state;

    private boolean isFinished;

    private ElapsedTime time = new ElapsedTime();

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);
        state = State.DELAY;
        isFinished = false;

    }

    @Override
    public void loop(){
        switch (state) {
            case DELAY:
                time.reset();
                if (time.seconds() >=5)
                    state = State.RAISEHOOK;
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
                    state = State.DRIVEMID;
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
            case DRIVEMARKER:
                telemetry.addData("Running DRIVEMARKER", "");
                telemetry.update();
                if (!isFinished) {
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 7);
                } else {
                    telemetry.addData("Finished DRIVEMARKER", "");
                    telemetry.update();
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;
            case TURNRIGHT:
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 70);
                } else {
                    isFinished = false;
                    state = State.DROPSOAS;
                    time.reset();
                }
                break;
            case DROPSOAS:
                robot.dropSoas();
                if (time.seconds() >= 1) {
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
