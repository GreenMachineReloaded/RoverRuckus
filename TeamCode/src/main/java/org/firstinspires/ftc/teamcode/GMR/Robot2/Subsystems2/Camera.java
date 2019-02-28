package org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2;

import android.graphics.Bitmap;
import android.graphics.YuvImage;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.State;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.tfod.AnnotatedYuvRgbFrame;
import org.firstinspires.ftc.robotcore.internal.tfod.VuforiaFrameGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

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

    private File cameraDataTextDirectory;
    private File cameraDataText;
    private File cameraDataCaptureDirectory;
    private File cameraDataCapture;
    private FileOutputStream fileOutput;
    private PrintStream ps;

    private AnnotatedYuvRgbFrame annotatedFrame;
    private VuforiaFrameGenerator frameGenerator;
    private BlockingQueue queue;
    private VuforiaLocalizer.CloseableFrame frame;
    private State state;
    public Image rgb;


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

        frameGenerator = new VuforiaFrameGenerator(vuforia, 0);
    }

    public Mineral sampleSingle() {
        //CHANGE TO BOOLEAN RETURN
        if(tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null) {
                telemetry.addData("# Objects detected: ", updatedRecognitions.size());
                //telemetry.update();
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

    /*public void recordData(){
        try {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            cameraDataText = addFile("/sdcard/FIRST/CameraData/TextData/", ".txt");
            cameraDataCapture = addFile("/sdcard/FIRST/CameraData/Captures/", ".png");
            fileOutput = new FileOutputStream(cameraDataText);
            ps = new PrintStream(fileOutput);
            if(updatedRecognitions != null){
                State state = new State();
                frame = new VuforiaLocalizer.CloseableFrame(state.getFrame());
                long num = frame.getNumImages();
                for(int i = 0; i<num; i++){
                    if(frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565){
                        rgb = frame.getImage(i);
                    }
                }
                int recognitionCount = 0;
                for (Recognition recognition : updatedRecognitions){
                    recognitionCount++;
                    String mineral = "";
                    if(recognition.getLabel() == LABEL_GOLD_MINERAL){
                        mineral = recognitionCount + ") Gold";
                    } else if(recognition.getLabel() == LABEL_SILVER_MINERAL){
                        mineral = recognitionCount + ") Silver";
                    }
                    ps.println(mineral + ", " + recognition.getConfidence());
                }
                ps.close();
                fileOutput = new FileOutputStream(cameraDataCapture);
                Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(rgb.getPixels());
                //Bitmap copiedBitmap = bm.copy(bm.getConfig(), true);
                bm.compress(Bitmap.CompressFormat.JPEG, 85, fileOutput);
                fileOutput.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File addFile(String directoryPath, String type) throws IOException {
        File directory = new File(directoryPath);
        int fileNumber = directory.listFiles().length;
        String filePath = directoryPath+"Capture"+fileNumber+type;
        File file = new File(filePath);
        file.createNewFile();
        return file;
    }*/

    public void activate() {
        tfod.activate();
    }

    public void deactivate() {
        tfod.deactivate();
    }

    public void shutdown() {
        tfod.shutdown();
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
