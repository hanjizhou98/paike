package com.zj.demo.mapper;

import com.zj.demo.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubjectMapper {

    List<Subject> findAllSubjects();
    List<Subject> findSubjectsByKeyword(String keyword);
    List<Subject> findSubjectsByStudentId(String id);
    List<Subject> findSubjectsByMajorId(String id);
    Subject findSubjectById(String id);
    Subject findSubjectByTeacherId(String id);

    void deleteSubjectById(String id);
    void updateSubjectById(Subject subject);
    void addSubject(Subject subject);

    int getSubjectTotalNum();
}
