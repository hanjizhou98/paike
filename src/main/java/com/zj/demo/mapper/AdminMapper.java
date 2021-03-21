package com.zj.demo.mapper;

import com.zj.demo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    List<Admin> findAllAdmins();
    List<Admin> findAdminsByKeyword(String keyword);
    Admin findAdminById(String id);

    void deleteAdminById(String id);
    void updateAdminById(Admin admin);
    void addAdmin(Admin admin);

    Admin findAdminByNameAndPassword(String name, String password);
}
