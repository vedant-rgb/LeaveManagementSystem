package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;

//@Entity
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nature;

    private String reason;

    @OneToOne(mappedBy = "id")
    private Teacher teacherId;

}