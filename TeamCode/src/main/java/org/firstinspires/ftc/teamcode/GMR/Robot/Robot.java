package org.firstinspires.ftc.teamcode.GMR.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.BlockLift;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.LatchLift;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RelicGrab;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by pston on 11/12/2017
 */

public class Robot {

    public DriveTrain driveTrain;
    public BlockLift blockLift;
    public RelicGrab relicGrab;
    public LatchLift latchLift;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private NavxMicroNavigationSensor gyroReference;
    private IntegratingGyroscope gyro;

    private DcMotor liftMotor;
    private Servo topLeftGrab;
    private Servo topRightGrab;
    private Servo bottomLeftGrab;
    private Servo bottomRightGrab;

    private DcMotor relicLift;
    private Servo slideLift;
    private Servo relicTilt;
    private Servo relicClamp;

    private Servo rightColor;
    private Servo leftColor;


    public Robot (HardwareMap hardwareMap, Telemetry telemetry) {

        gyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        leftFront = hardwareMap.dcMotor.get("leftfront");
        rightFront = hardwareMap.dcMotor.get("rightfront");
        leftRear = hardwareMap.dcMotor.get("leftrear");
        rightRear = hardwareMap.dcMotor.get("rightrear");

        liftMotor = hardwareMap.dcMotor.get("liftmotor");

        driveTrain = new DriveTrain(leftFront, rightFront, leftRear, rightRear, gyro, telemetry);

        latchLift = new LatchLift(liftMotor, telemetry);
    }
}
