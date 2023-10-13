package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.service.servicesimpl.SettingService;

import java.util.List;

@Controller
public class SettingController {
    @Autowired
    SettingService settingService;
    @GetMapping("/admin/setting")
    public String settingPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer typeId,
                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "1") Integer sortType, Model model) {
        Page<Setting> settingList = settingService.filter(search, pageNo, pageSize, sortBy, sortType, typeId, status);
        model.addAttribute("settingList", settingList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("typeId", typeId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", settingList.getTotalPages());
        return "admin/setting/settingList";
    }

    @GetMapping("/admin/setting/detail")
    public String settingDetail(@RequestParam("id") Integer id, Model model) {
        Setting setting = settingService.getSettingByID(id);
        String typeName=settingService.setTypeName(setting.getTypeId());
        model.addAttribute("setting", setting);
        model.addAttribute("typeName", typeName);
        return "admin/setting/settingDetail";
    }

    @PostMapping("/admin/setting/update")
    public String updateSetting(
            @RequestParam Integer typeId, @RequestParam String settingTitle,
            @RequestParam Integer displayOrder, @RequestParam String description, WebRequest request, Model model, RedirectAttributes attributes) {
        String status = request.getParameter("status");
        Setting setting = new Setting();
        String id = request.getParameter("id");
        if(id!=null && !id.isEmpty())    setting.setId(Integer.parseInt(id));
        setting.setTypeId(typeId);
        String typeName=settingService.setTypeName(typeId);
        setting.setSettingTitle(settingTitle);
        setting.setDescription(description);
        setting.setDisplayOrder(displayOrder);
        setting.setStatus(status!=null);
        model.addAttribute("setting", setting);
        if(settingService.checkExistedSettingTitle(settingTitle, id)){
            model.addAttribute("errmsg", "This " + typeName.toLowerCase() + " has already existed!");
        }
        else if(settingService.checkExistedDisplayOrder(typeId, displayOrder, id)){
            model.addAttribute("errmsg", "Display Order has already existed!");
        }
        else {
            attributes.addFlashAttribute("toastMessage", "Update setting details successfully");
            if(id==null || id.isEmpty()){
                attributes.addFlashAttribute("toastMessage", "Add new setting successfully");
            }
            settingService.saveSetting(setting);
            return "redirect:/admin/setting";
        }
        model.addAttribute("typeId", typeId);
        model.addAttribute("typeName", typeName);
        return "admin/setting/settingDetail";
    }

    @GetMapping("/admin/setting/add")
    public String settingAddForm(Model model) {
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
