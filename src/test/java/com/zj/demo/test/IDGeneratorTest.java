package com.zj.demo.test;

import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IDGeneratorTest {

    @Test
    public void test1(){
        for (int i = 0; i < 100; i++) {
            System.out.println((i+1)+"="+IDGenerator.getUniqueID());
        }
    }
}
