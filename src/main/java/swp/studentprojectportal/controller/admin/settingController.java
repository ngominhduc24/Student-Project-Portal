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
public class settingController {
    @Autowired
    SettingService settingSevice;
    @GetMapping("/admin/setting")
    public String settingPage(Model model) {
        List<Setting> roleList = settingSevice.findSettingByTypeIdOrderByDisplayOrder(1);
        List<Setting> semesterList = settingSevice.findSettingByTypeIdOrderByDisplayOrder(3);
        List<Setting> emailDomainList = settingSevice.findSettingByTypeIdOrderByDisplayOrder(2);
        model.addAttribute("roleList", roleList);
        model.addAttribute("semesterList", semesterList);
        model.addAttribute("emailDomainList", emailDomainList);
        return "admin/settingList";
    }

    @GetMapping("/admin/setting/detail")
    public String settingDetail(@RequestParam("id") Integer id, Model model) {
        Setting setting = settingSevice.getSettingByID(id);
        model.addAttribute("setting", setting);
        return "admin/settingDetail";
    }

    @PostMapping("/admin/setting/update")
    public String updateSetting(
            @RequestParam int id,
            @RequestParam int typeId,
            @RequestParam String settingTitle,
            @RequestParam int displayOrder,
            WebRequest request, HttpSession session) {
        String status = request.getParameter("status");
        Setting setting = new Setting();
        setting.setId(id);
        setting.setTypeId(typeId);
        setting.setSettingTitle(settingTitle);
        setting.setDisplayOrder(displayOrder);
        setting.setStatus(status!=null);
        settingSevice.saveSetting(setting);
        return "redirect:/admin/setting";
    }

    @GetMapping("/admin/setting/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Setting setting = settingSevice.findById(id);
        setting.setStatus(status);
        settingSevice.saveSetting(setting);
        return "redirect:/";
    }
}
