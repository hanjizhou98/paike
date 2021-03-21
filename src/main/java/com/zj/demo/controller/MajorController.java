package com.zj.demo.controller;

import com.zj.demo.entity.*;
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
import java.util.*;


@Controller
public class MajorController {

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private MajorSubjectMapper majorSubjectMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentMajorMapper studentMajorMapper;

    @Resource
    private TeacherSubjectMapper teacherSubjectMapper;

    @Resource
    private TeacherTimeMapper teacherTimeMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private ClassroomTimeMapper classroomTimeMapper;

    @Resource
    private ClassroomMapper classroomMapper;

    @Resource
    private PaikeMapper paikeMapper;

    @RequestMapping("/major_index")
    public String major_index(Model model){
        List<Major> majorList = majorMapper.findAllMajors();
        model.addAttribute("majorList",majorList);
        return "major_index";
    }

    @RequestMapping("/to_major_add")
    public String to_major_add(Model model){
        List<Subject> subjects = subjectMapper.findAllSubjects();
        model.addAttribute("subjects",subjects);
        return "major_add";
    }

    @RequestMapping("/major_add")
    @Transactional
    public String major_add(Major major,String[] subject){
        major.setId("MJ"+IDGenerator.getUniqueID());
        System.out.println(Arrays.toString(subject));
        for (String suid :
                subject) {
            majorSubjectMapper.addSubjectsToMajor(new MajorSubject(
                    "MS"+IDGenerator.getUniqueID(),major.getId(),suid
            ));
        }
        majorMapper.addMajor(major);
        return "redirect:major_index";
    }

    @RequestMapping("/to_major_update")
    public String to_major_update(@RequestParam("id") String id, Model model){
        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major",major);
        return "major_update";
    }

    @RequestMapping("/major_update")
    @Transactional
    public String major_update(Major major){
        majorMapper.updateMajorById(major);
        if (major.getState().equals("0")){
            List<Paike> paikes = paikeMapper.findPaikesByMajorId(major.getId());
            for (Paike paike :
                    paikes) {
                classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(),"0");
                teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(),paike.getTimeNum(),"0");
            }
            paikeMapper.deletePaikesByMajorId(major.getId());
        }
        return "redirect:major_index";
    }

    @RequestMapping("/to_major_delete")
    @Transactional
    public String to_major_delete(@RequestParam("id") String id){
        majorSubjectMapper.deleteMajorAndSubjectByMajorId(id);
        studentMajorMapper.deleteMajorAndStudentByMajorId(id);
        majorMapper.deleteMajorById(id);
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        for (Paike paike :
                paikes) {
            classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(),"0");
            teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(),paike.getTimeNum(),"0");
        }
        paikeMapper.deletePaikesByMajorId(id);
        return "redirect:major_index";
    }

    @RequestMapping("/major_search")
    public String major_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Major> majorList = majorMapper.findMajorsByKeyword(keyword);
        model.addAttribute("majorList",majorList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",majorList.size());
        return "major_search";
    }

    @RequestMapping("/to_major_update_search")
    public String to_major_update_search(@RequestParam("id") String id,
                                           @RequestParam("keyword") String keyword,Model model){
        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major",major);
        model.addAttribute("keyword",keyword);
        return "major_update_search";
    }

    @RequestMapping("/major_update_search")
    public String major_update_search(Major major, String keyword, RedirectAttributes attributes){
        majorMapper.updateMajorById(major);
        if (major.getState().equals("0")){
            List<Paike> paikes = paikeMapper.findPaikesByMajorId(major.getId());
            for (Paike paike :
                    paikes) {
                classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(),"0");
                teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(),paike.getTimeNum(),"0");
            }
            paikeMapper.deletePaikesByMajorId(major.getId());
        }
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:major_search";
    }

    @RequestMapping("/to_major_delete_search")
    @Transactional
    public String to_major_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        majorSubjectMapper.deleteMajorAndSubjectByMajorId(id);
        studentMajorMapper.deleteMajorAndStudentByMajorId(id);
        majorMapper.deleteMajorById(id);
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        for (Paike paike :
                paikes) {
            classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(),"0");
            teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(),paike.getTimeNum(),"0");
        }
        paikeMapper.deletePaikesByMajorId(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:major_search";
    }

    @RequestMapping("/to_major_detail")
    public String to_major_detail(String id,Model model){
        Major major = majorMapper.findMajorById(id);
        List<String> subjects = majorSubjectMapper.findSubjectsByMajorID(id);
        List<Subject> subjectList = new ArrayList<>();
        for (String sid :
                subjects) {
            subjectList.add(subjectMapper.findSubjectById(sid));
        }
        List<String> students = studentMajorMapper.findStudentsByMajorID(id);
        List<Student> studentList = new ArrayList<>();
        for (String tid :
                students) {
            studentList.add(studentMapper.findStudentById(tid));
        }
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("major",major);
        return "major_detail";
    }

    @RequestMapping("/to_paike")
    public String to_paike(String id,Model model){
        // 根据专业id找专业对应的课程
        System.out.println("id==="+id);
        List<String> subjects = majorSubjectMapper.findSubjectsByMajorID(id);
        System.out.println("subjects=="+subjects);
        List<String> timeList = new ArrayList<>();
        for (String sid :
                subjects) {
            for (int i = 0; i < 7; i++) {
                // 每个课程排好后都有教师，教室，时间一一对应
                Teacher teacher = null;
                Classroom classroom = null;
                String timeNum="";
                // 根据课程id获取所有的老师
                List<String> teachers = teacherSubjectMapper.findTeacherIdBySubjectId(sid);
                // 洗牌
                Collections.shuffle(teachers);
                for (String tid :
                        teachers) {
                    // 找到老师的有空时间
                    List<TeacherTime> teacherTimes = teacherTimeMapper.findTeacherTimesByTeacherId(tid);
                    Collections.shuffle(teacherTimes);
                    boolean fa = false;
                    for (TeacherTime tt:
                            teacherTimes) {
                        // 判断该老师空闲时间上是否与之前的课程重复了
                        boolean fb = false;
                        for (String tl :
                                timeList) {
                            if (tl.equals(tt.getTimeNum())) {
                                fb = true;
                                break;
                            }
                        }
                        if (fb){
                            continue;
                        }
                        // 时间不重复继续
                        if (tt.getState().equals("0")){
                            // 将该时间临时存储起来
                            timeList.add(tt.getTimeNum());
                            // 根据老师时间找空闲的教室
                            List<ClassroomTime> classroomTimes = classroomTimeMapper.findClassroomTimesByTimeNum(tt.getTimeNum());
                            Collections.shuffle(classroomTimes);
                            boolean fc = false;
                            for (ClassroomTime ct :
                                    classroomTimes) {
                                if (ct.getState().equals("0")){
                                    classroom = classroomMapper.findClassroomById(ct.getClassroomId());
                                    classroomTimeMapper.updateClassroomStateByIdAndTimeNum(classroom.getId(),tt.getTimeNum(),"1");
                                    timeNum=tt.getTimeNum();
                                    fc = true;
                                    break;
                                }
                            }
                            if (fc){
                                teacher = teacherMapper.findTeacherById(tt.getTeacherId());
                                teacherTimeMapper.updateTeacherStateByIdAndTimeNum(tt.getTeacherId(),tt.getTimeNum(),"1");
                                fa=true;
                                break;
                            }else{
                                continue;
                            }
                        }
                    }
                    // 如果该课程的老师和教室都找到了
                    if (fa){
                        paikeMapper.addPaike(new Paike(IDGenerator.getUniqueID(),
                                id,sid,classroom.getId(),teacher.getId(),timeNum));
                        majorMapper.updateMajorStateById(id,"1");
                        break;
                    }else{
                        continue;
                    }
                }
            }
        }
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        Collections.sort(paikes, new Comparator<Paike>() {
            @Override
            public int compare(Paike o1, Paike o2) {
                return Integer.valueOf(o1.getTimeNum())-Integer.valueOf(o2.getTimeNum());
            }
        });
        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major",major);
        model.addAttribute("paikes",paikes);
        return "paike_index";
    }

    @RequestMapping("/to_paike_info")
    public String to_paike_info(String id,Model model){
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        Major major = majorMapper.findMajorById(id);
        Collections.sort(paikes, new Comparator<Paike>() {
            @Override
            public int compare(Paike o1, Paike o2) {
                return Integer.valueOf(o1.getTimeNum())-Integer.valueOf(o2.getTimeNum());
            }
        });
        model.addAttribute("major",major);
        model.addAttribute("paikes",paikes);
        return "paike_index";
    }

}
