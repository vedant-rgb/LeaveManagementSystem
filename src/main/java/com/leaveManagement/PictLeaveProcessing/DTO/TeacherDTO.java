package com.leaveManagement.PictLeaveProcessing.DTO;

public class TeacherDTO {

    private Long id;
    private String teacherId;
    private String name;
    private String post;
    private String subject;
    private String department;
    private TeacherLeaveDTO leave;

    public TeacherDTO() {
    }

    public TeacherDTO(Long id, String teacherId, String name, String post, String subject, String department, TeacherLeaveDTO leave) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.post = post;
        this.subject = subject;
        this.department = department;
        this.leave = leave;
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

    public TeacherLeaveDTO getLeave() {
        return leave;
    }

    public void setLeave(TeacherLeaveDTO leave) {
        this.leave = leave;
    }
}
