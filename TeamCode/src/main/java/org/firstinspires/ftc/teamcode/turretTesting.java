package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class turretTesting extends OpMode {

    // Instantiate your custom mechanism
    private AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    private turretmechanism turret = new turretmechanism();

    double stepSizes[] = {0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 2;

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, String.valueOf(telemetry));
        turret.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    public void start() {
        turret.resetTimer();
    }

    @Override
    public void loop() {
        aprilTagWebcam.update();
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);
        turret.update(id20);
        // update P and D on the fly
// 'B' button cycles through the different step sizes for tuning precision.
        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length; // Modulo wraps the index back to 0.
        }

// D-pad left/right adjusts the P gain.
        if (gamepad1.dpadLeftWasPressed()) {
            turret.setkP(turret.getkP() - stepSizes[stepIndex]);
        }
        if (gamepad1.dpadRightWasPressed()) {
            turret.setkP(turret.getkP() + stepSizes[stepIndex]);
        }

// D-pad up/down adjusts the D gain.
        if (gamepad1.dpadUpWasPressed()) {
            turret.setkD(turret.getkD() + stepSizes[stepIndex]);
        }
        if (gamepad1.dpadDownWasPressed()) {
            turret.setkD(turret.getkD() - stepSizes[stepIndex]);
        }


        if(id20 != null) {
            telemetry.addData("current ID", aprilTagWebcam);
        } else {
            telemetry.addLine("No tag detected, stopping turret motor");
        }

        telemetry.addData("P value", "%.5f (D-Pad L/R)", turret.getkP());
        telemetry.addData("D value", "%.5f (D-Pad U/D)", turret.getkD());
        telemetry.addData("Step Size", "%.5f (X Button)", stepSizes[stepIndex]);
    }
}