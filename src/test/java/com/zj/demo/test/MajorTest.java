package com.zj.demo.test;

import com.zj.demo.entity.Major;
import com.zj.demo.mapper.MajorMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Random;

@SpringBootTest
public class MajorTest {

    private static final String[] STATE = {
            "0", "1"
    };

    private static final String[] MAJOR = {
            "计算机科学", "软件工程", "多媒体技术", "法律", "美术", "会计", "信息管理", "数学"
            , "经济管理", "英语", "小语种", "哲学", "逻辑学", "宗教学", "伦理学", "经济学",
            "经济统计", "国民经济", "劳动经济", "财政学"
            , "金融学", "保险学", "投资学", "国际经济"
            , "法学", "知识产权", "国际政治", "外交学", "社会学"
            , "人类学"
    };

    @Autowired(required = false)
    private MajorMapper majorMapper;

    @Test
    public void test1() {
        Major temp = null;
        for (int i = 0; i < 3; i++) {
            temp = new Major("MJ" + IDGenerator.getUniqueID(),
                    "MJ"+IDGenerator.getID(i+1), STATE[new Random().nextInt(STATE.length)]);
            majorMapper.addMajor(temp);
        }
    }

}