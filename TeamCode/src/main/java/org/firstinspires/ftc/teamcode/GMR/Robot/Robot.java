package org.firstinspires.ftc.teamcode.GMR.Robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RobotArm;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RobotLift;

public class Robot {

    public DriveTrain driveTrain;

    public RobotLift robotLift;

    public RobotArm robotArm;

    private DcMotor leftFront;
    private DcMotor leftRear;
    private DcMotor rightFront;
    private DcMotor rightRear;
    private IntegratingGyroscope gyro;

    private DcMotor liftMotor;

    private DcMotor armPulley;
    private DcMotor armHinge;

    private Servo soas;

    public Robot (HardwareMap hardwareMap, Telemetry telemetry){

        leftFront = hardwareMap.dcMotor.get("leftfront");
        leftRear = hardwareMap.dcMotor.get("leftrear");
        rightFront = hardwareMap.dcMotor.get("rightfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        gyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");

        liftMotor = hardwareMap.dcMotor.get("liftmotor");

        armPulley = hardwareMap.dcMotor.get("armpulley");
        armHinge = hardwareMap.dcMotor.get("armhinge");

        soas = hardwareMap.servo.get("soas");

        driveTrain = new DriveTrain(leftFront, rightFront, leftRear, rightRear, gyro, telemetry);


        robotLift = new RobotLift(liftMotor, telemetry);

        robotArm = new RobotArm(armPulley, armHinge, telemetry);
    }

    public void dropSoas() {
        soas.setPosition(0.7);
    }

    public void liftSoas() {
        soas.setPosition(0);
    }

    public void runServo(boolean a, boolean x) {
        if (a) {
            liftSoas();
        } else if (x) {
            dropSoas();
        }
    }


}
