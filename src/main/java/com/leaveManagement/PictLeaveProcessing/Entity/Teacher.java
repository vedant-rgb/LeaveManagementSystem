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

}
