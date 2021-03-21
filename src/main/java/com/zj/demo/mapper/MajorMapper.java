package com.zj.demo.mapper;

import com.zj.demo.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface MajorMapper {

    List<Major> findAllMajors();
    List<Major> findMajorsByKeyword(String keyword);
    Major findMajorById(String id);

    void deleteMajorById(String id);
    void updateMajorById(Major major);
    void addMajor(Major major);

    int getMajorTotalNum();

    void updateMajorStateById(String id,String state);
}
