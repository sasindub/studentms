package com.tution.studentms.repository;

import com.tution.studentms.dto.StudentDTO;
import com.tution.studentms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT * FROM STUDENT WHERE stu_id LIKE %:searchedValue% OR stu_name LIKE %:searchedValue%",nativeQuery = true)
    List<Student> getStudentBySearch(@Param("searchedValue") String searchedValue);

    @Modifying
    @Query(value = "UPDATE STUDENT SET stu_paid_month = ?1 WHERE stu_id = ?2",nativeQuery = true)
    void updateStudentById(String paidMonth, int stuId);
}
