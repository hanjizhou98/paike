package com.zj.demo.test;

import com.zj.demo.entity.Admin;
import com.zj.demo.mapper.AdminMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class AdminTest {

    private static final String[] XING = {
            "赵","钱","孙","李","周","吴","郑","王"
            ,"冯","陈","褚","卫","蒋","沈","韩","杨"
            ,"刘","张"
    };
    private static final String[] MING={
            "伟","芳","秀英","娜","敏","静","丽"
            ,"洋","磊","强","军","勇","杰","艳","涛","秀文","博文","星驰"
            ,"志远","雨"
    };
    private static final String[] SEX = {
            "M","F"
    };
    @Autowired(required = false)
    private AdminMapper adminMapper;

    @Test
    public void test1(){
        Admin temp = null;
        for (int i = 0; i < 2; i++) {
            temp = new Admin("AD"+ IDGenerator.getUniqueID(),
                    "AD"+IDGenerator.getID(i+1)
                    , UUID.randomUUID().toString().replaceAll("-","").substring(0,4).toUpperCase(Locale.ROOT)
                    ,SEX[new Random().nextInt(SEX.length)]);
            adminMapper.addAdmin(temp);

        }
    }
}
