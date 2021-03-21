package com.zj.demo.mapper;

import com.zj.demo.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassroomMapper {

    List<Classroom> findAllClassrooms();
    List<Classroom> findClassroomsByKeyword(String keyword);
    List<Classroom> findClassroomsByStudentId(String id);
    List<Classroom> findClassroomsByTeacherId(String id);
    List<Classroom> findClassroomsBySubjectId(String id);
    List<Classroom> findClassroomsByMajorId(String id);
    Classroom findClassroomById(String id);

    void deleteClassroomById(String id);
    void updateClassroomById(Classroom classroom);
    void addClassroom(Classroom classroom);

    int getClassroomTotalNum();
}
