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

    /**
     * 班级首页Controller
     * @param model
     * @return
     */
    @RequestMapping("/major_index")
    public String major_index(Model model) {
        List<Major> majorList = majorMapper.findAllMajors();
        model.addAttribute("majorList", majorList);
        return "major_index";
    }

    /**
     * 跳转到添加班级
     * @param model
     * @return
     */
    @RequestMapping("/to_major_add")
    public String to_major_add(Model model) {
        List<Subject> subjects = subjectMapper.findAllSubjects();
        model.addAttribute("subjects", subjects);
        return "major_add";
    }

    /**
     *
     * @param major
     * @param subject
     * @return
     */
    @RequestMapping("/major_add")
    @Transactional
    public String major_add(Major major, String[] subject) {
        major.setId("MJ" + IDGenerator.getUniqueID());
        System.out.println(Arrays.toString(subject));
        for (String suid :
                subject) {
            majorSubjectMapper.addSubjectsToMajor(new MajorSubject(
                    "MS" + IDGenerator.getUniqueID(), major.getId(), suid
            ));
        }
        majorMapper.addMajor(major);
        return "redirect:major_index";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/to_major_update")
    public String to_major_update(@RequestParam("id") String id, Model model) {
        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major", major);
        return "major_update";
    }

    /**
     *
     * @param major
     * @return
     */
    @RequestMapping("/major_update")
    @Transactional
    public String major_update(Major major) {
        updateMajorById(major);
        return "redirect:major_index";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/to_major_delete")
    @Transactional
    public String to_major_delete(@RequestParam("id") String id) {
        deleteMajorAndSubjectByMajorId(id);
        return "redirect:major_index";
    }

    /**
     *
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("/major_search")
    public String major_search(@ModelAttribute(value = "keyword", binding = false) String keyword, Model model) {
        List<Major> majorList = majorMapper.findMajorsByKeyword(keyword);
        model.addAttribute("majorList", majorList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("num", majorList.size());
        return "major_search";
    }

    /**
     *
     * @param id
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("/to_major_update_search")
    public String to_major_update_search(@RequestParam("id") String id,
                                         @RequestParam("keyword") String keyword, Model model) {
        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major", major);
        model.addAttribute("keyword", keyword);
        return "major_update_search";
    }

    /**
     *
     * @param major
     * @param keyword
     * @param attributes
     * @return
     */
    @RequestMapping("/major_update_search")
    public String major_update_search(Major major, String keyword, RedirectAttributes attributes) {
        updateMajorById(major);
        attributes.addFlashAttribute("keyword", keyword);
        return "redirect:major_search";
    }

    /**
     * 根据班级id更新班级信息
     * @param major
     */
    private void updateMajorById(Major major) {
        majorMapper.updateMajorById(major);
        if (major.getState().equals("0")) {
            List<Paike> paikes = paikeMapper.findPaikesByMajorId(major.getId());
            for (Paike paike :
                    paikes) {
                classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(), "0");
                teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(), paike.getTimeNum(), "0");
            }
            paikeMapper.deletePaikesByMajorId(major.getId());
        }
    }

    /**
     *
     * @param id
     * @param keyword
     * @param attributes
     * @return
     */
    @RequestMapping("/to_major_delete_search")
    @Transactional
    public String to_major_delete_search(@RequestParam("id") String id, String keyword, RedirectAttributes attributes) {
        deleteMajorAndSubjectByMajorId(id);
        attributes.addFlashAttribute("keyword", keyword);
        return "redirect:major_search";
    }

    /**
     * 根据班级id删除MajorAndSubject对象
     * @param id
     */
    private void deleteMajorAndSubjectByMajorId(@RequestParam("id") String id) {
        majorSubjectMapper.deleteMajorAndSubjectByMajorId(id);
        studentMajorMapper.deleteMajorAndStudentByMajorId(id);
        majorMapper.deleteMajorById(id);
        findPaikes(id);
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/to_major_detail")
    public String to_major_detail(String id, Model model) {
        Major major = majorMapper.findMajorById(id);
        List<String> subjects = majorSubjectMapper.findSubjectsByMajorID(id);
        List<Subject> subjectList = new ArrayList<>();
        int sum =0;
        for (String sid :
                subjects) {
            subjectList.add(subjectMapper.findSubjectById(sid));
            sum+=Integer.valueOf(subjectMapper.findSubjectById(sid).getNumOfWeek());
        }
        List<String> students = studentMajorMapper.findStudentsByMajorID(id);
        List<Student> studentList = new ArrayList<>();
        for (String tid :
                students) {
            studentList.add(studentMapper.findStudentById(tid));
        }
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("numOfStu",studentList.size());
        model.addAttribute("totalTime",sum);
        model.addAttribute("numOfSub",subjectList.size());
        model.addAttribute("studentList", studentList);
        model.addAttribute("major", major);
        return "major_detail";
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @RequestMapping("/to_paike")
    public String to_paike(@ModelAttribute("id") String id,RedirectAttributes attributes) {
        /**
         * 根据专业id找专业对应的课程
         */
        List<String> subjects = majorSubjectMapper.findSubjectsByMajorID(id);
        /**
         * 存储当前班级所有已安排课程的时间段
         * 通过比较防止后面的课程时间与前面的课程时间重复
         */
        List<String> timeList = new ArrayList<>();

        /**
         * 遍历该班级的所有课程
         * 进行排课
         * 注意：课程分为必修和选修课程，必修课程优先安排在白天，选修课程优先安排在晚上
         */
        int sum =0;
        for (String sid :
                subjects) {
            // 获取课程一周的课时量
            String num = subjectMapper.findSubjectById(sid).getNumOfWeek();
            sum+=Integer.valueOf(num);
            // 获取课程的类别：必修，选修
            String type = subjectMapper.findSubjectById(sid).getType();
            /**
             * 按照每个课程一周的课程量排课
             */
            for (int i = 0; i < Integer.valueOf(num); i++) {
                /**
                 *  定义变量
                 *  每个课程排好后都有教师，教室，时间
                 */
                Teacher teacher = null;
                Classroom classroom = null;
                String timeNum = null;

                // 存储该课程的所有授课老师
                List<String> teachers = new ArrayList<>();
                /**
                 * 要求：
                 * 同一课程同一老师
                 */
                if (i >= 1) {// 如果这门课之前排过，则找到之前的这门课老师
                    List<String> teacherId = paikeMapper.findTeacherIdByMajorIdAndSubjectId(id, sid);
                    if (teacherId.size()==0);
                    else teachers.add(teacherId.get(0));
                } else {// 如果第一次排这门课，则根据课程id获取所有的授课老师
                    teachers = teacherSubjectMapper.findTeacherIdBySubjectId(sid);
                }
                // 洗牌
                Collections.shuffle(teachers);
                for (String tid :
                        teachers) {
                    // 找到老师的有空时间
                    List<TeacherTime> teacherTimes = teacherTimeMapper.findTeacherTimesByTeacherId(tid);
                    Collections.shuffle(teacherTimes);
                    boolean fa = false;
                    for (TeacherTime tt :
                            teacherTimes) {
                        // 判断必修
                        if (type.equals("1") & Integer.valueOf(tt.getTimeNum()) % 5 == 0) {
                            continue;
                        }
                        if (type.equals("0") & Integer.valueOf(tt.getTimeNum()) % 5 != 0) {
                            continue;
                        }
                        // 判断该老师空闲时间上是否与之前的课程重复了
                        boolean fb = false;
                        for (String tl :
                                timeList) {
                            if (tl.equals(tt.getTimeNum())) {
                                fb = true;
                                break;
                            }
                        }
                        if (fb) {
                            continue;
                        }
                        // 时间不重复继续
                        if (tt.getState().equals("0")) {
                            // 将该时间存储到timeList中
                            timeList.add(tt.getTimeNum());
                            // 根据老师时间找教室
                            List<ClassroomTime> classroomTimes = classroomTimeMapper.findClassroomTimesByTimeNum(tt.getTimeNum());
                            Collections.shuffle(classroomTimes);
                            boolean fc = false;
                            for (ClassroomTime ct :
                                    classroomTimes) {
                                // 教室可用
                                if (ct.getState().equals("0")) {
                                    classroom = classroomMapper.findClassroomById(ct.getClassroomId());
                                    classroomTimeMapper.updateClassroomStateByIdAndTimeNum(classroom.getId(), tt.getTimeNum(), "1");
                                    timeNum = tt.getTimeNum();
                                    fc = true;
                                    break;
                                }
                            }
                            /**
                             * 如果教室可用就更新教师该时间段状态
                             * 否则继续
                             */
                            if (fc) {
                                teacher = teacherMapper.findTeacherById(tt.getTeacherId());
                                teacherTimeMapper.updateTeacherStateByIdAndTimeNum(tt.getTeacherId(), tt.getTimeNum(), "1");
                                fa = true;
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                    /**
                     * 如果该课程的老师和教室都找到了
                     * 则将这个课表对象存到数据库中
                     * 否则继续
                     */
                    if (fa) {
                        paikeMapper.addPaike(new Paike(IDGenerator.getUniqueID(),
                                id, sid, classroom.getId(), teacher.getId(), timeNum));
                        majorMapper.updateMajorStateById(id, "1");
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
        /**
         * 当前班级所有课程都排好后，根据班级id获取
         * 班级的全部课表信息，传递到前段展示即可
         */
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        // 按照时间先后顺序排序
        Collections.sort(paikes, (o1, o2) -> Integer.valueOf(o1.getTimeNum()) - Integer.valueOf(o2.getTimeNum()));
        // 填充没课的时间段
        List<String> list = new ArrayList<>();
        for (Paike paike :
                paikes) {
            list.add(paike.getTimeNum());
        }
        for (int i = 0; i < 35; i++) {
            if (!list.contains(String.valueOf(i+1))){
                paikeMapper.addPaike(new Paike(IDGenerator.getUniqueID(),
                        id, null, null, null, (i+1)+""));
            }
        }
        attributes.addFlashAttribute("id",id);
        return "redirect:to_paike_info";
    }

    /**
     * addList
     * @param id
     * @param model
     * @param paikeno
     */
    private void addList(String id, Model model, List<Paike> paikeno) {
        getList(id, model, paikeno, majorMapper);
    }

    /**
     * getList
     * @param id
     * @param model
     * @param paikeno
     * @param majorMapper
     */
    static void getList(String id, Model model, List<Paike> paikeno, MajorMapper majorMapper) {
        List<Paike> one = new ArrayList<>();
        List<Paike> two = new ArrayList<>();
        List<Paike> three = new ArrayList<>();
        List<Paike> four = new ArrayList<>();
        List<Paike> five = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            getLists(paikeno, one, two, three, four, five, i);
        }

        Major major = majorMapper.findMajorById(id);
        model.addAttribute("major", major);
        model.addAttribute("ones",one);
        model.addAttribute("twos",two);
        model.addAttribute("threes",three);
        model.addAttribute("fours",four);
        model.addAttribute("fives",five);
    }

    /**
     * getList
     * @param paikeno
     * @param one
     * @param two
     * @param three
     * @param four
     * @param five
     * @param i
     */
    private static void getLists(List<Paike> paikeno, List<Paike> one, List<Paike> two, List<Paike> three, List<Paike> four, List<Paike> five, int i) {
        if (i%5==0)one.add(paikeno.get(i));
        if (i%5==1)two.add(paikeno.get(i));
        if (i%5==2)three.add(paikeno.get(i));
        if (i%5==3)four.add(paikeno.get(i));
        if (i%5==4)five.add(paikeno.get(i));
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/to_paike_info")
    public String to_paike_info(@ModelAttribute("id")String id, Model model) {
        /**
         * 当前班级所有课程都排好后，根据班级id获取
         * 班级的全部课表信息，传递到前段展示即可
         */
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
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
        addList(id, model, paikes);
        model.addAttribute("numOfSub",paikes.size()-temp);
        return "paike_index";
    }

    @RequestMapping("to_paike_reset")
    public String to_paike_reset(String id,RedirectAttributes attributes){
        findPaikes(id);
        attributes.addFlashAttribute("id",id);
        return "redirect:to_paike";
    }

    /**
     * 找到对应的排课信息进行级联操作
     * @param id
     */
    private void findPaikes(String id) {
        List<Paike> paikes = paikeMapper.findPaikesByMajorId(id);
        for (Paike paike :
                paikes) {
            classroomTimeMapper.updateClassroomStateByIdAndTimeNum(paike.getClassroomId(), paike.getTimeNum(), "0");
            teacherTimeMapper.updateTeacherStateByIdAndTimeNum(paike.getTeacherId(), paike.getTimeNum(), "0");
        }
        paikeMapper.deletePaikesByMajorId(id);
    }
}
