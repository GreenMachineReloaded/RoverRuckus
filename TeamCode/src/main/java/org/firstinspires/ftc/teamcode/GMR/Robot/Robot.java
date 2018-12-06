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

    private DcMotor armMotor;
    private int armEncoder;

    public Robot (HardwareMap hardwareMap, Telemetry telemetry){

        leftFront = hardwareMap.dcMotor.get("leftfront");
        leftRear = hardwareMap.dcMotor.get("leftrear");
        rightFront = hardwareMap.dcMotor.get("rightfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        gyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");

        liftMotor = hardwareMap.dcMotor.get("liftmotor");

        armMotor = hardwareMap.dcMotor.get("armmotor");

        driveTrain = new DriveTrain(leftFront, rightFront, leftRear, rightRear, gyro, telemetry);


        robotLift = new RobotLift(liftMotor, telemetry);
    }

    public void rake(boolean bumper, float trigger){
        if(bumper && trigger != 1){
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotor.setPower(1.0);
        } else if (!bumper && trigger == 1){
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotor.setPower(-1.0);
        } else {
            armEncoder = armMotor.getCurrentPosition();
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setTargetPosition(armEncoder);
        }
    }
}
