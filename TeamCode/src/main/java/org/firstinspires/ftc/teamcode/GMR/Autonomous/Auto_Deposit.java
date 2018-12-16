package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

import javax.xml.transform.dom.DOMResult;

import static org.firstinspires.ftc.teamcode.GMR.Autonomous.State.END;
import static org.firstinspires.ftc.teamcode.GMR.Autonomous.State.RAISESOAS;


/**
 * Created by Arroz on 11/4/2018
 */

@Autonomous(name = "Auto_Deposit", group = "Blue")
public class Auto_Deposit extends OpMode {
    private Robot robot;
    /*
        private DcMotor leftFront;
        private DcMotor rightFront;
        private DcMotor leftRear;
        private DcMotor rightRear;

        private NavxMicroNavigationSensor gyroscope; */
    private IntegratingGyroscope gyro;

    private Servo soas;

    private State state;

    private boolean isFinished;

    @Override
    public void init() {
       /* rightFront = hardwareMap.dcMotor.get("rightfront");
        leftFront = hardwareMap.dcMotor.get("leftfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        leftRear = hardwareMap.dcMotor.get("leftrear");*/

        soas = hardwareMap.servo.get("soas");
        soas.setPosition(0);

        //gyroscope = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");

        robot = new Robot(hardwareMap, telemetry);


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
            /*case LOWERHOOK:
                if (!isFinished) {
                    isFinished = robot.robotLift.setLift(0, 0.25);
                }   else{
                    isFinished = false;
                    state = State.ROTATE;
                }
                break;*/
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
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 9);
                } else {
                    telemetry.addData("Finished DRIVEMARKER","");
                    telemetry.update();
                    isFinished = false;
                    state = State.TURNRIGHT;
                }
                break;

                case TURNRIGHT:
                if (!isFinished){
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, .5,70);
                } else {
                    isFinished = false;
                    state = State.DROPSOAS;
                }
                break;


            case DROPSOAS:
                if (!isFinished) {
                    soas.setPosition(0.50);
                } else {
                    isFinished = false;
                    state = State.DROPSERVO;
                }

            case RAISESERVO:
                if (!isFinished) {
                    soas.setPosition(0);
                } else {
                    isFinished = false;
                    state = State.ROTATEBOT;
                }

            case ROTATEBOT:
                // do something
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 45);
                }
                // at the end, make sure to change the value of state
                else {
                    isFinished = false;
                    state = State.DRIVEFORWARD;
                }

                break;

            case DRIVEFORWARD:
                // do something
                if (!isFinished){
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 50);
                }
                else{
                    isFinished = false;
                    state = State.LOWERARM;
                }

                break;

            case LOWERARM:
                // do something
                if (!isFinished){
                    soas.setPosition(0);
                }
                else {
                    isFinished = false;
                    state = RAISESOAS;
                }
            case RAISESOAS:
                if (!isFinished){
                    soas.setPosition(0.50);
                }
                else {
                    isFinished = false;
                    state = END;
                }

                break;

             case END:
                robot.driveTrain.stop();
                break;
        }

    }
}
