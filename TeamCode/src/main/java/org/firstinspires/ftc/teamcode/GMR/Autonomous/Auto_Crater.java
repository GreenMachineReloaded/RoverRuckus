package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

/**
 * Created by Arroz on 11/4/2018
 */
@Autonomous (name = "Auto_Crater")
public class Auto_Crater extends OpMode {
    private Robot robot;
/*
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
*/
    private NavxMicroNavigationSensor gyroscope;
    private IntegratingGyroscope gyro;

    private Servo soas;

    private State state;

    private boolean isFinished;

    @Override
    public void init() {
        /*rightFront = hardwareMap.dcMotor.get("rightfront");
        leftFront = hardwareMap.dcMotor.get("leftfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        leftRear = hardwareMap.dcMotor.get("leftrear");

        soas = hardwareMap.servo.get("soas");

        gyroscope = hardwareMap.get (NavxMicroNavigationSensor.class, "navx");*/

        robot = new Robot(hardwareMap, telemetry);

        state = State.DRIVEOUT;
        isFinished = false;
    }

        @Override
        public void loop(){
            switch (state){
                /*case RAISEHOOK:
                    if (!isFinished) {
                        isFinished = robot.robotLift;
                    }*/
                case DRIVEOUT:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.4, 2);
                    } else{
                        isFinished = false;
                        state = State.ROTATE;
                    }
                    break;
                /*case ROTATE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 90);
                    } else {
                        isFinished = false;
                        state = State.DRIVECRATER;
                    }
                    break;
                case DRIVECRATER:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 10);
                    } else{
                        isFinished = false;
                        state = State.DROPSOAS;
                    }
                    break;
                case DROPSOAS:
                    if (!isFinished) {
                        isFinished = false;
                        state = State.END;
                    }
                    break;
                case END:
                    robot.driveTrain.stop();
                    break;
            }*/
        }
}}
