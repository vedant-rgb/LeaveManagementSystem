package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.Entity.MedicalLeave;
import com.leaveManagement.PictLeaveProcessing.Service.CloudinaryService;
import com.leaveManagement.PictLeaveProcessing.Repository.MedicalLeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/medical-leave")
public class MedicalLeaveController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MedicalLeaveRepository medicalLeaveRepository;

    @PostMapping("/apply")
    public String applyForMedicalLeave(
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("employeeName") String employeeName,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("reason") String reason) {

        // Validate file size (optional)
        long maxFileSize = 10 * 1024 * 1024; // 10 MB
        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("File size exceeds the maximum allowed limit of 10 MB");
        }

        // Upload the file to Cloudinary
        String fileUri = cloudinaryService.uploadFile(file);

        // Calculate the number of days
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        int numberOfDays = calculateNumberOfDays(start, end);

        // Create and save the MedicalLeave entity
        MedicalLeave medicalLeave = new MedicalLeave();
        medicalLeave.setEmployeeId(employeeId);
        medicalLeave.setEmployeeName(employeeName);
        medicalLeave.setStartDate(start);
        medicalLeave.setEndDate(end);
        medicalLeave.setNumberOfDays(numberOfDays);
        medicalLeave.setReason(reason);
        medicalLeave.setMedicalCertificateUri(fileUri); // Save the Cloudinary URI
        medicalLeave.setApplicationDate(LocalDate.now()); // Set the application date to the current date

        medicalLeaveRepository.save(medicalLeave);

        return "Medical leave application submitted successfully!";
    }

    // Helper method to calculate the number of days between two dates
    private int calculateNumberOfDays(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.until(endDate).getDays() + 1; // Inclusive of both start and end dates
    }

}