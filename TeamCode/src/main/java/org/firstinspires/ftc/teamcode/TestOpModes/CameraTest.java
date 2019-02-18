package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2.Camera;

import static android.os.SystemClock.sleep;

@TeleOp(name = "CameraTest", group = "test")
public class CameraTest extends OpMode {

    private Camera camera;

    @Override
    public void init() {
        camera = new Camera(hardwareMap, telemetry);
        camera.activate();
    }
    @Override
    public void loop() {
        if(camera.sampleSingle() == Camera.Mineral.GOLD) {
            telemetry.addData("Found mineral: ", "Gold");
        } else if(camera.sampleSingle() == Camera.Mineral.SILVER) {
            telemetry.addData("Found mineral: ", "Silver");
        } else if(camera.sampleSingle() == null) {
            telemetry.addData("Found mineral: ", "Unknown");
        }
        telemetry.update();
    }
    @Override
    public void stop() {
        camera.shutdown();
    }
}
