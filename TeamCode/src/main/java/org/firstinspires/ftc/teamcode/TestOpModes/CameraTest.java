package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2.Camera;

import static android.os.SystemClock.sleep;

@TeleOp(name = "CameraTest", group = "test")
public class CameraTest extends OpMode {

    private Camera camera;
    private Camera.Mineral mineral;
    private boolean isPressed;

    @Override
    public void init() {
        camera = new Camera(hardwareMap, telemetry);
        camera.activate();
        isPressed = false;
    }
    @Override
    public void loop() {
        if(gamepad1.a && !isPressed){
            //camera.recordData();
            isPressed = true;
        } else if(isPressed){
            isPressed = false;
        }
        }
    @Override
    public void stop() {
        camera.shutdown();
    }
}
