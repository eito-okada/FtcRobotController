package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "movement testing", group = "Main")
public class jan25 extends OpMode {

    // Drive motors
    DcMotor fl, bl, fr, br;

    // Mechanisms
    DcMotor intake;
    DcMotor shooter1;
    DcMotor shooter2;

    // Shooter toggle
    boolean shooterOn = false;
    boolean lastShooterBtn = false;

    static final double DEADZONE = 0.05;

    @Override
    public void init() {

        // Drive motors
        fl = hardwareMap.get(DcMotor.class, "motor0");
        bl = hardwareMap.get(DcMotor.class, "motor1");
        fr = hardwareMap.get(DcMotor.class, "motor2");
        br = hardwareMap.get(DcMotor.class, "motor3");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Mechanism motors
        intake = hardwareMap.get(DcMotor.class, "motor4");
        shooter1 = hardwareMap.get(DcMotor.class, "motor5");
        shooter2 = hardwareMap.get(DcMotor.class, "motor6");

        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        // Shooter coasts
        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop() {

        // =========================
        // DRIVE (MECANUM)
        // =========================
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        if (Math.abs(y) < DEADZONE) y = 0;
        if (Math.abs(x) < DEADZONE) x = 0;
        if (Math.abs(r) < DEADZONE) r = 0;

        double flPow = y + x + r;
        double frPow = y - x - r;
        double blPow = y - x + r;
        double brPow = y + x - r;

        double max = Math.max(1.0,
                Math.max(Math.abs(flPow),
                        Math.max(Math.abs(frPow),
                                Math.max(Math.abs(blPow), Math.abs(brPow)))));

        fl.setPower(flPow / max);
        fr.setPower(frPow / max);
        bl.setPower(blPow / max);
        br.setPower(brPow / max);

// =========================
// MECHANISMS
// =========================

        boolean reverse = gamepad1.left_bumper; // L1
        double dir = reverse ? -1.0 : 1.0;

        // Intake
        if (gamepad1.x) { // Square
            intake.setPower(dir);
        } else {
            intake.setPower(0);
        }


        // Shooter
        boolean shooterBtn = gamepad1.right_bumper; // R1
        if (shooterBtn && !lastShooterBtn)
            shooterOn = !shooterOn;
        lastShooterBtn = shooterBtn;

        shooter1.setPower(shooterOn ? 1.0 : 0.0);
        shooter2.setPower(shooterOn ? 1.0 : 0.0);

    }
}
