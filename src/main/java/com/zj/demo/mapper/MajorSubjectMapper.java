package com.zj.demo.mapper;

import com.zj.demo.entity.MajorSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MajorSubjectMapper {

    List<MajorSubject> findAllMS();

    void addSubjectsToMajor(MajorSubject majorSubject);

    List<String> findSubjectsByMajorID(String id);

    void deleteSubjectFromMajor(String id);

    void deleteMajorAndSubjectByMajorId(String id);
}
