package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class Camera {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AeMOrwX/////AAABmfT7G+QwOkX8tJ01GKNfSHpTu0AM83Vojwk9rAY53xCbB6Xjb0JRAvKsBmLnSyKvi9Ly2CHSxI2CvWwVwUSjdUP20+VyT9ZW2LS+cc6cZQNjA+QiG6XUwyzloO/O1CMhJW+Idn6v6fCwuWQQZqIHeZpm3DO+/XvO+jN3utA1L5RycBsdvoJP3JhEazHENhYJ1mPEN6vICe5AZIAZMImZ2qiNbEk0lLwqequHuuDVkgoTPvIheK9J9Mk9YzirjsVVVM2LG27KKVvxmoxYWwQ+35jWe7ij1+yvJvedinzJLf6DEYJltnV/OfNVKcKsht2tiC8Ihq7MacpCZ40EpwyjceLHgJK8Fq4NHWl87f1jFfhD";

    private VuforiaLocalizer vuforia;
    private VuforiaLocalizer.Parameters parameters;

    private TFObjectDetector tfod;
    private int tfodMonitorViewId;
    private TFObjectDetector.Parameters tfodParameters;


    private Telemetry telemetry;
    public Camera (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        //ADD hardwareMap for camera to Robot \/
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            //ADD hardwareMap for tfodMonitorViewId \/
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        } else {
            telemetry.addData("Device incompatible with TFOD! ", "Please use other devices");
        }
        telemetry.addData("TFOD", " ready");
    }

    public Mineral sampleSingle() {
        if(tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null) {
                telemetry.addData("# Objects detected: ", updatedRecognitions.size());
                if(updatedRecognitions.size() == 1) {
                    Recognition recognition = updatedRecognitions.get(0);
                    if(recognition.getLabel().equals(LABEL_GOLD_MINERAL)){
                        return Mineral.GOLD;
                    } else if(recognition.getLabel().equals(LABEL_SILVER_MINERAL)){
                        return Mineral.SILVER;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void activate() {
        tfod.activate();
    }

    public void deactivate() {
        tfod.deactivate();
    }

    public enum Mineral {
        GOLD,
        SILVER
    }

    public enum position {
        LEFT,
        CENTER,
        RIGHT
    }
}
