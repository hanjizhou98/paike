package com.zj.demo.controller;

import com.zj.demo.entity.*;
import com.zj.demo.mapper.PaikeMapper;
import com.zj.demo.mapper.SubjectMapper;
import com.zj.demo.mapper.TeacherMapper;
import com.zj.demo.mapper.TeacherSubjectMapper;
import com.zj.demo.utils.IDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TeacherController {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private TeacherSubjectMapper teacherSubjectMapper;

    @Resource
    private PaikeMapper paikeMapper;

    @RequestMapping("/teacher_index")
    public String teacher_index(Model model){
        List<Teacher> teacherList = teacherMapper.findAllTeachers();
        model.addAttribute("teacherList",teacherList);
        return "teacher_index";
    }

    @RequestMapping("/to_teacher_add")
    public String to_teacher_add(Model model){
        List<Subject> subjects = subjectMapper.findAllSubjects();
        model.addAttribute("subjects",subjects);
        return "teacher_add";
    }

    @RequestMapping("/teacher_add")
    public String teacher_add(Teacher teacher,String subject){
        teacher.setId("TC"+ IDGenerator.getUniqueID());
        teacherSubjectMapper.addSubjectToTeacher(new TeacherSubject(
                "TS"+IDGenerator.getUniqueID(),
                teacher.getId(),subject
        ));
        teacherMapper.addTeacher(teacher);
        return "redirect:teacher_index";
    }

    @RequestMapping("/to_teacher_update")
    public String to_teacher_update(@RequestParam("id") String id, Model model){
        getTeacher(id, model);
        return "teacher_update";
    }

    /**
     * getTeacher
     * @param id
     * @param model
     */
    private void getTeacher(@RequestParam("id") String id, Model model) {
        Teacher teacher = teacherMapper.findTeacherById(id);
        List<Subject> subjects = subjectMapper.findAllSubjects();
        String subjectID = teacherSubjectMapper.findSubjectIdByTeacherId(id);
        model.addAttribute("subjectID",subjectID);
        model.addAttribute("subjects",subjects);
        model.addAttribute("teacher",teacher);
    }

    @RequestMapping("/teacher_update")
    @Transactional
    public String teacher_update(Teacher teacher,String subject){
        if (teacherSubjectMapper.findTeacherSubjectByTeacherId(teacher.getId())!=null){
            teacherSubjectMapper.updateSubjectOfTeacher(new TeacherSubject(
                    null,teacher.getId(),subject
            ));
        }else{
            teacherSubjectMapper.addSubjectToTeacher(new TeacherSubject(
                    "TS"+IDGenerator.getUniqueID(),
                    teacher.getId(),subject
            ));
        }
        teacherMapper.updateTeacherById(teacher);
        return "redirect:teacher_index";
    }

    @RequestMapping("/to_teacher_delete")
    @Transactional
    public String to_teacher_delete(@RequestParam("id") String id){
        teacherSubjectMapper.deleteTeacherFromSubject(id);
        teacherMapper.deleteTeacherById(id);
        return "redirect:teacher_index";
    }

    @RequestMapping("/teacher_search")
    public String teacher_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Teacher> teacherList = teacherMapper.findTeachersByKeyword(keyword);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",teacherList.size());
        return "teacher_search";
    }

    @RequestMapping("/to_teacher_update_search")
    public String to_teacher_update_search(@RequestParam("id") String id,
                                           @RequestParam("keyword") String keyword,Model model){
        getTeacher(id, model);
        model.addAttribute("keyword",keyword);
        return "teacher_update_search";
    }

    @RequestMapping("/teacher_update_search")
    @Transactional
    public String teacher_update_search(Teacher teacher, String keyword,String subject, RedirectAttributes attributes){
        if (teacherSubjectMapper.findTeacherSubjectByTeacherId(teacher.getId())!=null){
            teacherSubjectMapper.updateSubjectOfTeacher(new TeacherSubject(
                    null,teacher.getId(),subject
            ));
        }else{
            teacherSubjectMapper.addSubjectToTeacher(new TeacherSubject(
                    "TS"+IDGenerator.getUniqueID(),
                    teacher.getId(),subject
            ));
        }
        teacherMapper.updateTeacherById(teacher);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:teacher_search";
    }

    @RequestMapping("/to_teacher_delete_search")
    @Transactional
    public String to_teacher_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        teacherSubjectMapper.deleteTeacherFromSubject(id);
        teacherMapper.deleteTeacherById(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:teacher_search";
    }

    @RequestMapping("/to_teacher_password_update")
    public String to_teacher_password_update(String id,Model model){
        Teacher teacher = teacherMapper.findTeacherById(id);
        model.addAttribute("teacher",teacher);
        return "teacher_password_update";
    }

    /**
     *
     * @param id
     * @param password
     * @return
     */
    @RequestMapping("/teacher_password_update")
    public String teacher_password_update(String id,String password){
        teacherMapper.updatePasswordByTeacherId(id,password);
        return "redirect:index";
    }

}
