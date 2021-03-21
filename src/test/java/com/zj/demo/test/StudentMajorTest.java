package com.zj.demo.test;

import com.zj.demo.entity.*;
import com.zj.demo.mapper.*;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class StudentMajorTest {

    @Autowired(required = false)
    private MajorMapper majorMapper;

    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Autowired(required = false)
    private StudentMajorMapper studentMajorMapper;

    @Test
    public void test1(){
        List<Student> students = studentMapper.findAllStudents();
        List<Major> majors = majorMapper.findAllMajors();
        for (int i = 0; i < students.size() ; i++) {
            studentMajorMapper.addMajorToStudent(new StudentMajor("SM"+IDGenerator.getUniqueID(),
                    students.get(i).getId(),
                    majors.get(new Random().nextInt(majors.size())).getId()));
        }
    }
}
