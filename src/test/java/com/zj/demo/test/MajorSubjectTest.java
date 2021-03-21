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
        for (int i = 0; i < majors.size() ; i++) {
            for(int j=0;j<11;j++){
                majorSubjectMapper.addSubjectsToMajor(new MajorSubject("MS"+IDGenerator.getUniqueID(),
                        majors.get(i).getId(),
                        subjects.get(i*11+j).getId()));
            }
        }
    }
}
