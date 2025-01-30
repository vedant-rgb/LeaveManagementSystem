package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "teacherId",unique = true)
    private String teacherId;
    private String name;
    private String post;
    private String subject;
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id")
    private TeacherLeave leave;

    public Teacher() {
        this.leave = new TeacherLeave(this);
    }

    public Teacher(Long id, String teacherId, String name, String post, String subject, String department) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.post = post;
        this.subject = subject;
        this.department = department;
        this.leave = new TeacherLeave(this);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public TeacherLeave getLeave() {
        return leave;
    }

    public void setLeave(TeacherLeave leave) {
        this.leave = leave;
    }
}
