package com.zj.demo.mapper;

import com.zj.demo.entity.Major;
import com.zj.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    List<Student> findAllStudents();
    List<Student> findStudentsByKeyword(String keyword);
    List<Student> findStudentsByMajorID(String id);
    Student findStudentById(String id);
    Student findStudentByNameAndPassword(String name,String password);
    int getStudentsTotalNum();

    void deleteStudentById(String id);
    void updateStudentById(Student student);
    void addStudent(Student student);

}
