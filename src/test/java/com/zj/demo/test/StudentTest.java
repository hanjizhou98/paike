package com.zj.demo.test;

import com.zj.demo.entity.Major;
import com.zj.demo.entity.Student;
import com.zj.demo.mapper.MajorMapper;
import com.zj.demo.mapper.StudentMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {

    private static final String[] MAJOR = {
            "计算机科学","软件工程","多媒体技术","法律","美术","会计","信息管理","数学"
            ,"经济管理","英语","小语种","哲学","逻辑学","宗教学","伦理学","经济学",
            "经济统计","国民经济","劳动经济","财政学"
            ,"金融学","保险学","投资学","国际经济"
            ,"法学","知识产权","国际政治","外交学","社会学"
            ,"人类学"
    };
    private static final String[] SEX = {
            "M","F"
    };
    private static final String[] XING = {
            "赵","钱","孙","李","周","吴","郑","王"
            ,"冯","陈","褚","卫","蒋","沈","韩","杨"
            ,"刘","张","后","蓝","吕","文","严","马","徐"
            ,"欧阳","肖","萧","毕","台","华","林","敬"
    };
    private static final String[] MING={
            "伟","芳","秀英","娜","敏","静","丽"
            ,"洋","磊","强","军","勇","杰","艳","涛","秀文","博文","星驰"
            ,"志远","雨","志强","恒久","剑飞","强","清华","严飞","硕","英文",
            "志恒","洁","介良","自远","树人","自强","子阳","思雨","思宇","怀仁"
            ,"署","军军","风眠","啸天","汉卿"
    };
    private static final String[] STATE={
            "0","1"
    };

    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Autowired(required = false)
    private MajorMapper majorMapper;

    @Test
    public void test1(){
        Student student = null;
        for(int i=0;i<100;i++){
            student = new Student("ST"+ IDGenerator.getUniqueID()
                    ,"ST"+IDGenerator.getID(i+1)
                    ,"1111"
                    ,SEX[new Random().nextInt(SEX.length)],new Random().nextInt(15)+15
                    ,STATE[0]);
            studentMapper.addStudent(student);
        }
    }
}
