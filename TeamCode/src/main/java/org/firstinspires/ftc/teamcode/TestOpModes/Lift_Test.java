package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Lift Test", group = "test")
public class Lift_Test extends OpMode {
    public DcMotor liftMotor;

    private boolean bumper;
    private float trigger;
    @Override
    public void init() {
        liftMotor = hardwareMap.dcMotor.get("liftmotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        bumper = gamepad1.left_bumper;
        trigger = gamepad1.left_trigger;

        if(bumper){
            if(trigger == 1) {
                liftMotor.setPower(0.0);
            }
            else {
                liftMotor.setPower(-0.5);
            }
        } else {
            if(trigger == 1) {
                liftMotor.setPower(0.5);
            }
            else {
                liftMotor.setPower(0.0);
            }
        }
        telemetry.addData("Encoder Value:", liftMotor.getCurrentPosition());
        telemetry.update();
    }
}
