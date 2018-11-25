package org.firstinspires.ftc.teamcode.GMR.Robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RobotLift;

public class Robot {

    public DriveTrain driveTrain;

    public RobotLift robotLift;


    private DcMotor leftFront;
    private DcMotor leftRear;
    private DcMotor rightFront;
    private DcMotor rightRear;
    private IntegratingGyroscope gyro;

    private DcMotor liftMotor;

    public Robot (HardwareMap hardwareMap, Telemetry telemetry){

        leftFront = hardwareMap.dcMotor.get("leftfront");
        leftRear = hardwareMap.dcMotor.get("leftrear");
        rightFront = hardwareMap.dcMotor.get("rightfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        gyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");

        liftMotor = hardwareMap.dcMotor.get("liftmotor");



        driveTrain = new DriveTrain(leftFront, rightFront, leftRear, rightRear, gyro, telemetry);

        robotLift = new RobotLift(liftMotor, telemetry);
        
    }
}
