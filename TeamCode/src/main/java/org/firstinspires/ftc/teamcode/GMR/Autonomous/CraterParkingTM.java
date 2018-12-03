package org.firstinspires.ftc.teamcode.GMR.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GMR.Robot.Robot;
import org.firstinspires.ftc.teamcode.GMR.Robot.SubSystems.DriveTrain;


@Autonomous(name="Crater Parking TM")
public class CraterParkingTM extends OpMode
{
    private Robot robot;

    private Servo armServo;

    private States state;

    private boolean isFinished;

    @Override public void init () {
        robot = new Robot(hardwareMap, telemetry);
        armServo = hardwareMap.servo.get("soas");
        state = States.ROTATE;
        isFinished = false;

    }
      @Override public void loop(){
        switch(state) {
            case ROTATE:
                // do something
                if (!isFinished) {
                    isFinished = robot.driveTrain.gyroTurn(DriveTrain.Direction.TURNRIGHT, 0.5, 135);
                }
                // at the end, make sure to change the value of state
                else {
                    isFinished = false;
                    state = States.DRIVEFORWARD;
                }

                break;

            case DRIVEFORWARD:
                // do something
                if (!isFinished){
                    isFinished = robot.driveTrain.encoderDrive(DriveTrain.Direction.N, 0.5, 60);
                }
                else{
                    isFinished = false;
                    state = States.TURNBOX;

                }

                break;

            case LOWERARM:
                // do something
                if (!isFinished){
                    armServo.setPosition(0);
                }
                else{
                    isFinished = false;
                    state = States.END;
                }

        }
      }
}



