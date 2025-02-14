package com.leaveManagement.PictLeaveProcessing.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacherId", unique = true)
    private String teacherId;

    private String name;
    private String post;
    private String subject;
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id", referencedColumnName = "id")
    private TeacherLeave leave;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TeacherTimetable> timetable;

    public Teacher() {
        this.leave = new TeacherLeave();
    }



}
