package com.zj.demo.mapper;

import com.zj.demo.entity.TeacherSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherSubjectMapper {

    List<String> findTeacherIdBySubjectId(String id);

    String findSubjectIdByTeacherId(String id);

    TeacherSubject findTeacherSubjectByTeacherId(String id);

    void addSubjectToTeacher(TeacherSubject teacherSubject);

    void updateSubjectOfTeacher(TeacherSubject teacherSubject);

    void deleteTeacherFromSubject(String id);

    void deleteTeacherAndSubjectBySubjectId(String id);

}
