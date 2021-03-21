package com.zj.demo.controller;

import com.zj.demo.entity.Classroom;
import com.zj.demo.entity.ClassroomTime;
import com.zj.demo.mapper.ClassroomMapper;
import com.zj.demo.mapper.ClassroomTimeMapper;
import com.zj.demo.mapper.PaikeMapper;
import com.zj.demo.utils.IDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class ClassroomController {

    @Resource
    private ClassroomMapper classroomMapper;

    @Resource
    private ClassroomTimeMapper classroomTimeMapper;

    @Resource
    private PaikeMapper paikeMapper;

    @RequestMapping("/classroom_index")
    public String classroom_index(Model model){
        List<Classroom> classroomList = classroomMapper.findAllClassrooms();
        model.addAttribute("classroomList",classroomList);
        return "classroom_index";
    }

    @RequestMapping("/to_classroom_add")
    @Transactional
    public String to_classroom_add(){
        Classroom classroom = new Classroom();
        classroom.setId("CR"+ IDGenerator.getUniqueID());
        classroom.setName("CRNO"+ IDGenerator.getUniqueID().substring(0,3));
        classroom.setState("0");
        classroomMapper.addClassroom(classroom);
        for (int i = 0; i < 77; i++) {
            classroomTimeMapper.addClassroomTime(new ClassroomTime(
                    "CT"+IDGenerator.getUniqueID(),
                    classroom.getId(),
                    (i+1)+"",
                    "0"
                    ));
        }
        return "redirect:classroom_index";
    }

    @RequestMapping("/to_classroom_update")
    public String to_classroom_update(@RequestParam("id") String id, Model model){
        Classroom classroom = classroomMapper.findClassroomById(id);
        model.addAttribute("classroom",classroom);
        return "classroom_update";
    }

    @RequestMapping("/classroom_update")
    @Transactional
    public String classroom_update(Classroom classroom){
        classroomMapper.updateClassroomById(classroom);
        return "redirect:classroom_index";
    }

    @RequestMapping("/to_classroom_delete")
    @Transactional
    public String to_classroom_delete(@RequestParam("id") String id){
        classroomMapper.deleteClassroomById(id);
        classroomTimeMapper.deleteClassroomTimeByClassroomId(id);
        paikeMapper.deletePaikesByClassroomId(id);
        return "redirect:classroom_index";
    }

    @RequestMapping("/classroom_search")
    public String classroom_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Classroom> classroomList = classroomMapper.findClassroomsByKeyword(keyword);
        model.addAttribute("classroomList",classroomList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",classroomList.size());
        return "classroom_search";
    }

    @RequestMapping("/to_classroom_update_search")
    public String to_classroom_update_search(@RequestParam("id") String id,
                                         @RequestParam("keyword") String keyword,Model model){
        Classroom classroom = classroomMapper.findClassroomById(id);
        model.addAttribute("classroom",classroom);
        model.addAttribute("keyword",keyword);
        return "classroom_update_search";
    }

    @RequestMapping("/classroom_update_search")
    @Transactional
    public String classroom_update_search(Classroom classroom, String keyword, RedirectAttributes attributes){
        classroomMapper.updateClassroomById(classroom);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:classroom_search";
    }

    @RequestMapping("/to_classroom_delete_search")
    @Transactional
    public String to_classroom_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        classroomMapper.deleteClassroomById(id);
        classroomTimeMapper.deleteClassroomTimeByClassroomId(id);
        paikeMapper.deletePaikesByClassroomId(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:classroom_search";
    }



}
