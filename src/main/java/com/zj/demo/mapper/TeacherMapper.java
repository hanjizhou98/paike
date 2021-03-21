package com.zj.demo.mapper;


import com.zj.demo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {

    List<Teacher> findAllTeachers();
    List<Teacher> findTeachersByKeyword(String keyword);
    List<Teacher> findTeachersBySubject(String subject);
    Teacher findTeacherById(String id);

    void deleteTeacherById(String id);
    void updateTeacherById(Teacher teacher);
    void addTeacher(Teacher teacher);

    int getTeacherTotalNum();

    Teacher findTeacherByNameAndPassword(String name, String password);
}
