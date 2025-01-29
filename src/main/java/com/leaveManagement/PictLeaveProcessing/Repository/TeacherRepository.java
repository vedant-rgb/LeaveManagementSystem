package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {
}
