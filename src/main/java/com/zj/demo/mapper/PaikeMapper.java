package com.zj.demo.mapper;

import com.zj.demo.entity.Paike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaikeMapper {

    List<Paike> findPaikesByMajorId(String id);

    void addPaike(Paike paike);

    void deletePaikesByMajorId(String id);

    void deletePaikesByClassroomId(String id);

    void deletePaikesBySubjectId(String id);

    List<String> findTeacherIdByMajorIdAndSubjectId(String id, String sid);

}
