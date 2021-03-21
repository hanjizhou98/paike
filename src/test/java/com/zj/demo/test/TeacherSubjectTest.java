package com.zj.demo.test;

import com.zj.demo.entity.Teacher;
import com.zj.demo.entity.Subject;
import com.zj.demo.entity.TeacherSubject;
import com.zj.demo.mapper.TeacherMapper;
import com.zj.demo.mapper.TeacherSubjectMapper;
import com.zj.demo.mapper.SubjectMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class TeacherSubjectTest {

    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    @Autowired(required = false)
    private TeacherSubjectMapper teacherSubjectMapper;

    @Test
    public void test1(){
        List<Subject> subjects = subjectMapper.findAllSubjects();
        List<Teacher> teachers = teacherMapper.findAllTeachers();
        for (int i = 0; i < teachers.size() ; i++) {
            TeacherSubject teacherSubject = new TeacherSubject("TS"+IDGenerator.getUniqueID(),
                    teachers.get(i).getId(),
                    subjects.get(i).getId());
            teacherSubjectMapper.addSubjectToTeacher(teacherSubject);
        }
    }
}
