package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_leave")
public class TeacherLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commonLeave = 10L;
    private Long medicalLeave = 10L;
    private Long earnedLeaves = 10L;
    private Long C_off = 10L;
    private Long LWP = 10L;

    @OneToOne(mappedBy = "leave")
    private Teacher teacher;

    public TeacherLeave() {}

    public TeacherLeave(Teacher teacher) {
        this.teacher = teacher;
    }

    // Method to take leave
    public boolean takeLeave(String leaveType) {
        switch (leaveType) {
            case "commonLeave":
                if (commonLeave > 0) {
                    commonLeave--;
                    return true;
                }
                break;
            case "medicalLeave":
                if (medicalLeave > 0) {
                    medicalLeave--;
                    return true;
                }
                break;
            case "earnedLeaves":
                if (earnedLeaves > 0) {
                    earnedLeaves--;
                    return true;
                }
                break;
            case "C_off":
                if (C_off > 0) {
                    C_off--;
                    return true;
                }
                break;
            case "LWP":
                if (LWP > 0) {
                    LWP--;
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
