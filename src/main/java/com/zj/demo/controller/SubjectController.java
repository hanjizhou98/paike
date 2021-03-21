package com.zj.demo.controller;

import com.zj.demo.entity.Subject;
import com.zj.demo.entity.Teacher;
import com.zj.demo.mapper.*;
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
import java.util.List;

@Controller
public class SubjectController {

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private TeacherSubjectMapper teacherSubjectMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private MajorSubjectMapper majorSubjectMapper;

    @Resource
    private PaikeMapper paikeMapper;

    @RequestMapping("/subject_index")
    public String subject_index(Model model){
        List<Subject> subjectList = subjectMapper.findAllSubjects();
        model.addAttribute("subjectList",subjectList);
        return "subject_index";
    }

    @RequestMapping("/to_subject_add")
    public String to_subject_add(){
        return "subject_add";
    }

    @RequestMapping("/subject_add")
    @Transactional
    public String subject_add(Subject subject){
        subject.setId("SB"+IDGenerator.getUniqueID());
        subjectMapper.addSubject(subject);
        return "redirect:subject_index";
    }

    @RequestMapping("/to_subject_update")
    public String to_subject_update(@RequestParam("id") String id, Model model){
        Subject subject = subjectMapper.findSubjectById(id);
        model.addAttribute("subject",subject);
        return "subject_update";
    }

    @RequestMapping("/subject_update")
    @Transactional
    public String subject_update(Subject subject){
        subjectMapper.updateSubjectById(subject);
        return "redirect:subject_index";
    }

    @RequestMapping("/to_subject_delete")
    @Transactional
    public String to_subject_delete(@RequestParam("id") String id){
        majorSubjectMapper.deleteSubjectFromMajor(id);
        teacherSubjectMapper.deleteTeacherAndSubjectBySubjectId(id);
        paikeMapper.deletePaikesBySubjectId(id);
        subjectMapper.deleteSubjectById(id);
        return "redirect:subject_index";
    }

    @RequestMapping("/subject_search")
    public String subject_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Subject> subjectList = subjectMapper.findSubjectsByKeyword(keyword);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",subjectList.size());
        return "subject_search";
    }

    @RequestMapping("/to_subject_update_search")
    public String to_subject_update_search(@RequestParam("id") String id,
                                           @RequestParam("keyword") String keyword,Model model){
        Subject subject = subjectMapper.findSubjectById(id);
        model.addAttribute("subject",subject);
        model.addAttribute("keyword",keyword);
        return "subject_update_search";
    }

    @RequestMapping("/subject_update_search")
    @Transactional
    public String subject_update_search(Subject subject, String keyword, RedirectAttributes attributes){
        subjectMapper.updateSubjectById(subject);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:subject_search";
    }

    @RequestMapping("/to_subject_delete_search")
    @Transactional
    public String to_subject_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        majorSubjectMapper.deleteSubjectFromMajor(id);
        teacherSubjectMapper.deleteTeacherAndSubjectBySubjectId(id);
        paikeMapper.deletePaikesBySubjectId(id);
        subjectMapper.deleteSubjectById(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:subject_search";
    }

    @RequestMapping("/to_subject_detail")
    public String to_subject_detail(String id,Model model){
        System.out.println(id);
        Subject subject = subjectMapper.findSubjectById(id);
        List<String> teachers = teacherSubjectMapper.findTeacherIdBySubjectId(id);
        List<Teacher> teacherList = new ArrayList<>();
        for (String tid :
                teachers) {
            teacherList.add(teacherMapper.findTeacherById(tid));
        }
        model.addAttribute("subject",subject);
        model.addAttribute("teacherList",teacherList);
        return "subject_detail";
    }


}
