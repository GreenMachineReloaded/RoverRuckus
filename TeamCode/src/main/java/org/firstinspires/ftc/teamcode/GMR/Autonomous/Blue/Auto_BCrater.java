package org.firstinspires.ftc.teamcode.GMR.Autonomous.Blue;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GMR.Autonomous.States;
import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;

/**
 * Created by FTC 4316 on 11/4/2018
 */
@Autonomous (name = "Auto_BCrater", group = "Blue")
public class Auto_BCrater extends OpMode {
    private Robot robot;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    private NavxMicroNavigationSensor gyroscope;
    private IntegratingGyroscope gyro;

    private Servo soas;

    private States state;

    private boolean isFinished;

    @Override
    public void init() {
        rightFront = hardwareMap.dcMotor.get("rightfront");
        leftFront = hardwareMap.dcMotor.get("leftfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        leftRear = hardwareMap.dcMotor.get("leftrear");

        soas = hardwareMap.servo.get(Servo.class, "soas");

        gyroscope = hardwareMap.get (NavxMicroNavigationSensor.class, "navx");

        robot = new Robot(hardwareMap, telemetry);

        state = States.TIME;
        isFinished = false;
    }

        @Override
        public void loop(){
            switch (state){
                case DRIVE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 2);
                    } else{
                        isFinished = false;
                        state = States.OPEN;
                    }
                case ROTATE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNLEFT, 0.5, 90);
                    } else {
                        isFinished = false;
                        state = States.END;
                    }
                case DRIVE:
                    if (!isFinished) {
                        isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.S, 0.5, 24);
                    } else{
                        isFinished = false;
                        state = States.OPEN;
                    }
                case DROPSOAS:
                    if (!isFinished) {
                        isFinished = false;
                        state = States.OPEN;
                    }

                    break;
            }
        }
}
