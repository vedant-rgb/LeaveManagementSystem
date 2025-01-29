package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LeaveAvailability")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "leave")
    private Teacher teacherId;

    private Long commonLeave;

    private Long medicalLeave ;

    private Long earnedLeaves;

    private Long C_off;

    private Long LWP;

    public Long getId() {
        return id;
    }

    public Long getCommonLeave() {
        return commonLeave;
    }

    public Long getMedicalLeave() {
        return medicalLeave;
    }

    public Long getEarnedLeaves() {
        return earnedLeaves;
    }

    public Long getC_off() {
        return C_off;
    }

    public Long getLWP() {
        return LWP;
    }

    public Teacher getTeacherId() {
        return teacherId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }

    public void setCommonLeave(Long commonLeave) {
        this.commonLeave = commonLeave;
    }

    public void setMedicalLeave(Long medicalLeave) {
        this.medicalLeave = medicalLeave;
    }

    public void setEarnedLeaves(Long earnedLeaves) {
        this.earnedLeaves = earnedLeaves;
    }

    public void setC_off(Long c_off) {
        C_off = c_off;
    }

    public void setLWP(Long LWP) {
        this.LWP = LWP;
    }
}
