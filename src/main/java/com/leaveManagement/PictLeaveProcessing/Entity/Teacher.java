package com.leaveManagement.PictLeaveProcessing.Entity;

import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "Teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherId;

    private String name;

    private String post;

    private String subject;

    private String department;

    @OneToOne
    @JoinColumn(name = "teacherId_leave_mapping")
    private Leave leave;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getSubject() {
        return subject;
    }

    public String getDepartment() {
        return department;
    }

    public Leave getLeave() {
        return leave;
    }

    public String getTeacherId() {return teacherId;}

    public void setTeacherId(String teacherId) {this.teacherId = teacherId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(post, teacher.post) && Objects.equals(subject, teacher.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, post, subject);
    }
}