package com.zj.demo.test;

import com.zj.demo.entity.Subject;
import com.zj.demo.entity.Teacher;
import com.zj.demo.mapper.SubjectMapper;
import com.zj.demo.mapper.TeacherMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class SubjectTest {
    
    private static final String[] TYPE={
            "0","1"
    };

    private static final String[] SUBJECTS={
            "Java基础","C语言入门",".Net网站设计","多媒体技术","软件工程"
            ,"高等数学1","线性代数","概率论与数理统计","运筹学",
            "数据挖掘","人工智能","管理学","大学英语","马克思主义",
            "毛泽东思想","高等数学2","大学语文","操作系统","离散数学"
            ,"计算机网络"
    };

    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    @Test
    public void test1(){
        Subject temp;
        for (int i = 0; i < 30; i++) {
            temp = new Subject("SB"+ IDGenerator.getUniqueID(),
                    "SB"+IDGenerator.getID(i+1),
                    TYPE[new Random().nextInt(TYPE.length)],String.valueOf(new Random().nextInt(4)+1));
            subjectMapper.addSubject(temp);
        }
    }
}
