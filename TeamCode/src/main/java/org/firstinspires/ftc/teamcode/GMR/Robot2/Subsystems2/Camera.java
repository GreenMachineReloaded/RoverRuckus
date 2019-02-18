package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
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

    private WebcamName webcam;
    public Camera(WebcamName webcam){
        this.webcam = webcam;
        parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }
}
