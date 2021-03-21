package com.zj.demo.test;

import com.zj.demo.entity.Teacher;
import com.zj.demo.entity.TeacherTime;
import com.zj.demo.mapper.TeacherMapper;
import com.zj.demo.mapper.TeacherTimeMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class TeacherTimeTest {

    @Autowired(required = false)
    private TeacherTimeMapper teacherTimeMapper;
    
    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    @Test
    public void test1(){
        List<Teacher> teacherList = teacherMapper.findAllTeachers();
        for (Teacher t :
                teacherList) {
            for (int i = 0; i < 77; i++) {
                TeacherTime teacherTime = new TeacherTime(
                        "TT"+IDGenerator.getUniqueID(),
                        t.getId(),""+(i+1),"0");
                teacherTimeMapper.addTeacherTime(teacherTime);
            }
        }
    }
}
