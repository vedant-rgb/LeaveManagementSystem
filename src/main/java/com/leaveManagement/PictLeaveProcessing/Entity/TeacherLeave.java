package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class TeacherLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commonLeave;
    private Long medicalLeave;
    private Long earnedLeaves;
    private Long C_off;
    private Long LWP;

    public TeacherLeave() {
        this.commonLeave=10L;
        this.medicalLeave=10L;
        this.earnedLeaves=10L;
        this.C_off=10L;
        this.LWP=10L;
    }
}
