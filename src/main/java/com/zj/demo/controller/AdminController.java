package com.zj.demo.controller;

import com.zj.demo.entity.Admin;
import com.zj.demo.mapper.AdminMapper;
import com.zj.demo.utils.IDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

@Controller
public class AdminController {

    @Resource
    private AdminMapper adminMapper;

    @RequestMapping("/admin_index")
    public String admin_index(Model model){
        List<Admin> adminList = adminMapper.findAllAdmins();
        model.addAttribute("adminList",adminList);
        return "admin_index";
    }

    @RequestMapping("/to_admin_add")
    public String to_admin_add(){
        return "admin_add";
    }

    @RequestMapping("/admin_add")
    public String admin_add(Admin admin){
        admin.setId("AD"+IDGenerator.getUniqueID());
        admin.setPassword(admin.getPassword().toUpperCase(Locale.ROOT));
        adminMapper.addAdmin(admin);
        return "redirect:admin_index";
    }

    @RequestMapping("/to_admin_update")
    public String to_admin_update(@RequestParam("id") String id, Model model){
        Admin admin = adminMapper.findAdminById(id);
        model.addAttribute("admin",admin);
        return "admin_update";
    }

    @RequestMapping("/admin_update")
    public String admin_update(Admin admin){
        admin.setPassword(admin.getPassword().toUpperCase(Locale.ROOT));
        adminMapper.updateAdminById(admin);
        return "redirect:admin_index";
    }

    @RequestMapping("/to_admin_delete")
    public String to_admin_delete(@RequestParam("id") String id){
        adminMapper.deleteAdminById(id);
        return "redirect:admin_index";
    }

    @RequestMapping("/admin_search")
    public String admin_search(@ModelAttribute(value = "keyword",binding = false) String keyword, Model model){
        List<Admin> adminList = adminMapper.findAdminsByKeyword(keyword);
        model.addAttribute("adminList",adminList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("num",adminList.size());
        return "admin_search";
    }

    @RequestMapping("/to_admin_update_search")
    public String to_admin_update_search(@RequestParam("id") String id,
                                         @RequestParam("keyword") String keyword,Model model){
        Admin admin = adminMapper.findAdminById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("keyword",keyword);
        return "admin_update_search";
    }

    @RequestMapping("/admin_update_search")
    public String admin_update_search(Admin admin, String keyword, RedirectAttributes attributes){
        admin.setPassword(admin.getPassword().toUpperCase(Locale.ROOT));
        adminMapper.updateAdminById(admin);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:admin_search";
    }

    @RequestMapping("/to_admin_delete_search")
    public String to_admin_delete_search(@RequestParam("id") String id,String keyword, RedirectAttributes attributes){
        adminMapper.deleteAdminById(id);
        attributes.addFlashAttribute("keyword",keyword);
        return "redirect:admin_search";
    }
}
