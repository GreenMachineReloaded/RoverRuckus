package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Color Sensor Sampling Test")
public class Color_Sensor_Sampling_Test extends OpMode{
    private ColorSensor color;
    private DistanceSensor distance;

    private double dist;

    @Override
    public void init() {
        color = hardwareMap.get(ColorSensor.class, "colorDistance");
        distance = hardwareMap.get(DistanceSensor.class, "colorDistance");

        dist = 3.0;
    }
    @Override
    public void loop() {

        telemetry.addData("Red:", color.red());
        telemetry.addData("Blue:", color.blue());
        telemetry.addData("Green:", color.green());
        telemetry.addData("Distance:", distance.getDistance(DistanceUnit.INCH));
        if(distance.getDistance(DistanceUnit.INCH) <= dist + 0.1 && distance.getDistance(DistanceUnit.INCH) >= dist - 0.1){
            if(color.red() < 215 && color.blue() < 215 && color.green() < 175){
                telemetry.addData("Mineral:", "Gold");
            }
            else {
                telemetry.addData("Mineral:", "Silver");
            }
        }
        telemetry.update();
    }
}
