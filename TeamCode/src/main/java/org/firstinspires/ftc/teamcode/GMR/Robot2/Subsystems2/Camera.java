package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class Camera {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AeMOrwX/////AAABmfT7G+QwOkX8tJ01GKNfSHpTu0AM83Vojwk9rAY53xCbB6Xjb0JRAvKsBmLnSyKvi9Ly2CHSxI2CvWwVwUSjdUP20+VyT9ZW2LS+cc6cZQNjA+QiG6XUwyzloO/O1CMhJW+Idn6v6fCwuWQQZqIHeZpm3DO+/XvO+jN3utA1L5RycBsdvoJP3JhEazHENhYJ1mPEN6vICe5AZIAZMImZ2qiNbEk0lLwqequHuuDVkgoTPvIheK9J9Mk9YzirjsVVVM2LG27KKVvxmoxYWwQ+35jWe7ij1+yvJvedinzJLf6DEYJltnV/OfNVKcKsht2tiC8Ihq7MacpCZ40EpwyjceLHgJK8Fq4NHWl87f1jFfhD";

    private VuforiaLocalizer vuforia;
    private VuforiaLocalizer.Parameters parameters;

    private TFObjectDetector tfod;
    private int tfodMonitorViewId;

    private WebcamName webcam;

    private Telemetry telemetry;
    public Camera(WebcamName webcam, Telemetry telemetry, int tfodMonitorViewId){
        this.webcam = webcam;
        this.tfodMonitorViewId = tfodMonitorViewId;
        this.telemetry = telemetry;

        parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        //ADD hardwareMap for camera to Robot \/
        //parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            //ADD hardwareMap for tfodMonitorViewId \/
        } else {
            telemetry.addData("Device incompatible with TFOD! ", "Please use other devices");
        }
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
