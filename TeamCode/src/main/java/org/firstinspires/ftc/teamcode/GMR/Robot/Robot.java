package org.firstinspires.ftc.teamcode.GMR.Robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RobotArm;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.RobotLift;

import java.util.List;

public class Robot {

    //private static final String VUFORIA_KEY = "AeMOrwX/////AAABmfT7G+QwOkX8tJ01GKNfSHpTu0AM83Vojwk9rAY53xCbB6Xjb0JRAvKsBmLnSyKvi9Ly2CHSxI2CvWwVwUSjdUP20+VyT9ZW2LS+cc6cZQNjA+QiG6XUwyzloO/O1CMhJW+Idn6v6fCwuWQQZqIHeZpm3DO+/XvO+jN3utA1L5RycBsdvoJP3JhEazHENhYJ1mPEN6vICe5AZIAZMImZ2qiNbEk0lLwqequHuuDVkgoTPvIheK9J9Mk9YzirjsVVVM2LG27KKVvxmoxYWwQ+35jWe7ij1+yvJvedinzJLf6DEYJltnV/OfNVKcKsht2tiC8Ihq7MacpCZ40EpwyjceLHgJK8Fq4NHWl87f1jFfhD";

    public DriveTrain driveTrain;

    public RobotLift robotLift;

    public RobotArm robotArm;

    private DcMotor leftFront;
    private DcMotor leftRear;
    private DcMotor rightFront;
    private DcMotor rightRear;
    private IntegratingGyroscope gyro;

    private DcMotor liftMotor;
    private Servo lockServo;

    private DcMotor armPulley;
    private DcMotor armHinge;

    private Servo soas;
    private CRServo collector;

    private Telemetry telemetry;

    /*private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";*/


    public Robot (HardwareMap hardwareMap, Telemetry telemetry){

        leftFront = hardwareMap.dcMotor.get("leftfront");
        leftRear = hardwareMap.dcMotor.get("leftrear");
        rightFront = hardwareMap.dcMotor.get("rightfront");
        rightRear = hardwareMap.dcMotor.get("rightrear");
        gyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");

        liftMotor = hardwareMap.dcMotor.get("liftmotor");

        armPulley = hardwareMap.dcMotor.get("armpulley");
        // armHinge = hardwareMap.dcMotor.get("armhinge");

        soas = hardwareMap.servo.get("soas");
        lockServo = hardwareMap.servo.get("lock");
        // collector = hardwareMap.crservo.get("collector");

        driveTrain = new DriveTrain(leftFront, rightFront, leftRear, rightRear, gyro, telemetry);


        robotLift = new RobotLift(liftMotor, lockServo, telemetry);

        robotArm = new RobotArm(armPulley, armHinge, collector, telemetry);
        this.telemetry = telemetry;

        /*VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);*/
    }

    /*public String sample() {
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            if (updatedRecognitions.size() == 3) {
                int goldMineralX = -1;
                int silverMineral1X = -1;
                int silverMineral2X = -1;
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                        goldMineralX = (int) recognition.getLeft();
                    } else if (silverMineral1X == -1) {
                        silverMineral1X = (int) recognition.getLeft();
                    } else {
                        silverMineral2X = (int) recognition.getLeft();
                    }
                }
                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                    if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                        return "Left";
                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                        return "Right";
                    } else {
                        return "Center";
                    }
                }
            }
        }
        return null;
    }*/

    public void dropSoas() {
        soas.setPosition(0.7);
    }

    public void liftSoas() {
        soas.setPosition(0.1);
    }

    public void runServo(boolean a, boolean x) {
        if (a) {
            soas.setPosition(0.7);
        } else if (x) {
            soas.setPosition(0.1);
        }
    }


}
