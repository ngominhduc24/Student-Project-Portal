package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.services.servicesimpl.SettingService;

import java.util.List;

@Controller
public class SettingController {
    @Autowired
    SettingService settingService;
    @GetMapping("/admin/setting")
    public String settingPage(Model model) {
        List<Setting> roleList = settingService.findSettingByTypeIdOrderByDisplayOrder(1);
        List<Setting> semesterList = settingService.findSettingByTypeIdOrderByDisplayOrder(3);
        List<Setting> emailDomainList = settingService.findSettingByTypeIdOrderByDisplayOrder(2);
        model.addAttribute("roleList", roleList);
        model.addAttribute("semesterList", semesterList);
        model.addAttribute("emailDomainList", emailDomainList);
        return "admin/setting/settingList";
    }

    @GetMapping("/admin/setting/detail")
    public String settingDetail(@RequestParam("id") Integer id, Model model) {
        Setting setting = settingService.getSettingByID(id);
        model.addAttribute("setting", setting);
        return "admin/setting/settingDetail";
    }

    @PostMapping("/admin/setting/update")
    public String updateSetting(
            @RequestParam Integer typeId,
            @RequestParam String settingTitle,
            @RequestParam Integer displayOrder,
            WebRequest request, HttpSession session) {
        String status = request.getParameter("status");
        Setting setting = new Setting();
        String id = request.getParameter("id");
        if(id!=null && !id.isEmpty())
            setting.setId(Integer.parseInt(id));
        setting.setTypeId(typeId);
        setting.setSettingTitle(settingTitle);
        setting.setDisplayOrder(displayOrder);
        setting.setStatus(status!=null);
        settingService.saveSetting(setting);
        return "redirect:/admin/setting";
    }

    @GetMapping("/admin/setting/add")
    public String settingAddForm(@RequestParam("typeId") Integer typeId, Model model) {
        model.addAttribute("typeId", typeId);
        Setting setting = new Setting();
        model.addAttribute("setting", setting);
        return "admin/setting/settingDetail";
    }

    @GetMapping("/admin/setting/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Setting setting = settingService.findById(id);
        setting.setStatus(status);
        settingService.saveSetting(setting);
        return "redirect:/";
    }
}
