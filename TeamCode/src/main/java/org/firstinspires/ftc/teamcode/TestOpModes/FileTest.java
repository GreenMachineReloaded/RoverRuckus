package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GMR.Robot2.Subsystems2.Camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Autonomous(name = "test")
public class FileTest extends LinearOpMode {


@Override
public void runOpMode(){
    //Camera camera = new Camera(hardwareMap, telemetry);
    File test = new File("/sdcard/FIRST/test.txt");
    waitForStart();
    try {
        test.createNewFile();
        FileOutputStream out = new FileOutputStream(test);
        PrintStream ps = new PrintStream(out);
        ps.println("testing 123");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
