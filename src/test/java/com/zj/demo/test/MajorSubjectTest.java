package com.zj.demo.test;

import com.zj.demo.entity.Major;
import com.zj.demo.entity.MajorSubject;
import com.zj.demo.entity.Subject;
import com.zj.demo.mapper.MajorMapper;
import com.zj.demo.mapper.MajorSubjectMapper;
import com.zj.demo.mapper.SubjectMapper;
import com.zj.demo.utils.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class MajorSubjectTest {

    @Autowired(required = false)
    private MajorMapper majorMapper;

    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    @Autowired(required = false)
    private MajorSubjectMapper majorSubjectMapper;

    @Test
    public void test1(){
        List<Subject> subjects = subjectMapper.findAllSubjects();
        List<Major> majors = majorMapper.findAllMajors();
        int idx = 0;
        for (int i = 0; i < majors.size() ; i++) {
            int temp = new Random().nextInt(3)+7;
            for(int j=0;j<temp;j++){
                if (idx>=subjects.size())break;
                else
                majorSubjectMapper.addSubjectsToMajor(new MajorSubject("MS"+IDGenerator.getUniqueID(),
                        majors.get(i).getId(),
                        subjects.get(idx++).getId()));
            }
        }
    }
}
