package com.zj.demo.mapper;

import com.zj.demo.entity.StudentMajor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface StudentMajorMapper {

    List<String> findStudentsByMajorID(String id);

    String findMajorIdByStudentId(String id);

    void updateMajorOfStudent(StudentMajor studentMajor);

    void addMajorToStudent(StudentMajor studentMajor);

    void deleteStudentFromMajor(String id);

    void deleteMajorAndStudentByMajorId(String id);
}
