package com.zj.demo.mapper;

import com.zj.demo.entity.ClassroomTime;
import com.zj.demo.entity.TeacherTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassroomTimeMapper {

    List<TeacherTime> findAllTeacherTimes();

    List<ClassroomTime> findClassroomTimesByTimeNum(String timeNum);

    void addClassroomTime(ClassroomTime classroomTime);

    void updateClassroomStateByIdAndTimeNum(String id, String timeNum,String state);

    void deleteClassroomTimeByClassroomId(String id);
}
