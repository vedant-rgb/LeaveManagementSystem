package com.leaveManagement.PictLeaveProcessing.DTO;

public class TeacherLeaveDTO {
    private Long commonLeave;
    private Long medicalLeave;
    private Long earnedLeaves;
    private Long C_off;
    private Long LWP;

    public TeacherLeaveDTO() {
        this.commonLeave = 10L;
        this.medicalLeave = 10L;
        this.earnedLeaves = 10L;
        this.C_off = 10L;
        this.LWP = 10L;
    }

    public TeacherLeaveDTO(Long commonLeave, Long medicalLeave, Long earnedLeaves, Long C_off, Long LWP) {
        this.commonLeave = commonLeave;
        this.medicalLeave = medicalLeave;
        this.earnedLeaves = earnedLeaves;
        this.C_off = C_off;
        this.LWP = LWP;
    }

    // Getters and Setters
    public Long getCommonLeave() {
        return commonLeave;
    }

    public void setCommonLeave(Long commonLeave) {
        this.commonLeave = commonLeave;
    }

    public Long getMedicalLeave() {
        return medicalLeave;
    }

    public void setMedicalLeave(Long medicalLeave) {
        this.medicalLeave = medicalLeave;
    }

    public Long getEarnedLeaves() {
        return earnedLeaves;
    }

    public void setEarnedLeaves(Long earnedLeaves) {
        this.earnedLeaves = earnedLeaves;
    }

    public Long getC_off() {
        return C_off;
    }

    public void setC_off(Long c_off) {
        C_off = c_off;
    }

    public Long getLWP() {
        return LWP;
    }

    public void setLWP(Long LWP) {
        this.LWP = LWP;
    }
}
