package com.zj.demo.test;

import com.zj.demo.entity.Classroom;
import com.zj.demo.entity.ClassroomTime;
import com.zj.demo.mapper.ClassroomMapper;
import com.zj.demo.mapper.ClassroomTimeMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class ClassroomTimeTest {

    @Autowired(required = false)
    private ClassroomTimeMapper classroomTimeMapper;

    @Autowired(required = false)
    private ClassroomMapper classroomMapper;

    @Test
    public void test1(){
        List<Classroom> classroomList = classroomMapper.findAllClassrooms();
        for (Classroom t :
                classroomList) {
            for (int i = 0; i < 77; i++) {
                ClassroomTime classroomTime = new ClassroomTime(
                        "CT"+IDGenerator.getUniqueID(),
                        t.getId(),""+(i+1),"0");
                classroomTimeMapper.addClassroomTime(classroomTime);
            }
        }
    }
}
