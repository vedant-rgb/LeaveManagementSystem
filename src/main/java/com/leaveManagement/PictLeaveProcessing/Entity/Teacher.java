package com.leaveManagement.PictLeaveProcessing.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "Teacher")
@Getter
@Setter
@AllArgsConstructor
public class Teacher {

    @Id
    private String teacherRegistrationId;

    private String name;
    private String post;
    private String subject;
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id", referencedColumnName = "id")
    private TeacherLeave leave;

    public Teacher() {
        this.leave = new TeacherLeave();
    }

    public Teacher(String teacherRegistrationId, String name, String post, String subject, String department) {
        this.teacherRegistrationId = teacherRegistrationId;
        this.name = name;
        this.post = post;
        this.subject = subject;
        this.department = department;
        this.leave = new TeacherLeave();
    }
}
