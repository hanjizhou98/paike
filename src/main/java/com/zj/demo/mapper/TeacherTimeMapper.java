package com.zj.demo.mapper;

import com.zj.demo.entity.TeacherTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface TeacherTimeMapper {

    List<TeacherTime> findAllTeacherTimes();

    List<TeacherTime> findTeacherTimesByTeacherId(String id);

    void addTeacherTime(TeacherTime teacherTime);

    void updateTeacherStateByIdAndTimeNum(String id, String timeNum,String state);
}
