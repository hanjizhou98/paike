package com.zj.demo.controller;

import com.zj.demo.entity.Admin;
import com.zj.demo.entity.Student;
import com.zj.demo.entity.Teacher;
import com.zj.demo.mapper.AdminMapper;
import com.zj.demo.mapper.StudentMapper;
import com.zj.demo.mapper.TeacherMapper;
import com.zj.demo.utils.IDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class LoginController {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;

    @RequestMapping("/to_login")
    public String to_login(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String name, String password, Model model, Integer login_type, HttpSession session){
        System.out.println(name+" "+password+" "+login_type);
        if (login_type==0){//student
            Student student = studentMapper.findStudentByNameAndPassword(name,password);
            if (student!=null){
                session.setAttribute("user",student);
                session.setAttribute("type",0);
                return "redirect:index";
            }else{
                model.addAttribute("msg","用户名或密码错误");
                return "login";
            }
        }else if (login_type==1){//teacher
            Teacher teacher = teacherMapper.findTeacherByNameAndPassword(name,password);
            if (teacher!=null){
                session.setAttribute("user",teacher);
                session.setAttribute("type",1);
                return "redirect:index";
            }else{
                model.addAttribute("msg","用户名或密码错误");
                return "login";
            }
        }else{//admin
            Admin admin = adminMapper.findAdminByNameAndPassword(name,password);
            if (admin!=null){
                session.setAttribute("user",admin);
                session.setAttribute("type",2);
                return "redirect:index";
            }else{
                model.addAttribute("msg","用户名或密码错误");
                return "login";
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:index";
    }

    @RequestMapping("/to_register")
    public String to_register(){
        return "register";
    }

    @RequestMapping("/profile")
    public String profile(String id,int type,Model model) {
        if (type==0){
            Student student = studentMapper.findStudentById(id);
            model.addAttribute("user",student);
        }else if (type==1){
            Teacher teacher = teacherMapper.findTeacherById(id);
            model.addAttribute("user",teacher);
        }else{
            Admin admin = adminMapper.findAdminById(id);
            model.addAttribute("user",admin);
        }
        return "profile";
    }

    @RequestMapping("/admin_register_add")
    public String admin_register_add(Admin admin, HttpSession session){
        admin.setId("AD"+ IDGenerator.getUniqueID());
        admin.setPassword(admin.getPassword().toUpperCase(Locale.ROOT));
        adminMapper.addAdmin(admin);
        session.setAttribute("admin",admin);
        return "redirect:index";
    }

    @RequestMapping(value = {"/index","/"})
    public String index(){
        return "index";
    }
}
