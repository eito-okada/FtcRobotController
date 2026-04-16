package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;
    private List<AprilTagDetection> detectedTags = new ArrayList<>(); // Init with empty list for safety

    public void init(HardwareMap hwMap, String cameraName) {
        aprilTagProcessor = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, cameraName))
                .addProcessor(aprilTagProcessor)
                .build();
    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    // Added from your image: Finds a specific tag by its ID number
    public AprilTagDetection getTagBySpecificId(int id) {
        for (AprilTagDetection detection : detectedTags) {
            if (detection.id == id) {
                return detection;
            }
        }
        return null;
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }
}