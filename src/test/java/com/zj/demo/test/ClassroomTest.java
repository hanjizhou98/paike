package com.zj.demo.test;

import com.zj.demo.entity.Classroom;
import com.zj.demo.mapper.ClassroomMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class ClassroomTest {

    private static final String[] STATE={
            "1","0"
    };

    @Autowired(required = false)
    private ClassroomMapper classroomMapper;

    @Test
    public void test1(){
        Classroom temp = null;
        for (int i = 0; i < 10; i++) {
            temp = new Classroom("CR"+ IDGenerator.getUniqueID(),
                    "CNO"+IDGenerator.getID(i+1)
                    ,STATE[new Random().nextInt(STATE.length)]);
            classroomMapper.addClassroom(temp);
        }
    }
}
