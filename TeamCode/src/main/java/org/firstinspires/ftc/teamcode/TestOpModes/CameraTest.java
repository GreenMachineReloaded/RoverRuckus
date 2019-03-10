package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Robot2;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2.Camera;

import static android.os.SystemClock.sleep;

@TeleOp(name = "CameraTest", group = "test")
public class CameraTest extends OpMode {

    //private Robot2 robot;
    private Camera camera;
    private boolean isPressed;

    @Override
    public void init() {
        //robot = new Robot2(hardwareMap, telemetry);
        camera = new Camera(hardwareMap, telemetry);
        camera.activate();
        isPressed = false;
    }
    @Override
    public void loop() {
        camera.activate();
        camera.getPos();
        telemetry.update();
        }
    @Override
    public void stop() {
        camera.shutdown();
    }
}
