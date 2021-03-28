package com.zj.demo.controller;

import com.zj.demo.entity.Major;
import com.zj.demo.entity.Paike;
import com.zj.demo.entity.Student;
import com.zj.demo.entity.StudentMajor;
import com.zj.demo.mapper.MajorMapper;
import com.zj.demo.mapper.PaikeMapper;
import com.zj.demo.mapper.StudentMajorMapper;
import com.zj.demo.mapper.StudentMapper;
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
public class StudentController {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private StudentMajorMapper studentMajorMapper;

    @Resource
    private PaikeMapper paikeMapper;


    @RequestMapping("/student_index")
    public String findAllStudents(Model model){
        List<Student> studentList = studentMapper.findAllStudents();
        model.addAttribute("studentList",studentList);
        return "student_index";
    }
    
    @RequestMapping("/to_student_add")
    public String to_student_add(Model model){
        List<Major> majors = majorMapper.findAllMajors();
        model.addAttribute("majors",majors);
        return "student_add";
    }

    @RequestMapping("/student_add")
    @Transactional
    public String student_add(Student student,String major){
        student.setId("ST"+IDGenerator.getUniqueID());
        studentMajorMapper.addMajorToStudent(new StudentMajor(
                "SM" + IDGenerator.getUniqueID(),
                student.getId(), major
        ));
        student.setState("0");
        studentMapper.addStudent(student);
        return "redirect:student_index";
    }

    @RequestMapping("/to_student_update")
    public String to_student_update(@RequestParam("id") String id,Model model){
        Student student = studentMapper.findStudentById(id);
        List<Major> majors = majorMapper.findAllMajors();
        String majorID = studentMajorMapper.findMajorIdByStudentId(id);
        model.addAttribute("majorID",majorID);
        model.addAttribute("majors",majors);
        model.addAttribute("student",student);
        return "student_update";
    }

    @RequestMapping("/student_update")
    @Transactional
    public String student_update(Student student,String major){
        studentMapper.updateStudentById(student);
        studentMajorMapper.updateMajorOfStudent(new StudentMajor(
                null,student.getId(),major
        ));
        return "redirect:student_index";
    }

    @RequestMapping("/to_student_delete")
    @Transactional
    public String to_student_delete(@RequestParam("id") String id){
        studentMajorMapper.deleteStudentFromMajor(id);
        studentMapper.deleteStudentById(id);
        return "redirect:student_index";
    }

    @RequestMapping("/student_search")
    public String student_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Student> studentList = studentMapper.findStudentsByKeyword(keyword);
        model.addAttribute("studentList",studentList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",studentList.size());
        return "student_search";
    }

    @RequestMapping("/to_student_update_search")
    public String to_student_update_search(@RequestParam("id") String id,
                                           @RequestParam("keyword") String keyword,Model model){
        Student student = studentMapper.findStudentById(id);
        List<Major> majors = majorMapper.findAllMajors();
        String majorID = studentMajorMapper.findMajorIdByStudentId(id);
        model.addAttribute("majorID",majorID);
        model.addAttribute("majors",majors);
        model.addAttribute("student",student);
        model.addAttribute("keyword",keyword);
        return "student_update_search";
    }

    @RequestMapping("/student_update_search")
    @Transactional
    public String student_update_search(Student student, String keyword,String major, RedirectAttributes attributes){
        studentMapper.updateStudentById(student);
        studentMajorMapper.updateMajorOfStudent(new StudentMajor(
                null,student.getId(),major
        ));
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:student_search";
    }

    @RequestMapping("/to_student_delete_search")
    @Transactional
    public String to_student_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        studentMajorMapper.deleteStudentFromMajor(id);
        studentMapper.deleteStudentById(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:student_search";
    }

    @RequestMapping("/to_student_kebiao")
    public String to_student_kebiao(String id,Model model){
        String majorid = studentMajorMapper.findMajorIdByStudentId(id);
        /**
         * 当前班级所有课程都排好后，根据班级id获取
         * 班级的全部课表信息，传递到前段展示即可
         */
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(majorid);
        int temp =0;
        for (Paike paike :
                paikes) {
            if (paike.getClassroomId()==null|paike.getSubjectId()==null|paike.getTeacherId()==null){
                temp++;
            }
        }
        // 按照时间先后顺序排序
        Collections.sort(paikes, (o1, o2) -> Integer.valueOf(o1.getTimeNum()) - Integer.valueOf(o2.getTimeNum()));
        // 分开显示
        MajorController.getList(majorid, model, paikes, majorMapper);
        model.addAttribute("numOfSub",paikes.size()-temp);
        return "student_kebiao";
    }

    @RequestMapping("/to_student_password_update")
    public String to_student_password_update(String id,Model model){
        Student student = studentMapper.findStudentById(id);
        model.addAttribute("student",student);
        return "student_password_update";
    }

    @RequestMapping("/student_password_update")
    public String student_password_update(String id,String password){
        studentMapper.updatePasswordByStudentId(id,password);
        return "redirect:index";
    }

}
